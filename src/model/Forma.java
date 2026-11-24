package model;

public abstract class Forma {

    public static final double AREA_MINIMA = 100.0;
    public static final double AREA_MAXIMA = 1600.0;

    private Long id;
    private double dimensao;

    protected Forma(double dimensao) {
        setDimensao(dimensao);
    }

    protected Forma(double valor, boolean porArea) {
        if (porArea) {
            validarArea(valor);
            this.dimensao = calcularDimensaoPelaArea(valor);
        } else {
            setDimensao(valor);
        }
    }

    public static void validarArea(double area) {
        if (Double.isNaN(area) || Double.isInfinite(area) || area < AREA_MINIMA || area > AREA_MAXIMA) {
            throw new IllegalArgumentException("A área deve estar entre 100 e 1600 cm².");
        }
    }

    public static Forma criarPorDimensao(int tipo, double dimensao) {
        return switch (tipo) {
            case 1 -> new Circulo(dimensao);
            case 2 -> new Quadrado(dimensao);
            case 3 -> new Triangulo(dimensao);
            default -> throw new IllegalArgumentException("Formato inválido.");
        };
    }

    public static Forma criarPorArea(int tipo, double area) {
        return switch (tipo) {
            case 1 -> Circulo.porArea(area);
            case 2 -> Quadrado.porArea(area);
            case 3 -> Triangulo.porArea(area);
            default -> throw new IllegalArgumentException("Formato inválido.");
        };
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
                            + " deve estar entre " + (int) getDimensaoMinima()
                            + " e " + (int) getDimensaoMaxima() + " cm."
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