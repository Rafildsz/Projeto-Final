//Guerreiro.java

public class Guerreiro extends Personagem {

    // Construtor padrão 
    public Guerreiro() {
        this("Guerreiro Anônimo", 100, 15, 5, 1);
    }
    
    // Construtor com parâmetros
    public Guerreiro(String nome, int vida, int ataque, int defesa, int nivel) {
        super(nome, vida, ataque, defesa, nivel);
    }

    // Construtor de cópia 
    public Guerreiro(Guerreiro outro) {
        super(outro); // Chama o construtor de cópia da superclasse Personagem
    }

    @Override
    public int calcularDanoEfetivo() {
        int base = getAtaque() + Dado.rolar(6);
        // Chance de crítico
        if (Dado.rolar(4) == 4) {
            System.out.println("💥 GOLPE CRÍTICO!");
            base *= 1.5;
        }
        return base;
    }

    @Override
    public String descreverClasse() {
        return "Guerreiro valente e habilidoso com espadas.";
    }

    @Override
    public void atacar(Personagem oponente) {
        int dano = calcularDanoEfetivo() - oponente.getDefesa();
        if (dano > 0) {
            oponente.receberDano(dano);
            System.out.println(getNome() + " causa " + dano + " de dano a " + oponente.getNome() + "!");
        } else {
            System.out.println(getNome() + " não conseguiu causar dano!");
        }
    }
}