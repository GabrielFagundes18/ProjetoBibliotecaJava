import java.util.*;

public class ClienteRepository {
    private final List<Clientes> clientes = new ArrayList<>();
    private int proximoId = 1;

    public ClienteRepository() {
        popularClientesIniciais();
    }

    private void popularClientesIniciais() {
        adicionar(new Clientes(proximoId++, "Machado de Assis", "123.456.789-00", "Rua 1, 100"));
        adicionar(new Clientes(proximoId++, "Clarice Lispector", "987.654.321-11", "Rua 2, 200"));
        adicionar(new Clientes(proximoId++, "Fernando Pessoa", "999.888.777-66", "Rua 3, 300"));
    }

    public void adicionar(Clientes cliente) {
        clientes.add(cliente);
    }

    public List<Clientes> listarTodos() {
        return new ArrayList<>(clientes);
    }

    public Optional<Clientes> buscarPorId(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }
}