package model;

public abstract class Forma {

    private Long id;
    private double dimensao;

    protected Forma(double dimensao) {
        setDimensao(dimensao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDimensao() {
        return dimensao;
    }

    public void setDimensao(double valor) {
        validarDimensao(valor);
        this.dimensao = valor;
    }

    public void validarDimensao(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)
                || valor < getDimensaoMinima() || valor > getDimensaoMaxima()) {
            throw new IllegalArgumentException(
                    "O " + nomeDimensao().toLowerCase() + " do " + getNomeForma().toLowerCase()
                            + " deve ser maior que " + (int) getDimensaoMinima()
                            + " e menor que" + (int) getDimensaoMaxima() + " cm."
            );
        }
    }

    public abstract double calcularArea();
    public abstract double calcularDimensaoPelaArea(double area);
    public abstract String nomeDimensao();
    public abstract double getDimensaoMinima();
    public abstract double getDimensaoMaxima();
    public abstract String getNomeForma();
    public abstract int getNumbForma();

    @Override
    public String toString() {
        return String.format("%s (%s: %.2f cm | Área: %.2f cm²)",
                getNomeForma(), nomeDimensao(), dimensao, calcularArea());
    }
}