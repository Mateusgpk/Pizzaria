package model;

public abstract class Forma {
    public abstract double calcularArea();
    public abstract double calcularDimensaoPelaArea(double area);
    public abstract String nomeDimensao();
    public abstract double getDimensao();
    public abstract void setDimensao(double valor);
    public abstract void validarDimensao(double valor);
    public abstract double getDimensaoMinima();
    public abstract double getDimensaoMaxima();
    public abstract String getNomeForma();
    @Override
    public String toString() {
        return getNomeForma();
    }
}