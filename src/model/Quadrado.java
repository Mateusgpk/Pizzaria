package model;

import java.util.List;

public class Quadrado implements Forma {

    public static final double ladoMinimo = 10.0;
    public static final double ladoMaximo = 40.0;
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int getNumbForma() {
        return 2;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    private double lado;

    public Quadrado(double lado) {
        validarDimensao(lado);
        this.lado = lado;

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
