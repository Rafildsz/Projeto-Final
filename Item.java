import java.util.Objects;

enum Efeito {
    CURA,
    DANO,
    BUFF_ATAQUE,
    BUFF_DEFESA,
    BUFF_MAGIA,     
    BUFF_PRECISAO,  
    BUFF_TOTAL,     
    OUTRO

}


public class Item implements Comparable<Item> {

    private final String nome;
    private final String descricao;
    private final Efeito efeito;

    private int quantidade;

    public Item(String nome, String descricao, Efeito efeito, int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade inicial do item não pode ser negativa.");
        }
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Efeito getEfeito() {
        return efeito;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade do item não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        // O critério de igualdade é: Nome e Efeito
        return Objects.equals(nome, item.nome) && efeito == item.efeito;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, efeito);
    }

    @Override
    public int compareTo(Item outro) {
        // Ordena primeiramente pelo nome
        int comparacaoNome = this.nome.compareTo(outro.nome);
        
        if (comparacaoNome != 0) {
            return comparacaoNome;
        }
        
        // Desempate 
        return this.efeito.compareTo(outro.efeito);
    }

    @Override
    public String toString() {
        return "[" + nome + "] - Qtd: " + quantidade + " | Efeito: " + efeito + " | " + descricao;
    }
}