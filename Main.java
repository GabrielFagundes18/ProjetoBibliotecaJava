import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        BibliotecaRepository livroRepo = new BibliotecaRepository();
        ClienteRepository clienteRepo = new ClienteRepository();

        BibliotecaService service = new BibliotecaService(livroRepo, clienteRepo);

        String[] opcoes = {
                "Cadastrar Livro",
                "Livro disponível",
                "Livros Emprestados",
                "Devolver livro",
                "Lista De Livros",
                "Cadastrar Cliente",
                "Lista De Clientes",
                "Sair"
        };

        while (true) {
            int escolha = JOptionPane.showOptionDialog(null,
                    "O que deseja fazer hoje?",
                    "Biblioteca Digital v1.1",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]);

            if (escolha == 7 || escolha == -1)
                break;

            switch (escolha) {
                case 0 -> {
                    String t = JOptionPane.showInputDialog("Título do Livro:");
                    String a = JOptionPane.showInputDialog("Autor:");
                    String i = JOptionPane.showInputDialog("ID:");
                    if (t != null && a != null && i != null) {
                        service.cadastrarLivro(t, a, i);
                        JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
                    }
                }
                case 1 -> {
                    String disponiveis = service.obterListaParaJanela(true);
                    String busca = JOptionPane.showInputDialog(null,
                            disponiveis + "\nDigite o título para empréstimo:",
                            "Realizar Empréstimo", JOptionPane.QUESTION_MESSAGE);

                    if (busca != null && !busca.isEmpty()) {
                        service.emprestar(busca);
                    }
                }
                case 2 -> {
                    String livroEmprestado = service.ObterListaDeEmpestados();
                    JOptionPane.showMessageDialog(null, livroEmprestado);
                }
                case 3 -> {
                    service.devolverPorCliente();
                }
                case 4 -> {
                    String acervo = service.obterListaParaJanela(false);
                    JOptionPane.showMessageDialog(null, acervo);
                }
                case 5 -> {
                    service.cadastrarCliente();
                }
                case 6 -> {
                    String listaClientes = service.obterListaClientes();
                    JOptionPane.showMessageDialog(null, listaClientes);
                }
            }
        }
    }
}