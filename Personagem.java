import java.util.Objects;

public abstract class Personagem implements Cloneable {

    private String nome;
    private int maxPontosVida; // Para limitar a cura
    private int pontosVida;    
    private int ataque;
    private int defesa;
    private int nivel;
    private Inventario inventario; 

    // === Construtores ===
    public Personagem() {
        this("Nome Indefinido", 100, 10, 5, 1);
    }
    
    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel) {
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

    // Construtor de cópia
    public Personagem(Personagem outro) {
        this.nome = outro.nome;
        this.maxPontosVida = outro.maxPontosVida;
        this.pontosVida = outro.pontosVida;
        this.ataque = outro.ataque;
        this.defesa = outro.defesa;
        this.nivel = outro.nivel;
        this.inventario = outro.inventario != null ? outro.inventario.clone() : new Inventario();
    }
    
    // === Métodos abstratos ===
    public abstract int calcularDanoEfetivo();
    public abstract String descreverClasse();
    
    // === Lógica de combate ===
    public void atacar(Personagem oponente) {
        int dano = Math.max(0, calcularDanoEfetivo() - oponente.getDefesa());
        if (dano > 0) {
            oponente.receberDano(dano);
            System.out.println(getNome() + " causa " + dano + " de dano a " + oponente.getNome() + "!");
        } else {
            System.out.println(getNome() + " errou o ataque!");
        }
    }
    
    public void receberDano(int dano) {
        if (dano > 0) {
            this.pontosVida -= dano;
            if (this.pontosVida < 0) {
                this.pontosVida = 0;
            }
        }
    }
    
    public void receberCura(int cura) {
        if (cura > 0) {
            this.pontosVida += cura;
            if (this.pontosVida > this.maxPontosVida) { 
                this.pontosVida = this.maxPontosVida;
            }
        }
    }

    // === Evolução ===
    public void subirNivel() {
        this.nivel++;
        this.maxPontosVida += 10;
        this.ataque += 2;
        this.defesa += 1;
        this.pontosVida = this.maxPontosVida;
        System.out.println("\n✨ " + nome + " subiu para o nível " + nivel + "!");
        System.out.println("Seus atributos aumentaram!");
    }

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
    @Override
    public Personagem clone() {
        try {
            Personagem copia = (Personagem) super.clone();
            copia.inventario = this.inventario != null ? this.inventario.clone() : new Inventario();
            return copia;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Erro ao clonar personagem.");
        }
    }

    @Override
    public String toString() {
        return String.format(
            "%s [HP: %d/%d | Atq: %d | Def: %d | Nível: %d]",
            nome, pontosVida, maxPontosVida, ataque, defesa, nivel
        );
    }
}
