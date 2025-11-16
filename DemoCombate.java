//DemoCombate.java

import java.io.IOException;

public class DemoCombate {
	public static void main(String[] args) throws IOException {
		Personagem jogador = new Guerreiro("Garret", 100, 15, 8, 1);
		Personagem inimigo = new Inimigo("Logan", 60, 10, 5, 1);

		Combate.batalhar(jogador, inimigo);
	}
}