package model;

public class Quadrado extends Forma {

    public static final double ladoMinimo = 10.0;
    public static final double ladoMaximo = 40.0;

    public Quadrado(double lado) {
        super(lado);
    }

    private Quadrado(double area, boolean porArea) {
        super(area, porArea);
    }

    public static Quadrado porArea(double area) {
        return new Quadrado(area, true);
    }

    @Override
    public double calcularArea() {
        return getDimensao() * getDimensao();
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

    @Override
    public int getNumbForma() {
        return 2;
    }
}