import java.util.*;

public class BibliotecaRepository {
    private final List<ItemBiblioteca> acervo = new ArrayList<>();

    public void adicionar(ItemBiblioteca item) {
        acervo.add(item);
    }

    public List<ItemBiblioteca> listarTodos() {
        return new ArrayList<>(acervo);
    }

    public Optional<ItemBiblioteca> buscarPorTitulo(String titulo) {
        return acervo.stream()
                .filter(i -> i.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }
}