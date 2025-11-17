// Importa a classe IOException (necessária pois o método de combate trabalha com entrada do usuário)
import java.io.IOException;

public class DemoCombate {

    public static void main(String[] args) throws IOException {

        // Cria o personagem do jogador:
        Personagem jogador = new Guerreiro("Garret", 100, 15, 8, 1);

        // Cria o personagem inimigo:
        Personagem inimigo = new Inimigo("Logan", 60, 10, 5, 1);

        // Inicia o combate entre jogador e inimigo
        Combate.batalhar(jogador, inimigo);
    }
}
