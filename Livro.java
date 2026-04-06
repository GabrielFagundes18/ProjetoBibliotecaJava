public class Livro extends ItemBiblioteca {
    private String autor;

    public Livro(String titulo, String id, String autor) {
        super(titulo, id);
        this.autor = autor;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("LIVRO: " + getTitulo() + 
                           " | Autor: " + autor + 
                           " | Status: " + (isDisponivel() ? "Disponível" : "Emprestado"));
    }
}