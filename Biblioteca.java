import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<ItemBiblioteca> listaDeItens = new ArrayList<>();

    public void adicionarItem(ItemBiblioteca item) {
        listaDeItens.add(item);
    }

    public void emprestarItem(String titulo) {
        for (ItemBiblioteca item : listaDeItens) {
            if (item.getTitulo().equalsIgnoreCase(titulo.trim())) {
                if (item.isDisponivel()) {
                    item.setDisponivel(false);
                    System.out.println("✅ Sucesso: Empréstimo de '" + item.getTitulo() + "' realizado!");
                    return; 
                } else {
                    System.out.println("⚠️ Ops: O item '" + item.getTitulo() + "' já está emprestado.");
                    return;
                }
            }
        }
        System.out.println("❌ Erro: Item '" + titulo + "' não encontrado na lista.");
    }

    public void devolverItem(String titulo) {
        for (ItemBiblioteca item : listaDeItens) {
            if (item.getTitulo().equalsIgnoreCase(titulo.trim())) {
                if (!item.isDisponivel()) {
                    item.setDisponivel(true);
                    System.out.println("✅ Sucesso: Item '" + item.getTitulo() + "' devolvido!");
                } else {
                    System.out.println("ℹ️ Info: Este item já está disponível no sistema.");
                }
                return;
            }
        }
        System.out.println("❌ Erro: Título não encontrado para devolução.");
    }

    public void mostrarAcervo() {
        System.out.println("\n===============================");
        System.out.println("       ITENS NA BIBLIOTECA");
        System.out.println("===============================");
        
        if (listaDeItens.isEmpty()) {
            System.out.println("A biblioteca está vazia no momento.");
        } else {
            for (ItemBiblioteca item : listaDeItens) {
                item.exibirDetalhes(); 
            }
        }
        System.out.println("===============================\n");
    }
}