package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza implements Forma{

    private Double cms;

    public Double getCms() {
        return cms;
    }

    public void setCms(Double cms) {
        this.cms = cms;
    }

    private Double precoCm;
    private Double preco;
    private List<Sabores> sabores = new ArrayList<>();
    private Forma forma;

    public Pizza(List<Sabores> sabores){
        this.sabores=sabores;
        calculaPreco(sabores);
        this.preco=precoCm*cms;
    }


    public void alterarPizza(List<Sabores> sabores){
        this.sabores=sabores;
        calculaPreco(sabores);
        this.preco=precoCm*cms;
    }

    public void calculaPreco(List<Sabores> sabores){
        Double media=0.0;
        for (Sabores sabor : sabores){
            media+=sabor.getTipoSabor().getPreco();
        }
        media=media/sabores.size();
        this.precoCm=media;
    }
}