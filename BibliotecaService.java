import javax.swing.JOptionPane;

public class BibliotecaService {
    private final BibliotecaRepository repository = new BibliotecaRepository();

    public BibliotecaService() {
        popularDadosIniciais();
    }

    public void cadastrarLivro(String titulo, String autor, String id) {
        repository.adicionar(new Livro(titulo, id, autor));
    }

    public void emprestar(String titulo) {
        repository.buscarPorTitulo(titulo).ifPresentOrElse(item -> {
            if (item.isDisponivel()) {
                item.setDisponivel(false);
                JOptionPane.showMessageDialog(null,
                        " Livro emprestado com sucesso!\n" +
                                "Título: " + item.getTitulo() + "\n" +
                                "ID: " + item.getId(),
                        "Empréstimo Confirmado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "O livro '" + item.getTitulo() + "' já está emprestado!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }, () -> {
            JOptionPane.showMessageDialog(null,
                    " Livro não encontrado no sistema.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void devolver(String titulo) {
        repository.buscarPorTitulo(titulo).ifPresentOrElse(item -> {
            if (!item.isDisponivel()) {
                item.setDisponivel(true);
                JOptionPane.showMessageDialog(null,
                        " Devolução realizada!\nLivro: " + item.getTitulo(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Este livro já consta como disponível no acervo.",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        }, () -> {
            JOptionPane.showMessageDialog(null,
                    " Título inválido ou não encontrado.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        });
    }

    public String obterListaParaJanela(boolean apenasDisponiveis) {
        StringBuilder sb = new StringBuilder();
        sb.append(apenasDisponiveis ? " LIVROS PARA EMPRÉSTIMO \n" : " LISTA COMPLETA \n");
        sb.append(String.format("%-10s | %-5s | %s\n", "STATUS", "ID", "TÍTULO"));
        sb.append("-".repeat(40)).append("\n");

        var lista = repository.listarTodos().stream()
                .filter(i -> !apenasDisponiveis || i.isDisponivel())
                .toList();

        if (lista.isEmpty())
            return "Nenhum livro disponível no momento.";

        for (var item : lista) {
            String status = item.isDisponivel() ? " [OK] " : " [OUT] ";
            sb.append(String.format("%-8s | %-5s | %s\n", status, item.getId(), item.getTitulo()));
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
            repository.adicionar(new Livro(l[0], l[1], l[2]));
    }
}