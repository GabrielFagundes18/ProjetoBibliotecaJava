
public class Livro extends ItemBiblioteca {
    private String autor;

    public Livro(String titulo, String id, String autor) {
        super(titulo, id);
        this.autor = autor;
    }

    @Override
    public void exibirDetalhes() {
        String status = isDisponivel() ? "🟢 DISPONÍVEL" : "🔴 EMPRESTADO";
        System.out.printf("[%s] %-35s | Autor: %-20s | %s%n",
                getId(), getTitulo(), autor, status);
    }
}