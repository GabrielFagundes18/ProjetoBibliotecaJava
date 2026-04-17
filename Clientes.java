public class Clientes {
    private int id;
    private String nome;
    private String cpf;
    private String Endereco;

    public Clientes(int id, String nome, String cpf,String Endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.Endereco = Endereco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

   
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEndereco() {
        return Endereco;
    }
    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | CPF: %s | Endereço: %s", id, nome, cpf, Endereco);
    }
}