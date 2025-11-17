public class Guerreiro extends Personagem {

    // Construtor padrão (cria um guerreiro genérico caso nenhum dado seja informado)
    public Guerreiro() {
        this("Guerreiro Anônimo", 100, 15, 5, 1);
    }
    
    // Construtor completo (permite criar um guerreiro com atributos personalizados)
    public Guerreiro(String nome, int vida, int ataque, int defesa, int nivel) {
        super(nome, vida, ataque, defesa, nivel); // Envia os atributos para o construtor da classe Personagem
    }

    // Construtor de cópia (cria um novo guerreiro copiando outro)
    public Guerreiro(Guerreiro outro) {
        super(outro); // Reutiliza o construtor de cópia da superclasse Personagem
    }

    // Método que calcula o dano total deste guerreiro
    @Override
    public int calcularDanoEfetivo() {
        // Dano base = ataque do guerreiro + rolagem de dado de 1 a 6
        int base = getAtaque() + Dado.rolar(6);

        // Chance de crítico: se rolar 4 no dado de 4 faces, o dano aumenta 50%
        if (Dado.rolar(4) == 4) {
            System.out.println("💥 GOLPE CRÍTICO!");
            base *= 1.5;
        }

        return base;
    }

    // Descrição da classe do personagem
    @Override
    public String descreverClasse() {
        return "Guerreiro valente e habilidoso com espadas.";
    }

    // Método de ataque contra outro personagem
    @Override
    public void atacar(Personagem oponente) {
        // Cálculo do dano final: dano efetivo - defesa do oponente
        int dano = calcularDanoEfetivo() - oponente.getDefesa();

        // Se o dano for positivo, o ataque causa dano
        if (dano > 0) {
            oponente.receberDano(dano);
            System.out.println(getNome() + " causa " + dano + " de dano a " + oponente.getNome() + "!");
        } 
        // Caso contrário, o ataque não conseguiu ultrapassar a defesa
        else {
            System.out.println(getNome() + " não conseguiu causar dano!");
        }
    }
}
