package model;

import java.util.List;

public class Triangulo  implements Forma{

    public static final double ladoMinimo = 20.0;
    public static final double ladoMaximo = 60.0;

    private double lado;

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int getNumbForma() {
        return 3;
    }

    @Override
    public void setId(Long id) {

        this.id = id;
    }
    public Triangulo(double lado) {

        validarDimensao(lado);
        this.lado = lado;
    }

    @Override
    public double calcularArea() {
        // Area do triangulo equilatero = (raiz(3) / 4) * lado^2
        return (Math.sqrt(3) / 4.0) * lado * lado;
    }

    @Override
    public double calcularDimensaoPelaArea(double area) {
        // lado = raiz( (4 * area) / raiz(3) )
        return Math.sqrt((4.0 * area) / Math.sqrt(3));
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
                    "O lado do triangulo deve estar entre " + (int) ladoMinimo
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
        return "Triangulo";
    }
}
