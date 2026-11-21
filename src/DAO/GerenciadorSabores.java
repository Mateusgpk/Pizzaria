package DAO;

import model.Dao.SaboresDao;
import model.Dao.TipoSaboresDao;
import model.Sabores;
import model.TipoSabor;

import java.util.List;

public class GerenciadorSabores {

    private final SaboresDao saboresDao;
    private final TipoSaboresDao tipoSaboresDao;

    public GerenciadorSabores() {
        this.saboresDao = new SaboresDao();
        this.tipoSaboresDao = TipoSaboresDao.getTipoSaboresDao();
    }

    public void adicionarSabor(Sabores sabor) throws Exception {
        for (Sabores s : listarTodos()) {
            if (s.getNome().equalsIgnoreCase(sabor.getNome())) {
                throw new IllegalArgumentException("Já existe um sabor com este nome.");
            }
        }
        saboresDao.add(sabor);
    }

    public void atualizarSabor(Sabores sabor) throws Exception {
        if (sabor == null || sabor.getId() == null) {
            throw new IllegalArgumentException("Sabor inválido para atualização.");
        }
        saboresDao.update(sabor);
    }

    public void removerSabor(Sabores sabor) throws Exception {
        if (sabor == null || sabor.getId() == null) {
            throw new IllegalArgumentException("Sabor inválido para exclusão.");
        }
        saboresDao.delete(sabor);
    }

    public void atualizarPrecoTipo(TipoSabor tipo) throws Exception {
        if (tipo.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser positivo.");
        }
        tipoSaboresDao.update(tipo);
    }

    public List<Sabores> listarTodos() throws Exception {
        return saboresDao.getAll();
    }

    public List<TipoSabor> listarTipos() throws Exception {
        return tipoSaboresDao.getAll();
    }
}