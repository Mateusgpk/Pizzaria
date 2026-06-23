package model;

public class TipoSabor {
    private String nome;
    private Double precocm;

    public TipoSabor (String nome, Double precocm){
        this.nome=nome;
        this.precocm=precocm;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return precocm;
    }

    public void setPreco(Double preco) {
        this.precocm = preco;
    }
}
