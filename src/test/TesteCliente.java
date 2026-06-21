package test;

import DAO.GerenciadorCliente;
import model.Cliente;

public class TesteCliente {

    public static void main(String[] args) {

        GerenciadorCliente gerenciador = new GerenciadorCliente();

        System.out.println("Cadastrando clientes válidos...");
        gerenciador.adicionarCliente(new Cliente("Nicolas", "Mendes", "41999999999"));
        gerenciador.adicionarCliente(new Cliente("Rafael", "Tsuji", "41888888888"));
        gerenciador.adicionarCliente(new Cliente("Mateus", "Gabriel", "41777777777"));
        gerenciador.adicionarCliente(new Cliente("Carlos", "Vital", "41666666666"));
        imprimirLista(gerenciador);

        System.out.println("\nTentando cadastrar cliente com telefone duplicado...");
        try {
            gerenciador.adicionarCliente(new Cliente("João", "Silva", "41999999999"));
            System.err.println("FALHA: O sistema permitiu um telefone duplicado!");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO (Erro capturado): " + e.getMessage());
        }

        System.out.println("\nFiltrando clientes pelo sobrenome 'Tsuji'...");
        System.out.println("Resultados encontrados: " + gerenciador.filtrarClientes("Tsuji").size());

        System.out.println("\nFiltrando clientes por parte do telefone '41'...");
        System.out.println("Resultados encontrados: " + gerenciador.filtrarClientes("41").size());

        // 4. Teste de Atualização
        System.out.println("\nAtualizando dados do Rafael...");
        try {
            Cliente dadosAtualizados = new Cliente("Rafael", "Tsuji Uchida", "41888888888");
            gerenciador.atualizarCliente("41888888888", dadosAtualizados);
            System.out.println("SUCESSO: Model.Cliente atualizado.");
        } catch (IllegalArgumentException e) {
            System.err.println("FALHA ao atualizar: " + e.getMessage());
        }

        System.out.println("\nExcluindo o cliente Mateus...");
        boolean removido = gerenciador.removerCliente("41777777777");
        if (removido) {
            System.out.println("SUCESSO: Model.Cliente excluido.");
        } else {
            System.err.println("FALHA: Model.Cliente não encontrado.");
        }

        System.out.println("\n=== ESTADO FINAL ===");
        imprimirLista(gerenciador);
    }

    /**
     * Método auxiliar para imprimir a lista de clientes no console.
     */
    private static void imprimirLista(GerenciadorCliente gerenciador) {
        System.out.println("-------------------------------------------------");
        if (gerenciador.listarTodos().isEmpty()) {
            System.out.println("Lista vazia.");
        } else {
            for (Cliente c : gerenciador.listarTodos()) {
                System.out.printf("Nome: %-20s | Telefone: %s\n", c.toString(), c.getTelefone());
            }
        }
        System.out.println("-------------------------------------------------");
    }
}