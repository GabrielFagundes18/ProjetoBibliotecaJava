import javax.swing.JOptionPane;

public class Main {
    private static final BibliotecaService service = new BibliotecaService();

    public static void main(String[] args) {
        String[] opcoes = { "Cadastrar", "Empréstimo", "Devolução", "Ver Acervo", "Sair" };

        while (true) {
            int escolha = JOptionPane.showOptionDialog(null,
                    "O que deseja fazer hoje?",
                    "Biblioteca Digital v1.0",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]);

            if (escolha == 4 || escolha == -1)
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
                    String busca = javax.swing.JOptionPane.showInputDialog(null,
                            disponiveis + "\nDigite o título para empréstimo:",
                            "Realizar Empréstimo", javax.swing.JOptionPane.QUESTION_MESSAGE);

                    if (busca != null && !busca.isEmpty()) {
                        service.emprestar(busca);
                    }
                }
                case 2 -> {
                    String busca = JOptionPane.showInputDialog("Título para devolução:");
                    if (busca != null)
                        service.devolver(busca);
                }
                case 3 -> {
                    String acervo = service.obterListaParaJanela(false);
                    JOptionPane.showMessageDialog(null, acervo);
                }
            }
        }
    }
}