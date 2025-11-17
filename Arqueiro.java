public class Arqueiro extends Personagem {

    private double chanceCritico; // Probabilidade (0 a 1) de causar dano dobrado
    private int precisao;         // Bônus adicional ao ataque, representando habilidade com arcos

    // Construtor padrão (cria um arqueiro genérico com valores predefinidos)
    public Arqueiro() {
        this("Arqueiro Anônimo", 90, 14, 5, 1, 0.25, 3); 
        // chanceCritico = 25%
        // precisão = +3 de bônus
    }

    // Construtor com parâmetros (permite criar um arqueiro com atributos personalizados)
    public Arqueiro(String nome, int pontosVida, int ataque, int defesa, int nivel,
                    double chanceCritico, int precisao) {

        super(nome, pontosVida, ataque, defesa, nivel); // Inicializa atributos da classe base
        this.chanceCritico = chanceCritico; // Define chance de crítico
        this.precisao = precisao;           // Define bônus de precisão
    }

    // Construtor de cópia (cria um novo arqueiro com base em outro arqueiro)
    public Arqueiro(Arqueiro outro) {
        super(outro);                   // Copia atributos básicos
        this.chanceCritico = outro.chanceCritico;
        this.precisao = outro.precisao;
    }

    // Calcula o dano efetivo do ataque
    @Override
    public int calcularDanoEfetivo() {

        // Dano base = ataque + precisão + rolagem de dado (1 a 6)
        int danoBase = getAtaque() + precisao + Dado.rolar(6);

        // Verifica chance de crítico:
        // Se Math.random() retornar número menor que chanceCritico → dano dobrado
        if (Math.random() < chanceCritico) {
            System.out.println("🎯 Golpe crítico!");
            danoBase *= 2;
        }

        return danoBase;
    }

    // Descrição textual da classe
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

    // Representação textual do arqueiro
    @Override
    public String toString() {
        return "Arqueiro: " + getNome() + " | HP: " + getPontosVida();
    }
}
