import java.util.*;

public class Inventario implements Cloneable {

    // Mapa que armazena os itens. A chave é um Item e o valor é a quantidade 
    private final Map<Item, Integer> itens;

    // Construtor padrão (cria um inventário vazio)
    public Inventario() {
        this.itens = new HashMap<>();
    }

    // Construtor de cópia (cria um novo inventário baseado em outro existente)
    // Faz uma cópia profunda dos itens para evitar que objetos sejam compartilhados
    public Inventario(Inventario outro) {
        this.itens = new HashMap<>();
        
        for (Map.Entry<Item, Integer> entry : outro.itens.entrySet()) {
            Item original = entry.getKey();      // Item do outro inventário
            int quantidade = entry.getValue();   // Quantidade dele

            // Cria uma nova instância do Item (cópia profunda)
            Item copia = new Item(
                original.getNome(),
                original.getDescricao(),
                original.getEfeito(),
                quantidade
            );

            this.itens.put(copia, quantidade); // Armazena a cópia
        }
    }

    // Adiciona um item ao inventário
    public void adicionar(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo.");
        }

        // Verifica se já existe um item igual (mesmo nome e mesmo efeito)
        for (Item existente : itens.keySet()) {
            if (existente.equals(item)) {

                // Se existe, soma as quantidades
                int novaQtd = existente.getQuantidade() + item.getQuantidade();
                existente.setQuantidade(novaQtd);

                // Atualiza o mapa
                itens.put(existente, novaQtd);
                return;
            }
        }

        // Se for um item novo, apenas adiciona
        itens.put(item, item.getQuantidade());
    }

    // Remove uma quantidade específica de um item pelo nome
    public boolean remover(String nome, int quantidade) {
        if (nome == null || quantidade <= 0) {
            throw new IllegalArgumentException("Nome inválido ou quantidade deve ser positiva.");
        }

        // Cria um HashSet para evitar erros de modificação durante iteração
        for (Item item : new HashSet<>(itens.keySet())) {
            if (item.getNome().equalsIgnoreCase(nome)) {

                int qtdAtual = item.getQuantidade();

                // Se não há quantidade suficiente
                if (qtdAtual < quantidade) {
                    System.out.println("Quantidade insuficiente de " + nome + " para remover.");
                    return false;
                }

                // Atualiza a quantidade
                item.setQuantidade(qtdAtual - quantidade);

                // Se zerou, remove do inventário
                if (item.getQuantidade() == 0) {
                    itens.remove(item);
                } else {
                    itens.put(item, item.getQuantidade());
                }

                return true;
            }
        }
        return false; // Item com o nome solicitado não encontrado
    }

    // Retorna a lista de itens ordenada
    // A ordenação segue o compareTo() da classe Item (por nome e depois efeito)
    public List<Item> listarOrdenado() {
        List<Item> lista = new ArrayList<>(itens.keySet());
        Collections.sort(lista);
        return lista;
    }

    // Retorna quantos tipos de itens existem no inventário
    public int tamanho() {
        return itens.size();
    }

    // Implementa clonagem, utilizando o construtor de cópia
    @Override
    public Inventario clone() {
        return new Inventario(this);
    }

    // Representação textual do inventário
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
