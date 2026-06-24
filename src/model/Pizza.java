package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza implements Forma{


    private Double preco;
    private List<Sabores> sabores = new ArrayList<>();
    private Double cms;

    public List<Sabores> getSabores() {
        return sabores;
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

    public void setPreco(Double preco){
        this.preco=preco;
    }

    public Pizza(List<Sabores> sabores){
        this.sabores=sabores;
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