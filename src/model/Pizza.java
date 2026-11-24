package model;

import java.util.ArrayList;
import java.util.List;

public class Pizza {

    private Long id;
    private Long idPedido;
    private List<Sabores> sabores = new ArrayList<>();
    private Forma forma;

    public Pizza(Long id, Long idPedido, List<Sabores> sabores, Forma forma) {
        if (forma == null) {
            throw new IllegalArgumentException("A forma da pizza é obrigatória.");
        }
        validarSabores(sabores);
        this.id = id;
        this.idPedido = idPedido;
        this.sabores = new ArrayList<>(sabores);
        this.forma = forma;
    }

    private void validarSabores(List<Sabores> sabores) {
        if (sabores == null || sabores.isEmpty() || sabores.size() > 2) {
            throw new IllegalArgumentException("A pizza deve ter 1 ou 2 sabores.");
        }
        if (sabores.size() == 2 && sabores.get(0).equals(sabores.get(1))) {
            throw new IllegalArgumentException("Os dois sabores da pizza não podem ser iguais.");
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public List<Sabores> getSabores() { return sabores; }
    public Forma getForma() { return forma; }

    public Double getCms() { return forma.calcularArea(); }
    public int getTipoForma() { return forma.getNumbForma(); }
    public double getDimensao() { return forma.getDimensao(); }

    public void setDimensao(double dimensao) { this.forma.setDimensao(dimensao); }
    public void setTipoForma(int tipoForma) { /* derivado de forma.getNumbForma() */ }
    public void setCms(Double cms) { /* derivado de forma.calcularArea() */ }

    public String getsabor1() {
        return sabores.isEmpty() ? "" : sabores.get(0).getNome();
    }

    public String getsabor2() {
        return sabores.size() > 1 ? sabores.get(1).getNome() : "";
    }

    public Double getPreco() {
        return calculaPrecoInteiro();
    }

    public Double calculaPrecoInteiro() {
        return calculaPreco(this.sabores) * getCms();
    }

    public void alterarPizza(List<Sabores> sabores) {
        validarSabores(sabores);
        this.sabores = new ArrayList<>(sabores);
    }

    public Double calculaPreco(List<Sabores> sabores) {
        double soma = 0.0;
        for (Sabores sabor : sabores) {
            soma += sabor.getTipoSabor().getPreco();
        }
        return soma / sabores.size();
    }
}