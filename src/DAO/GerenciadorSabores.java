package DAO;

import model.Sabores;
import model.TipoSabor;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorSabores {

    private List<Sabores> sabores;
    private List<TipoSabor> tipos;

    public GerenciadorSabores() {
        this.tipos = new ArrayList<>();
        TipoSabor simples = new TipoSabor("Simples", 0.10);
        TipoSabor especial = new TipoSabor("Especial", 0.20);
        TipoSabor premium  = new TipoSabor("Premium",  0.35);
        tipos.add(simples);
        tipos.add(especial);
        tipos.add(premium);

        this.sabores = new ArrayList<>();
        sabores.add(new Sabores(simples, "Mussarela"));
        sabores.add(new Sabores(simples, "Calabresa"));
        sabores.add(new Sabores(especial, "Frango c/ Catupiry"));
        sabores.add(new Sabores(especial, "Quatro Queijos"));
        sabores.add(new Sabores(premium,  "Pepperoni"));
        sabores.add(new Sabores(premium,  "Trufada"));
    }

    public void adicionarSabor(Sabores sabor) {
        for (Sabores s : sabores)
            if (s.getNome().equalsIgnoreCase(sabor.getNome()))
                throw new IllegalArgumentException("Já existe um sabor com este nome.");
        sabores.add(sabor);
    }

    public void atualizarSabor(int index, String novoNome, TipoSabor novoTipo) {
        if (index < 0 || index >= sabores.size())
            throw new IllegalArgumentException("Sabor não encontrado.");
        sabores.get(index).setNome(novoNome);
        sabores.get(index).setTipoSabor(novoTipo);
    }

    public void removerSabor(int index) {
        if (index < 0 || index >= sabores.size())
            throw new IllegalArgumentException("Sabor não encontrado.");
        sabores.remove(index);
    }

    public List<Sabores> listarTodos() { return new ArrayList<>(sabores); }
    public List<TipoSabor> listarTipos() { return new ArrayList<>(tipos); }
}