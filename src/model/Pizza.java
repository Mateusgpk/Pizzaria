package model;

import java.util.ArrayList;
import java.util.List;

public class Pizza{

    private Long id;
    private Long idPedido;
    private List<Sabores> sabores = new ArrayList<>();
    private Double cms;
    private Forma forma;

    public int getTipoForma() {
        return tipoForma;
    }

    public void setTipoForma(int tipoForma) {
        this.tipoForma = tipoForma;
    }

    public double getDimensao() {
        return dimensao;
    }

    public void setDimensao(double dimensao) {
        this.dimensao = dimensao;
    }

    private int tipoForma;
    private double dimensao;
;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public List<Sabores> getSabores() {
        return sabores;
    }

    public Forma getForma(){
        return forma;
    }

    public Double getCms() {
        return cms;
    }

    public String getsabor1(){
        return this.sabores.get(0).getNome();
    }

    public String getsabor2(){
        return this.sabores.get(1).getNome();
    }

    public void setCms(Double cms) {
        this.cms = cms;
    }

    public Double getPreco() {
        return calculaPrecoInteiro();
    }


    public Pizza(Long id, Long idPedido, List<Sabores> sabores, Forma forma){
        this.id = id;
        this.idPedido = idPedido;
        this.sabores = sabores;
        this.forma = forma;
        this.tipoForma = forma.getNumbForma();
        this.dimensao = forma.getDimensao();
        this.cms = forma.calcularArea();
        calculaPreco(sabores);

    }
    public Double calculaPrecoInteiro(){
        return calculaPreco(this.sabores)*cms;
    }

    public void alterarPizza(List<Sabores> sabores){
        this.sabores=sabores;
        calculaPreco(sabores);
    }

    public Double calculaPreco(List<Sabores> sabores){
        Double media=0.0;
        for (Sabores sabor : sabores){
            media+=sabor.getTipoSabor().getPreco();
        }
        media=media/sabores.size();
        return media;
    }
}