public class Cliente {
	private String nome;
	private String sobrenome;
	private Pedido pedido;
	private String telefone;
	
	public Cliente(String nome, String sobrenome, String telefone) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.telefone = telefone;
	}
	
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getSobrenome() { return sobrenome; }
	public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
	public Pedido getPedido() { return pedido; }
	public Pedido setPedido(Pedido pedido) { return pedido; }
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }
	
	public String toString() {
		return nome + " " + sobrenome;
	}
	
}