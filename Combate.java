//Combate.java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Combate {

    public static void batalhar(Personagem jogador, Personagem inimigo) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n⚔️  Um inimigo apareceu: " + inimigo.getNome() + "!");
        System.out.println("Prepare-se para o combate!\n");

        while (jogador.getPontosVida() > 0 && inimigo.getPontosVida() > 0) {
            System.out.println("\n=== STATUS ===");
            System.out.println(jogador.getNome() + " - HP: " + jogador.getPontosVida());
            System.out.println(inimigo.getNome() + " - HP: " + inimigo.getPontosVida());
            System.out.println("================");
            System.out.println("1 - Atacar");
            System.out.println("2 - Tentar fugir");
            System.out.print("Escolha sua ação: ");

            String opcao = br.readLine();

            if (opcao.equals("1")) {
                jogador.atacar(inimigo);
            } else if (opcao.equals("2")) {
                int tentativa = Dado.rolar(6);
                if (tentativa >= 5) {
                    System.out.println("🏃 Você conseguiu fugir!");
                    return;
                } else {
                    System.out.println("❌ Falha na fuga!");
                }
            } else {
                System.out.println("Opção inválida!");
                continue;
            }

            if (inimigo.getPontosVida() <= 0) {
                System.out.println("\n🏆 " + jogador.getNome() + " venceu o combate!");
                break;
            }

            // Turno do inimigo
            System.out.println("\n➡️  Turno do inimigo...");
            inimigo.atacar(jogador);

            if (jogador.getPontosVida() <= 0) {
                System.out.println("\n💀 " + jogador.getNome() + " foi derrotado!");
                break;
            }
        }
    }
}