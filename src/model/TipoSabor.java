package model;

public class TipoSabor {
    private Long id;
    private String nome;
    private Double precocm;

    public TipoSabor (Long id, String nome, Double precocm){
        this.id=id;
        this.nome=nome;
        this.precocm=precocm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return nome;
    }

    public void setPreco(Double preco) {
        this.precocm = preco;
    }
}
