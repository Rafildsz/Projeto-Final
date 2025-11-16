//Mago.java

public class Mago extends Personagem {

    private int mana;           // energia mágica para lançar feitiços
    private int poderMagico;    // poder adicional dos feitiços

    // Construtor padrão
    public Mago() {
        this("Mago Anônimo", 80, 12, 4, 1, 50, 10);
    }

    // Construtor com parâmetros
    public Mago(String nome, int pontosVida, int ataque, int defesa, int nivel, int mana, int poderMagico) {
        super(nome, pontosVida, ataque, defesa, nivel);
        this.mana = mana;
        this.poderMagico = poderMagico;
    }

    // Construtor de cópia
    public Mago(Mago outro) {
        super(outro);
        this.mana = outro.mana;
        this.poderMagico = outro.poderMagico;
    }

    @Override
    public int calcularDanoEfetivo() {
        // Mago usa mana para aumentar dano
        if (mana >= 5) {
            mana -= 5; // gasta um pouco de mana por ataque
            return getAtaque() + poderMagico + Dado.rolar(6);
        } else {
            // ataque básico se estiver sem mana
            return getAtaque() + Dado.rolar(6);
        }
    }

    @Override
    public String descreverClasse() {
        return "Mago — mestre dos feitiços e do poder arcano.";
    }

    // Getters e Setters
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(mana, 0);
    }

    public int getPoderMagico() {
        return poderMagico;
    }

    public void setPoderMagico(int poderMagico) {
        this.poderMagico = poderMagico;
    }

    @Override
    public String toString() {
        return "Mago: " + getNome() + " | HP: " + getPontosVida() + " | Mana: " + mana;
    }
}