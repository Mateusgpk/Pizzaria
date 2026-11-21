package model;

public interface Forma {

    public abstract double calcularArea();
    public abstract double calcularDimensaoPelaArea(double area);
    public abstract String nomeDimensao();
    public abstract double getDimensao();
    public abstract void setDimensao(double valor);
    public abstract void validarDimensao(double valor);
    public abstract double getDimensaoMinima();
    public abstract double getDimensaoMaxima();
    public abstract String getNomeForma();
    public abstract Long getId();
    public abstract int getNumbForma();
    @Override
    public String toString();

    public abstract void setId(Long i);
}