public class Livro extends ItemBiblioteca {
    
    private Clientes clienteAtual;

    public Livro(String titulo, String id, String autor) {
        super(titulo, id, autor);
    }

    public Clientes getClienteAtual() {
        return clienteAtual;
    }

    public void setClienteAtual(Clientes clienteAtual) {
        this.clienteAtual = clienteAtual;
    }

    @Override
    public void exibirDetalhes() {
        String status = isDisponivel() ? " DISPONÍVEL" : " EMPRESTADO";
        
        String infoCliente = (clienteAtual != null) ? " | Com: " + clienteAtual.getNome() : "";

        System.out.printf("[%s] %-35s | Autor: %-20s | %s%s%n",
                getId(), getTitulo(), getAutor(), status, infoCliente);
    }
}