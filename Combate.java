import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Combate {

    public static void batalhar(Personagem jogador, Personagem inimigo) throws IOException {

        // Lê entrada do teclado 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Mensagens iniciais do combate
        System.out.println("\n⚔️  Um inimigo apareceu: " + inimigo.getNome() + "!");
        System.out.println("Prepare-se para o combate!\n");

        // Loop do combate: continua enquanto ambos ainda estiverem vivos
        while (jogador.getPontosVida() > 0 && inimigo.getPontosVida() > 0) {

            // Mostra o status atual dos dois personagens
            System.out.println("\n=== STATUS ===");
            System.out.println(jogador.getNome() + " - HP: " + jogador.getPontosVida());
            System.out.println(inimigo.getNome() + " - HP: " + inimigo.getPontosVida());
            System.out.println("================");

            // Ações disponíveis ao jogador
            System.out.println("1 - Atacar");
            System.out.println("2 - Tentar fugir");
            System.out.print("Escolha sua ação: ");

            // Recebe escolha do jogador
            String opcao = br.readLine();

            // --- AÇÃO 1: Atacar ---
            if (opcao.equals("1")) {
                jogador.atacar(inimigo);
            }

            // --- AÇÃO 2: Fugir ---
            else if (opcao.equals("2")) {

                // Rola um dado D6 para verificar tentativa de fuga
                int tentativa = Dado.rolar(6);

                // Se o jogador tirar 5 ou 6, a fuga é bem-sucedida
                if (tentativa >= 5) {
                    System.out.println("🏃 Você conseguiu fugir!");
                    return; // Encerra o combate
                } else {
                    System.out.println("❌ Falha na fuga!");
                }
            }

            // --- AÇÃO INVÁLIDA ---
            else {
                System.out.println("Opção inválida!");
                continue; // Volta ao início do turno sem penalizar o jogador
            }

            // Verifica se o inimigo foi derrotado após ataque do jogador
            if (inimigo.getPontosVida() <= 0) {
                System.out.println("\n🏆 " + jogador.getNome() + " venceu o combate!");
                break;
            }

            // --- TURNO DO INIMIGO ---
            System.out.println("\n➡️  Turno do inimigo...");
            inimigo.atacar(jogador);

            // Verifica se o jogador foi derrotado
            if (jogador.getPontosVida() <= 0) {
                System.out.println("\n💀 " + jogador.getNome() + " foi derrotado!");
                break;
            }
        }
    }
}
