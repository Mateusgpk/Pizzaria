package model;

/**
 * Classe que representa um cliente da pizzaria.
 * Armazena os dados pessoais de cada Model.Cliente,
 * além dos dados de contato.
 *
 * @version 1.0
 */
public class Cliente {

    private String nome;
    private String sobrenome;
    private Pedido pedido;
    private String telefone;

    /**
     * Construtor parametrizado para inicialização de um novo cliente
     *
     * @param nome      Primeiro nome do cliente.
     * @param sobrenome Sobrenome do cliente.
     * @param telefone  Telefone de contato
     */
    public Cliente(String nome, String sobrenome, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
    }

    /**
     * Retorna o primeiro nome do cliente.
     *
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o primeiro nome do cliente.
     *
     * @param nome O novo nome a ser atribuído.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o sobrenome do cliente.
     *
     * @return O sobrenome do cliente.
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     * Define o sobrenome do cliente.
     *
     * @param sobrenome O novo sobrenome a ser atribuído.
     */
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    /**
     * Retorna o pedido atual vinculado a este cliente.
     *
     * @return O objeto Model.Pedido associado.
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Vincula um pedido a este cliente.
     *
     * @param pedido O objeto Model.Pedido a ser associado.
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Retorna o telefone do cliente.
     *
     * @return O telefone do cliente.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone de contato do cliente.
     *
     * @param telefone O novo telefone a ser atribuído.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o nome completo do cliente.
     *
     * @return Nome e sobrenome concatenados.
     */
    @Override
    public String toString() {
        return nome + " " + sobrenome;
    }
}