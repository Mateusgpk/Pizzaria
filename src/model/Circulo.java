package model;

public class Circulo implements Forma {

        public static final double raioMinimo = 7.0;
        public static final double raioMaximo = 23.0;

        private double raio;
        private Long id;

        @Override
        public Long getId() {
            return id;
        }

    @Override
    public int getNumbForma() {
        return 1;
    }

    @Override
        public void setId(Long id) {
            this.id = id;
        }
    public Circulo(double raio) {

        validarDimensao(raio);
        this.raio = raio;
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
        return "Circulo";
    }

}
