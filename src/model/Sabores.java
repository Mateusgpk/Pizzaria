package model;

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
}