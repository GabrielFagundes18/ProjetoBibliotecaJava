import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Biblioteca minhaBiblioteca = new Biblioteca();
        Scanner leitor = new Scanner(System.in);
        int opcao = 0;

        System.out.println("=== BEM-VINDO AO SISTEMA DE BIBLIOTECA ===");

        while (opcao != 5) {
            System.out.println("\n1. Cadastrar Novo Livro");
            System.out.println("2. Pegar Livro (Empréstimo)");
            System.out.println("3. Devolver Livro");
            System.out.println("4. Ver Itens na Biblioteca");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Título do Livro: ");
                    String titulo = leitor.nextLine();
                    System.out.print("Autor: ");
                    String autor = leitor.nextLine();
                    System.out.print("ID (Ex: L01): ");
                    String id = leitor.nextLine();

                    Livro novoLivro = new Livro(titulo, id, autor);
                    minhaBiblioteca.adicionarItem(novoLivro);
                    System.out.println("Livro cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.print("Digite o título do livro para empréstimo: ");
                    String buscaEmprestimo = leitor.nextLine();
                    minhaBiblioteca.emprestarItem(buscaEmprestimo);
                    break;

                case 3:
                    System.out.print("Digite o título do livro para devolver: ");
                    String buscaDevolucao = leitor.nextLine();
                    minhaBiblioteca.devolverItem(buscaDevolucao);
                    break;

                case 4:
                    minhaBiblioteca.mostrarAcervo();
                    break;

                case 5:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
        leitor.close();
    }
}