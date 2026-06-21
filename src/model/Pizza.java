package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {

    private Double cms;
    private Double precoCm;
    private Double preco;
    private List<Sabores> sabores = new ArrayList<>();

    public Pizza(List<Sabores> sabores){
        this.sabores=sabores;
        calculaPreco(sabores);
        this.preco=precoCm*cms;
    }

    public abstract void calculaCm();

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
