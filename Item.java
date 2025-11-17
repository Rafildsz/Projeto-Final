// Importa a classe Objects, usada para equals() e hashCode()
import java.util.Objects;

// Enum que define os tipos possíveis de efeitos de um item. Padroniza os tipos de efeito 
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

// representa qualquer item utilizável no jogo. Implementa Comparable para permitir ordenação entre itens.
public class Item implements Comparable<Item> {

    private final String nome;
    private final String descricao;
    private final Efeito efeito;
    private int quantidade;

    // Construtor do item. Valida para garantir que a quantidade inicial não seja negativa.
    public Item(String nome, String descricao, Efeito efeito, int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade inicial do item não pode ser negativa.");
        }
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.quantidade = quantidade;
    }

    // Métodos getters — retornam os valores dos atributos.
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

    // Setter da quantidade. Inclui validação para impedir quantidades negativas.
    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade do item não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }

    // Método equals — define quando dois itens são considerados iguais (itens são iguais se tiverem o MESMO NOME e o MESMO EFEITO.)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Se for o mesmo objeto, já é igual.
        if (o == null || getClass() != o.getClass()) return false; // Se não for do mesmo tipo, não é igual.

        Item item = (Item) o;

        // Critério de igualdade: nome + efeito
        return Objects.equals(nome, item.nome) && efeito == item.efeito;
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nome, efeito);
    }

    // Implementação da interface Comparable
    // Permite ordenar itens em listas, sets ordenados, etc
    @Override
    public int compareTo(Item outro) {
        // Primeira ordenação: pelo nome do item
        int comparacaoNome = this.nome.compareTo(outro.nome);

        if (comparacaoNome != 0) {
            return comparacaoNome; // Se os nomes forem diferentes
        }

        // Se os nomes forem iguais, desempata pelo tipo de efeito.
        return this.efeito.compareTo(outro.efeito);
    }

    // Representação em texto do item 
    @Override
    public String toString() {
        return "[" + nome + "] - Qtd: " + quantidade + " | Efeito: " + efeito + " | " + descricao;
    }
}
