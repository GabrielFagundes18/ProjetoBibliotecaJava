
public abstract class ItemBiblioteca {
    private String titulo;
    private String id;
    private String autor;
    private boolean disponivel;

    public ItemBiblioteca(String titulo, String id, String autor) {
        this.titulo = titulo;
        this.id = id;
        this.autor = autor;
        this.disponivel = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getId() {
        return id;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public abstract void exibirDetalhes();
}