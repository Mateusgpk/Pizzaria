import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar o cadastro e controle
 * dos clientes do sistema.
 *
 * @version 1.0
 */
public class GerenciadorCliente {

    // Lista em memória que armazena todos os clientes ativos.
    private List<Cliente> clientes;

    /**
     * Construtor da lista de clientes, instanciando vazia.
     */
    public GerenciadorCliente() {
        this.clientes = new ArrayList<>();
    }

    /**
     * Cadastra um novo cliente no sistema.
     * Impede o cadastro de telefones duplicados.
     *
     * @param cliente O objeto Cliente a ser salvo.
     * @throws IllegalArgumentException Se o telefone já estiver cadastrado.
     */
    public void adicionarCliente(Cliente cliente) throws IllegalArgumentException {

        if (buscarPorTelefone(cliente.getTelefone()) != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este telefone.");
        }

        this.clientes.add(cliente);
    }

    /**
     * Retorna a lista completa de todos os clientes cadastrados.
     *
     * @return Uma lista (List) contendo os clientes.
     */
    public List<Cliente> listarTodos() {

        return new ArrayList<>(this.clientes);

    }

    /**
     * Atualiza os dados de um cliente existente com base no seu telefone atual.
     *
     * @param telefoneAtual  O telefone do cliente que será editado.
     * @param clienteEditado Objeto contendo os novos dados.
     * @throws IllegalArgumentException Se o cliente não for encontrado.
     */
    public void atualizarCliente(String telefoneAtual, Cliente clienteEditado) throws IllegalArgumentException {
        Cliente clienteExistente = buscarPorTelefone(telefoneAtual);

        if (clienteExistente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para atualização.");
        }

        // Atualiza os dados mantendo a referência do objeto (o que preserva o Pedido atual dele)
        clienteExistente.setNome(clienteEditado.getNome());
        clienteExistente.setSobrenome(clienteEditado.getSobrenome());
        clienteExistente.setTelefone(clienteEditado.getTelefone());
    }

    /**
     * Remove um cliente do sistema.
     *
     * @param telefone O telefone do cliente a ser excluído.
     * @return true se removido com sucesso, false caso não exista.
     */
    public boolean removerCliente(String telefone) {
        Cliente clienteParaRemover = buscarPorTelefone(telefone);
        if (clienteParaRemover != null) {
            return this.clientes.remove(clienteParaRemover);
        }
        return false;
    }

    /**
     * Busca um cliente exato pelo seu número de telefone.
     *
     * @param telefone O telefone a ser buscado.
     * @return O objeto Cliente correspondente, ou null se não encontrado.
     */
    public Cliente buscarPorTelefone(String telefone) {
        for (Cliente c : this.clientes) {
            if (c.getTelefone().equals(telefone)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Filtra a lista de clientes por sobrenome, parte dele ou telefone.
     *
     * @param termoBusca O texto usado para a pesquisa.
     * @return Uma nova lista contendo apenas os clientes que correspondem ao filtro.
     */
    public List<Cliente> filtrarClientes(String termoBusca) {
        if (termoBusca == null || termoBusca.trim().isEmpty()) {
            return listarTodos(); // Retorna todos se o filtro estiver vazio
        }

        List<Cliente> resultado = new ArrayList<>();
        String termoLowerCase = termoBusca.toLowerCase().trim();

        for (Cliente c : this.clientes) {
            boolean combinaSobrenome = c.getSobrenome().toLowerCase().contains(termoLowerCase);
            boolean combinaTelefone = c.getTelefone().contains(termoLowerCase);

            if (combinaSobrenome || combinaTelefone) {
                resultado.add(c);
            }
        }

        return resultado;
    }
}