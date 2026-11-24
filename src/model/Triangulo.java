package model;

public class Triangulo extends Forma {

    public static final double ladoMinimo = 20.0;
    public static final double ladoMaximo = 60.0;

    public Triangulo(double lado) {
        super(lado);
    }

    @Override
    public double calcularArea() {
        return (Math.sqrt(3) / 4.0) * getDimensao() * getDimensao();
    }

    @Override
    public double calcularDimensaoPelaArea(double area) {
        return Math.sqrt((4.0 * area) / Math.sqrt(3));
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
        return "Triangulo";
    }

    @Override
    public int getNumbForma() {
        return 3;
    }
}