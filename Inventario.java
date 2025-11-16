import java.util.*; // importa Map, HashMap, HashSet, ArrayList, Collections, List etc.

public class Inventario implements Cloneable {

    private final Map<Item, Integer> itens;

    public Inventario() {
        this.itens = new HashMap<>();
    }

    public Inventario(Inventario outro) {
        this.itens = new HashMap<>();
        for (Map.Entry<Item, Integer> entry : outro.itens.entrySet()) {
            Item original = entry.getKey();
            int quantidade = entry.getValue();
            Item copia = new Item(
                original.getNome(),
                original.getDescricao(),
                original.getEfeito(),
                quantidade
            );
            this.itens.put(copia, quantidade);
        }
    }

    public void adicionar(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo.");
        }

        for (Item existente : itens.keySet()) {
            if (existente.equals(item)) {
                int novaQtd = existente.getQuantidade() + item.getQuantidade();
                existente.setQuantidade(novaQtd);
                itens.put(existente, novaQtd);
                return;
            }
        }

        itens.put(item, item.getQuantidade());
    }

    public boolean remover(String nome, int quantidade) {
        if (nome == null || quantidade <= 0) {
            throw new IllegalArgumentException("Nome inválido ou quantidade deve ser positiva.");
        }

        for (Item item : new HashSet<>(itens.keySet())) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                int qtdAtual = item.getQuantidade();

                if (qtdAtual < quantidade) {
                    System.out.println("Quantidade insuficiente de " + nome + " para remover.");
                    return false;
                }

                item.setQuantidade(qtdAtual - quantidade);

                if (item.getQuantidade() == 0) {
                    itens.remove(item);
                } else {
                    itens.put(item, item.getQuantidade());
                }
                return true;
            }
        }
        return false;
    }

    public List<Item> listarOrdenado() {
        List<Item> lista = new ArrayList<>(itens.keySet());
        Collections.sort(lista);
        return lista;
    }

    public int tamanho() {
        return itens.size();
    }

    @Override
    public Inventario clone() {
        return new Inventario(this);
    }

    @Override
    public String toString() {
        if (itens.isEmpty()) {
            return "Inventário vazio.";
        }

        StringBuilder sb = new StringBuilder("=== Inventário ===\n");
        for (Item item : listarOrdenado()) {
            sb.append(item).append("\n");
        }
        return sb.toString();
    }
}
