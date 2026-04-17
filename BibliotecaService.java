import java.util.List;

import javax.swing.JOptionPane;

public class BibliotecaService {
    private final BibliotecaRepository livroRepository;
    private final ClienteRepository clienteRepository;

    public BibliotecaService(BibliotecaRepository livroRepo, ClienteRepository clienteRepo) {
        this.livroRepository = livroRepo;
        this.clienteRepository = clienteRepo;
        popularDadosIniciais();
    }

    public void cadastrarLivro(String titulo, String autor, String id) {
        livroRepository.adicionar(new Livro(titulo, id, autor));
    }

    public void emprestar(String titulo) {

        livroRepository.buscarPorTitulo(titulo).ifPresentOrElse(item -> {
            if (item.isDisponivel()) {

                String idInput = JOptionPane.showInputDialog(null,
                        "Livro: " + item.getTitulo() + "\nDigite o ID do Cliente:",
                        "Identificação do Cliente", JOptionPane.QUESTION_MESSAGE);

                if (idInput != null && !idInput.isEmpty()) {
                    try {
                        int idCliente = Integer.parseInt(idInput);

                        clienteRepository.buscarPorId(idCliente).ifPresentOrElse(clienteEncontrado -> {

                            item.setDisponivel(false);
                            ((Livro) item).setClienteAtual(clienteEncontrado);

                            JOptionPane.showMessageDialog(null,
                                    "Empréstimo realizado com sucesso!\n\n" +
                                            "Livro: " + item.getTitulo() + "\n" +
                                            "Autor: " + item.getAutor() + "\n" +
                                            "Cliente: " + clienteEncontrado.getNome() + "\n" +
                                            "Endereço: " + clienteEncontrado.getEndereco(),
                                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                        }, () -> {

                            JOptionPane.showMessageDialog(null,
                                    "Erro: Cliente com ID " + idCliente + " não encontrado!",
                                    "Erro de Identificação", JOptionPane.ERROR_MESSAGE);
                        });

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Por favor, digite um número de ID válido.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "O livro '" + item.getTitulo() + "' já está emprestado!");
            }
        }, () -> {
            JOptionPane.showMessageDialog(null, "Livro não encontrado no sistema.");
        });
    }

    public void devolverPorCliente() {
        try {
            String idInput = JOptionPane.showInputDialog("Digite o ID do cliente que está a devolver o livro:");
            if (idInput == null || idInput.isEmpty())
                return;

            int idCliente = Integer.parseInt(idInput);

            clienteRepository.buscarPorId(idCliente).ifPresentOrElse(cliente -> {

                List<Livro> livrosDoCliente = livroRepository.listarTodos().stream()
                        .filter(item -> item instanceof Livro) // Garante que é um Livro
                        .map(item -> (Livro) item) // Faz o cast
                        .filter(l -> !l.isDisponivel() && l.getClienteAtual() != null)
                        .filter(l -> l.getClienteAtual().getId() == idCliente)
                        .toList();

                if (livrosDoCliente.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Este cliente não tem livros para devolver.");
                } else {
                    StringBuilder menu = new StringBuilder("Livros em posse de " + cliente.getNome() + ":\n");
                    for (int i = 0; i < livrosDoCliente.size(); i++) {
                        menu.append(i).append(" - ").append(livrosDoCliente.get(i).getTitulo()).append("\n");
                    }
                    menu.append("\nDigite o número do livro que deseja devolver:");

                    String escolhaStr = JOptionPane.showInputDialog(menu.toString());

                    if (escolhaStr != null) {
                        int index = Integer.parseInt(escolhaStr);

                        if (index >= 0 && index < livrosDoCliente.size()) {
                            Livro livroParaDevolver = livrosDoCliente.get(index);

                            livroParaDevolver.setDisponivel(true);
                            livroParaDevolver.setClienteAtual(null); 

                            JOptionPane.showMessageDialog(null, "Sucesso! O livro '" +
                                    livroParaDevolver.getTitulo() + "' foi devolvido.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Opção inválida.");
                        }
                    }
                }
            }, () -> JOptionPane.showMessageDialog(null, "Cliente não encontrado!"));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Digite apenas números para o ID e para a escolha.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    public String obterListaParaJanela(boolean apenasDisponiveis) {
        StringBuilder sb = new StringBuilder();
        sb.append(apenasDisponiveis ? " LIVROS PARA EMPRÉSTIMO \n" : " LISTA COMPLETA \n");
        sb.append(String.format("%-10s | %-5s | %s\n", "STATUS", "ID", "TÍTULO"));
        sb.append("-".repeat(40)).append("\n");

        var lista = livroRepository.listarTodos().stream()
                .filter(i -> !apenasDisponiveis || i.isDisponivel())
                .toList();

        if (lista.isEmpty())
            return "Nenhum livro disponível no momento.";

        for (var item : lista) {
            String status = item.isDisponivel() ? "Disponível  " : "Emprestado ";
            sb.append(String.format("%-8s | %-5s | %s\n", status, item.getId(), item.getTitulo()));
        }
        return sb.toString();
    }

    public String ObterListaDeEmpestados() {
        StringBuilder sb = new StringBuilder();
        sb.append(" LIVROS EMPRESTADOS \n");
        sb.append(String.format("%-10s | %-5s | %s\n", "STATUS", "ID", "TÍTULO"));
        sb.append("-".repeat(40)).append("\n");

        var lista = livroRepository.listarTodos().stream()
                .filter(i -> !i.isDisponivel())
                .toList();
        if (lista.isEmpty())
            return "Nenhum livro emprestado no momento.";

        for (var item : lista) {
            String status = item.isDisponivel() ? "Disponível  " : "Emprestado ";
            sb.append(String.format("%-8s | %-5s | %s\n", status, item.getId(),
                    item.getTitulo() + "\n" + "Autor: " + item.getAutor() + "\n" + "Cliente: "
                            + ((Livro) item).getClienteAtual().getNome(),
                    ((Livro) item).getClienteAtual().getEndereco() + "\n" +
                     "-----------------------------"));
        }
        return sb.toString();
    }

    private void popularDadosIniciais() {
        String[][] livros = {
                { "O Senhor dos Anéis", "L001", "J.R.R. Tolkien" }, { "1984", "L002", "George Orwell" },
                { "Dom Casmurro", "L003", "Machado de Assis" }, { "Clean Code", "L004", "Robert C. Martin" },
                { "O Pequeno Príncipe", "L005", "Antoine de Saint-Exupéry" },
                { "Harry Potter", "L006", "J.K. Rowling" },
                { "O Código Da Vinci", "L007", "Dan Brown" }, { "A Menina que Roubava Livros", "L008", "Markus Zusak" },
                { "O Hobbit", "L009", "J.R.R. Tolkien" }, { "Orgulho e Preconceito", "L010", "Jane Austen" },
                { "O Alquimista", "L011", "Paulo Coelho" }, { "O Diário de Anne Frank", "L012", "Anne Frank" },
                { "Moby Dick", "L013", "Herman Melville" }, { "Cem Anos de Solidão", "L014", "G. García Márquez" },
                { "A Culpa é das Estrelas", "L015", "John Green" }, { "O Caçador de Pipas", "L016", "Khaled Hosseini" },
                { "Ensaio sobre a Cegueira", "L017", "José Saramago" },
                { "A Revolução dos Bichos", "L018", "George Orwell" },
                { "Crime e Castigo", "L019", "Fiódor Dostoiévski" },
                { "O Grande Gatsby", "L020", "F. Scott Fitzgerald" },
                { "Código Limpo", "L021", "Robert C. Martin" }, { "Arquitetura Limpa", "L022", "Robert C. Martin" },
                { "Design Patterns", "L023", "GoF" }, { "Java: Como Programar", "L024", "Deitel" },
                { "Estruturas de Dados", "L025", "Thomas Cormen" }, { "O Cortiço", "L026", "Aluísio Azevedo" },
                { "Memórias Póstumas", "L027", "Machado de Assis" }, { "Guerra e Paz", "L028", "Liev Tolstói" },
                { "Alice no País das Maravilhas", "L029", "Lewis Carroll" }, { "Frankenstein", "L030", "Mary Shelley" }
        };
        for (String[] l : livros)
            livroRepository.adicionar(new Livro(l[0], l[1], l[2]));
    }

    public void cadastrarCliente() {
        try {
            String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");
            if (nome == null || nome.trim().isEmpty())
                return;

            String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente:");
            if (cpf == null || cpf.trim().isEmpty())
                return;
            String endereco = JOptionPane.showInputDialog("Digite o endereço do cliente:");
            if (endereco == null || endereco.trim().isEmpty())
                return;

            int novoId = clienteRepository.listarTodos().size() + 1;
            Clientes novoCliente = new Clientes(novoId, nome, cpf, endereco);

            clienteRepository.adicionar(novoCliente);

            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!\n" + novoCliente);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    public String obterListaClientes() {
        StringBuilder sb = new StringBuilder();
        sb.append(" \n   LISTA DE CLIENTES CADASTRADOS \n");
        sb.append(String.format("%-5s | %-20s | %s\n", "ID", "NOME", "CPF"));
        sb.append("-".repeat(45)).append("\n");

        var lista = clienteRepository.listarTodos();

        if (lista.isEmpty()) {
            return "Nenhum cliente cadastrado no sistema.";
        }

        for (Clientes c : lista) {
            sb.append(String.format("%-5d | %-20s | %s\n",
                    c.getId(),
                    c.getNome(),
                    c.getCpf()));
        }

        return sb.toString();
    }
}