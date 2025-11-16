//Arqueiro.javahttps://www.onlinegdb.com/edit/icWENrOWp#_editor_9666324141

public class Arqueiro extends Personagem {

    private double chanceCritico; // chance de causar dano dobrado
    private int precisao;         // bônus no ataque

    // Construtor padrão
    public Arqueiro() {
        this("Arqueiro Anônimo", 90, 14, 5, 1, 0.25, 3);
    }

    // Construtor com parâmetros
    public Arqueiro(String nome, int pontosVida, int ataque, int defesa, int nivel, double chanceCritico, int precisao) {
        super(nome, pontosVida, ataque, defesa, nivel);
        this.chanceCritico = chanceCritico;
        this.precisao = precisao;
    }

    // Construtor de cópia
    public Arqueiro(Arqueiro outro) {
        super(outro);
        this.chanceCritico = outro.chanceCritico;
        this.precisao = outro.precisao;
    }

    @Override
    public int calcularDanoEfetivo() {
        int danoBase = getAtaque() + precisao + Dado.rolar(6);
        // Chance de crítico (25% por padrão)
        if (Math.random() < chanceCritico) {
            System.out.println("Golpe crítico!");
            danoBase *= 2;
        }
        return danoBase;
    }

    @Override
    public String descreverClasse() {
        return "Arqueiro — especialista em ataques à distância e precisão mortal.";
    }

    // Getters e Setters
    public double getChanceCritico() {
        return chanceCritico;
    }

    public void setChanceCritico(double chanceCritico) {
        this.chanceCritico = chanceCritico;
    }

    public int getPrecisao() {
        return precisao;
    }

    public void setPrecisao(int precisao) {
        this.precisao = precisao;
    }

    @Override
    public String toString() {
        return "Arqueiro: " + getNome() + " | HP: " + getPontosVida();
    }
}