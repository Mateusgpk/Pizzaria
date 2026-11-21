package DAO;

import model.Cliente;
import model.Dao.ClienteDao;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorCliente {

    private final ClienteDao clienteDao;

    public GerenciadorCliente() {
        this.clienteDao = ClienteDao.getClientDaoSql();
    }

    public void adicionarCliente(Cliente cliente) throws Exception {
        if (buscarPorTelefone(cliente.getTelefone()) != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este telefone.");
        }
        clienteDao.add(cliente);
    }

    public List<Cliente> listarTodos() throws Exception {
        return clienteDao.getAll();
    }

    public void atualizarCliente(String telefoneAtual, Cliente clienteEditado) throws Exception {
        Cliente clienteExistente = buscarPorTelefone(telefoneAtual);

        if (clienteExistente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para atualização.");
        }

        Cliente outroComMesmoTelefone = buscarPorTelefone(clienteEditado.getTelefone());
        if (outroComMesmoTelefone != null && !outroComMesmoTelefone.getId().equals(clienteExistente.getId())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este telefone.");
        }

        clienteEditado.setId(clienteExistente.getId());
        clienteDao.update(clienteEditado);
    }

    public boolean removerCliente(String telefone) throws Exception {
        Cliente clienteParaRemover = buscarPorTelefone(telefone);
        if (clienteParaRemover != null) {
            clienteDao.delete(clienteParaRemover);
            return true;
        }
        return false;
    }

    public Cliente buscarPorTelefone(String telefone) throws Exception {
        for (Cliente c : listarTodos()) {
            if (c.getTelefone().equals(telefone)) {
                return c;
            }
        }
        return null;
    }

    public List<Cliente> filtrarClientes(String termoBusca) throws Exception {
        List<Cliente> todos = listarTodos();
        if (termoBusca == null || termoBusca.trim().isEmpty()) {
            return todos;
        }

        List<Cliente> resultado = new ArrayList<>();
        String termoLowerCase = termoBusca.toLowerCase().trim();

        for (Cliente c : todos) {
            boolean combinaSobrenome = c.getSobrenome().toLowerCase().contains(termoLowerCase);
            boolean combinaTelefone = c.getTelefone().contains(termoLowerCase);

            if (combinaSobrenome || combinaTelefone) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}