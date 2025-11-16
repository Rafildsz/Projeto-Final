//Inimigo.java

public class Inimigo extends Personagem {

    // Construtor padrão 
    public Inimigo() {
        this("Inimigo Genérico", 50, 8, 3, 1);
    }
    
    // Construtor com parâmetros
    public Inimigo(String nome, int vida, int ataque, int defesa, int nivel) {
        super(nome, vida, ataque, defesa, nivel);
    }

    // Construtor de cópia 
    public Inimigo(Inimigo outro) {
        super(outro); // Chama o construtor de cópia da superclasse Personagem
    }

    @Override
    public int calcularDanoEfetivo() {
        return getAtaque() + Dado.rolar(6);
    }

    @Override
    public String descreverClasse() {
        return "Inimigo genérico e perigoso.";
    }

    @Override
    public void atacar(Personagem oponente) {
        int dano = calcularDanoEfetivo() - oponente.getDefesa();
        if (dano > 0) {
            oponente.receberDano(dano);
            System.out.println(getNome() + " causa " + dano + " de dano a " + oponente.getNome() + "!");
        } else {
            System.out.println(getNome() + " errou o ataque!");
        }
    }
}