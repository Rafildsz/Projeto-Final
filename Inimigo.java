public class Inimigo extends Personagem {

    // Construtor padrão (cria um inimigo genérico com valores básicos pré-definidos)
    public Inimigo() {
        this("Inimigo Genérico", 50, 8, 3, 1);
    }
    
    // Construtor personalizado (permite criar um inimigo com atributos específicos)
    public Inimigo(String nome, int vida, int ataque, int defesa, int nivel) {
        super(nome, vida, ataque, defesa, nivel); // Envia os dados para a superclasse Personagem
    }

    // Construtor de cópia (Cria um novo inimigo copiando os atributos de outro inimigo existente)
    public Inimigo(Inimigo outro) {
        super(outro); // Reutiliza o construtor de cópia definido em Personagem
    }

    // Cálculo do dano efetivo do inimigo (o dano é igual ao seu ataque mais a rolagem de um dado de 6 lados)
    @Override
    public int calcularDanoEfetivo() {
        return getAtaque() + Dado.rolar(6);
    }

    // Descrição textual da classe do personagem
    @Override
    public String descreverClasse() {
        return "Inimigo genérico e perigoso.";
    }

    // Método de ataque do inimigo contra outro personagem
    @Override
    public void atacar(Personagem oponente) {
        // O dano final considera o dano base menos a defesa do oponente
        int dano = calcularDanoEfetivo() - oponente.getDefesa();

        // Se o dano for positivo, aplica o dano
        if (dano > 0) {
            oponente.receberDano(dano);
            System.out.println(getNome() + " causa " + dano + " de dano a " + oponente.getNome() + "!");
        } 
        // Caso contrário, o ataque falhou
        else {
            System.out.println(getNome() + " errou o ataque!");
        }
    }
}
