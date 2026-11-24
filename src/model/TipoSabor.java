package model;

import java.util.Objects;

public class TipoSabor {
    private Long id;
    private String nome;
    private Double precocm;

    public TipoSabor(Long id, String nome, Double precocm) {
        this.id = id;
        this.nome = nome;
        this.precocm = precocm;
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

    public void setPreco(Double preco) {
        if (preco == null || preco <= 0 || Double.isNaN(preco) || Double.isInfinite(preco)) {
            throw new IllegalArgumentException("O preço por cm² deve ser um número positivo.");
        }

        this.precocm = preco;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoSabor that)) return false;
        if (this.id != null && that.id != null) {
            return Objects.equals(this.id, that.id);
        }
        return this.nome != null && this.nome.equalsIgnoreCase(that.nome);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(nome == null ? null : nome.toLowerCase());
    }

}
