package model;

public class Circulo extends Forma {
    public static final double raioMinimo = 7.0;
    public static final double raioMaximo = 23.0;
    public Circulo(double raio) {
        super(raio);
    }
    @Override
    public double calcularArea() {
        return Math.PI * getDimensao() * getDimensao();
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
    public double getDimensaoMinima() {
        return raioMinimo;
    }
    @Override
    public double getDimensaoMaxima() {
        return raioMaximo;
    }
    @Override
    public String getNomeForma() {
        return "Circulo";
    }
    @Override
    public int getNumbForma() {
        return 1;
    }
}
