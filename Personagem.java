import java.util.Objects;

// Classe abstrata base para qualquer tipo de personagem do jogo
// Implementa Cloneable para permitir cópias do personagem
public abstract class Personagem implements Cloneable {

    private String nome;
    private int maxPontosVida; // Vida máxima, usada para limitar cura
    private int pontosVida;    // Vida atual
    private int ataque;
    private int defesa;
    private int nivel;
    private Inventario inventario; 


    // Construtor padrão com valores básicos iniciais
    public Personagem() {
        this("Nome Indefinido", 100, 10, 5, 1);
    }
    
    // Construtor principal com validações obrigatórias
    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel) {
        // Evita personagens inválidos com atributos negativos ou nome vazio
        if (pontosVida <= 0 || ataque <= 0 || defesa < 0 || nivel <= 0 || nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro ao criar Personagem: Verifique os atributos iniciais.");
        }

        this.nome = nome;
        this.maxPontosVida = pontosVida; 
        this.pontosVida = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = new Inventario();
    }

    // Construtor de cópia (cria um novo personagem com os mesmos atributos)
    public Personagem(Personagem outro) {
        this.nome = outro.nome;
        this.maxPontosVida = outro.maxPontosVida;
        this.pontosVida = outro.pontosVida;
        this.ataque = outro.ataque;
        this.defesa = outro.defesa;
        this.nivel = outro.nivel;

        // Clona o inventário para evitar compartilhamento indevido
        this.inventario = outro.inventario != null ? outro.inventario.clone() : new Inventario();
    }
    
    // Métodos abstratos
    // Cada tipo de personagem terá sua própria forma de calcular dano
    public abstract int calcularDanoEfetivo();

    // Cada classe deve fornecer sua descrição
    public abstract String descreverClasse();
    
    // === Lógica de combate ===

    // Método que realiza um ataque contra outro personagem
    public void atacar(Personagem oponente) {
        // Dano efetivo é o cálculo do atacante menos a defesa do oponente
        int dano = Math.max(0, calcularDanoEfetivo() - oponente.getDefesa());

        // Se houver dano, aplica ao oponente
        if (dano > 0) {
            oponente.receberDano(dano);
            System.out.println(getNome() + " causa " + dano + " de dano a " + oponente.getNome() + "!");
        } else {
            // Caso contrário, o ataque "erra" (ou o oponente defende totalmente)
            System.out.println(getNome() + " errou o ataque!");
        }
    }
    
    // Reduz a vida do personagem ao receber dano
    public void receberDano(int dano) {
        if (dano > 0) {
            this.pontosVida -= dano;

            // Vida nunca pode ser negativa
            if (this.pontosVida < 0) {
                this.pontosVida = 0;
            }
        }
    }
    
    // Aumenta os pontos de vida até o limite máximo permitido
    public void receberCura(int cura) {
        if (cura > 0) {
            this.pontosVida += cura;

            // Não pode ultrapassar a vida máxima
            if (this.pontosVida > this.maxPontosVida) { 
                this.pontosVida = this.maxPontosVida;
            }
        }
    }

    // === Evolução do personagem ===

    // Sobe de nível aumentando seus atributos
    public void subirNivel() {
        this.nivel++;
        this.maxPontosVida += 10;
        this.ataque += 2;
        this.defesa += 1;
        this.pontosVida = this.maxPontosVida; // Recupera a vida ao máximo

        System.out.println("\n✨ " + nome + " subiu para o nível " + nivel + "!");
        System.out.println("Seus atributos aumentaram!");
    }

    // Verifica se o personagem ainda está vivo
    public boolean estaVivo() {
        return this.pontosVida > 0;
    }

    // === Getters e Setters ===

    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public int getMaxPontosVida() { 
        return maxPontosVida; 
    } 
    public void setMaxPontosVida(int maxPontosVida) { 
        if (maxPontosVida <= 0) {
             throw new IllegalArgumentException("HP Máximo deve ser positivo.");
        }
        this.maxPontosVida = maxPontosVida;
    }

    public int getPontosVida() {
        return pontosVida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setAtaque(int ataque) {
        if (ataque < 0) throw new IllegalArgumentException("Ataque não pode ser negativo.");
        this.ataque = ataque;
    }

    public void setDefesa(int defesa) {
        if (defesa < 0) throw new IllegalArgumentException("Defesa não pode ser negativa.");
        this.defesa = defesa;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        if (nivel <= 0) throw new IllegalArgumentException("Nível deve ser positivo.");
        this.nivel = nivel;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        if (inventario == null) throw new IllegalArgumentException("Inventário não pode ser nulo.");
        this.inventario = inventario;
    }

    // === Métodos utilitários ===

    // Realiza clonagem profunda do personagem, incluindo inventário
    @Override
    public Personagem clone() {
        try {
            Personagem copia = (Personagem) super.clone();
            
            // Clona inventário para evitar referência compartilhada
            copia.inventario = this.inventario != null ? this.inventario.clone() : new Inventario();

            return copia;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Erro ao clonar personagem.");
        }
    }

    // Representação em texto do personagem
    @Override
    public String toString() {
        return String.format(
            "%s [HP: %d/%d | Atq: %d | Def: %d | Nível: %d]",
            nome, pontosVida, maxPontosVida, ataque, defesa, nivel
        );
    }
}
