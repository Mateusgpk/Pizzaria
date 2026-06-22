package model;

import java.util.List;

public class Circulo extends Pizza {

    public static final double raioMinimo = 7.0;
    public static final double raioMaximo = 23.0;

    private double raio;

    public Circulo(double raio, List<Sabores> sabores) {
        super(sabores);
        validarDimensao(raio);
        this.raio = raio;
        this.setCms(calcularArea());
    }

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override
    public double calcularDimensaoPelaArea(double area) {
        return Math.sqrt(area / Math.PI);
    }

    @Override
    public String nomeDimensao() {
        return "Raio";
    }

    @Override
    public double getDimensao() {
        return raio;
    }

    @Override
    public void setDimensao(double valor) {
        validarDimensao(valor);
        this.raio = valor;
    }

    @Override
    public void validarDimensao(double valor) {
        if (valor < raioMinimo || valor > raioMaximo) {
            throw new IllegalArgumentException(
                    "O raio do circulo deve estar entre " + (int) raioMinimo
                            + " e " + (int) raioMaximo + " cm.");
        }
    }

    @Override
    public double getDimensaoMinima() {
        return raioMinimo;
    }

    @Override
    public double getDimensaoMaxima() {
        return raioMaximo;
    }

    @Override
    public String getNomeForma() {
        return "model.Circulo";
    }

}
