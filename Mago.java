public class Mago extends Personagem {

    private int mana;           // Quantidade de energia mágica disponível para lançar feitiços
    private int poderMagico;    // Intensidade extra que aumenta o dano mágico

    // Construtor padrão (cria um mago genérico com valores pré-definidos)
    public Mago() {
        this("Mago Anônimo", 80, 12, 4, 1, 50, 10);
    }

    // Construtor com parâmetros (permite criar magos com atributos personalizados)
    public Mago(String nome, int pontosVida, int ataque, int defesa, int nivel, int mana, int poderMagico) {
        super(nome, pontosVida, ataque, defesa, nivel); // Envia atributos básicos para Personagem
        this.mana = mana;               // Inicializa a mana
        this.poderMagico = poderMagico; // Inicializa o poder mágico
    }

    // Construtor de cópia (cria um novo mago copiando os atributos de outro)
    public Mago(Mago outro) {
        super(outro);               // Usa o construtor de cópia da superclasse
        this.mana = outro.mana;
        this.poderMagico = outro.poderMagico;
    }

    // Cálculo de dano efetivo
    @Override
    public int calcularDanoEfetivo() {

        // Se tiver mana suficiente, aumenta o dano
        if (mana >= 5) {
            mana -= 5; // Gasta 5 pontos de mana por ataque mágico
            return getAtaque() + poderMagico + Dado.rolar(6);
        } 
        // Se estiver sem mana, usa ataque simples
        else {
            return getAtaque() + Dado.rolar(6);
        }
    }

    // Descrição textual da classe para exibição
    @Override
    public String descreverClasse() {
        return "Mago — mestre dos feitiços e do poder arcano.";
    }

    // Getters e Setters com proteção para evitar mana negativa
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(mana, 0); // Garante que nunca fique abaixo de 0
    }

    public int getPoderMagico() {
        return poderMagico;
    }

    public void setPoderMagico(int poderMagico) {
        this.poderMagico = poderMagico;
    }

    // Representação textual do mago
    @Override
    public String toString() {
        return "Mago: " + getNome() + " | HP: " + getPontosVida() + " | Mana: " + mana;
    }
}
