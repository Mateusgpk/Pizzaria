package model;

import java.util.Objects;

public class Sabores {
    private Long id;
    private TipoSabor tipoSabor;
    private String nome;

    public Sabores(Long id, TipoSabor tipoSabor, String nome){
        this.id=id;
        this.tipoSabor=tipoSabor;
        this.nome=nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoSabor getTipoSabor() {
        return tipoSabor;
    }

    public void setTipoSabor(TipoSabor tipoSabor) {
        this.tipoSabor = tipoSabor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sabores that)) return false;
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