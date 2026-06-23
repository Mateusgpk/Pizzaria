package model;

import java.util.List;

public class Quadrado extends Pizza {

    public static final double ladoMinimo = 10.0;
    public static final double ladoMaximo = 40.0;

    private double lado;

    public Quadrado(double lado, List<Sabores> sabores) {
        super(sabores);
        validarDimensao(lado);
        this.lado = lado;
        this.setCms(calcularArea());
        setPreco(calculaPrecoInteiro());
    }

    @Override
    public double calcularArea() {
        return lado * lado;
    }

    @Override
    public double calcularDimensaoPelaArea(double area) {
        return Math.sqrt(area);
    }

    @Override
    public String nomeDimensao() {
        return "Lado";
    }

    @Override
    public double getDimensao() {
        return lado;
    }

    @Override
    public void setDimensao(double valor) {
        validarDimensao(valor);
        this.lado = valor;
    }

    @Override
    public void validarDimensao(double valor) {
        if (valor < ladoMinimo || valor > ladoMaximo) {
            throw new IllegalArgumentException(
                    "O lado do quadrado deve estar entre " + (int) ladoMinimo
                            + " e " + (int) ladoMaximo + " cm.");
        }
    }

    @Override
    public double getDimensaoMinima() {
        return ladoMinimo;
    }

    @Override
    public double getDimensaoMaxima() {
        return ladoMaximo;
    }

    @Override
    public String getNomeForma() {
        return "Quadrado";
    }
}
