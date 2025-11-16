import java.util.*;

public class Jogo {

    private final Scanner scanner = new Scanner(System.in);
    private final Dado dado = new Dado();
    private final Personagem jogador;
    private boolean jogoAtivo = true;

    public Jogo() {
        System.out.println("🌌 Bem-vindo ao mundo de ELDARION — terras de magia, aço e destino!");
        System.out.print("Digite o nome do seu herói: ");
        String nome = scanner.nextLine();

        // --- Escolha de classe ---
        Personagem tempJogador = null;
        boolean classeValida = false;

        while (!classeValida) {
            System.out.println("""
            Escolha sua classe:
            1) Guerreiro ⚔️ — Força e honra nas batalhas corpo a corpo.
            2) Mago 🔮 — Manipulador do poder arcano.
            3) Arqueiro 🏹 — Mestre da precisão e da emboscada.
            """);
            System.out.print("Digite o número da sua escolha: ");
            String entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1" -> {
                    tempJogador = new Guerreiro(nome, 100, 15, 5, 1);
                    classeValida = true;
                }
                case "2" -> {
                    tempJogador = new Mago(nome, 80, 12, 4, 1, 50, 10);
                    classeValida = true;
                }
                case "3" -> {
                    tempJogador = new Arqueiro(nome, 90, 14, 5, 1, 0.25, 3);
                    classeValida = true;
                }
                default -> System.out.println("\n❌ Opção inválida! Escolha 1, 2 ou 3.\n");
            }
        }

        jogador = tempJogador;
        jogador.getInventario().adicionar(new Item("Poção de Cura", "Restaura 30 de vida.", Efeito.CURA, 2));

        System.out.println("\n🗡️ " + jogador.getNome() + " iniciou sua jornada como " + jogador.descreverClasse());
        System.out.println("O sol nasce sobre Eldarion... sua lenda começa agora!\n");
    }

    public void iniciar() {
        while (jogoAtivo && jogador.getPontosVida() > 0) {
            System.out.println("""
            === MENU ===
            1) Explorar
            2) Usar Item
            3) Fugir
            4) Status
            5) Encerrar jornada
            """);
            System.out.print("Escolha uma ação: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> explorar();
                case 2 -> usarItem();
                case 3 -> tentarFugir();
                case 4 -> System.out.println(jogador);
                case 5 -> encerrar();
                default -> System.out.println("Opção inválida.");
            }

            if (jogador.getNivel() >= 5 && jogoAtivo) {
                System.out.println("\n🔥 As montanhas estremecem... o Dragão Ancião desperta!");
                enfrentarDragao();
                return;
            }
        }

        if (jogador.getPontosVida() <= 0) {
            System.out.println("☠️ " + jogador.getNome() + " tombou em batalha. As trevas tomam Eldarion...");
        }
    }

    // ---------------- EXPLORAÇÃO ----------------
    private void explorar() {
        int evento = dado.rolar(10);
        switch (evento) {
            case 1, 2 -> encontrarInimigo();
            case 3 -> encontrarArmadilha();
            case 4 -> encontrarItem();
            case 5 -> encontrarNPC();
            case 6 -> encontrarRuina();
            default -> System.out.println("Você caminha por vales silenciosos... apenas o vento responde.");
        }
    }

    private void encontrarNPC() {
        int tipo = dado.rolar(3);
        switch (tipo) {
            case 1 -> {
                System.out.println("\n🧙 Um velho andarilho se aproxima: \"Vejo coragem em seus olhos, jovem herói.\"");
                System.out.println("Ele oferece uma bênção misteriosa (+10 de vida e +1 de ataque).");
                jogador.receberCura(10);
                jogador.setAtaque(jogador.getAtaque() + 1);
            }
            case 2 -> {
                System.out.println("\n💰 Um mercador viajante surge: \"Tenho algo que pode lhe interessar.\"");
                if (jogador instanceof Guerreiro)
                    jogador.getInventario().adicionar(new Item("Espada Rúnica", "Aumenta seu ataque.", Efeito.BUFF_ATAQUE, 1));
                else if (jogador instanceof Mago)
                    jogador.getInventario().adicionar(new Item("Grimório Antigo", "Amplia seu poder mágico.", Efeito.BUFF_MAGIA, 1));
                else
                    jogador.getInventario().adicionar(new Item("Arco Élfico", "Melhora sua precisão.", Efeito.BUFF_PRECISAO, 1));
                System.out.println("Você recebeu um item especial!");
            }
            case 3 -> {
                System.out.println("\n👻 Um espírito antigo aparece e sussurra: \"O dragão... ele retorna...\"");
                System.out.println("Você sente um arrepio. Sua determinação aumenta (+1 nível).");
                jogador.setNivel(jogador.getNivel() + 1);
            }
        }
    }

    private void encontrarRuina() {
        System.out.println("\n🏰 Você encontra uma ruína antiga coberta por musgo...");
        System.out.println("Dentro, uma inscrição brilha: 'Somente o digno encontrará poder'.");
        if (dado.rolar(6) > 3) {
            Item item = new Item("Amuleto do Destino", "Aumenta todos os atributos.", Efeito.BUFF_TOTAL, 1);
            jogador.getInventario().adicionar(item);
            System.out.println("✨ Você obtém o " + item.getNome() + "!");
        } else {
            System.out.println("Nada acontece... talvez ainda não seja o momento.");
        }
    }

    private void encontrarInimigo() {
        Inimigo inimigo;
        if (jogador.getNivel() < 2) inimigo = new Inimigo("Lobo Faminto", 40, 10, 2, 1);
        else if (jogador.getNivel() < 3) inimigo = new Inimigo("Bandido", 60, 12, 4, 2);
        else if (jogador.getNivel() < 4) inimigo = new Inimigo("Necromante", 80, 15, 6, 3);
        else inimigo = new Inimigo("Guardião de Lava", 100, 18, 8, 4);

        System.out.println("\n⚔️ Um " + inimigo.getNome() + " bloqueia seu caminho!");

        while (inimigo.getPontosVida() > 0 && jogador.getPontosVida() > 0) {
            System.out.println("\n1) Atacar | 2) Usar Item | 3) Fugir");
            int acao = scanner.nextInt();
            scanner.nextLine();

            switch (acao) {
                case 1 -> jogador.atacar(inimigo);
                case 2 -> usarItem();
                case 3 -> {
                    if (tentarFugir()) return;
                }
            }

            if (inimigo.getPontosVida() > 0) inimigo.atacar(jogador);
        }

        if (jogador.getPontosVida() > 0) {
            System.out.println("\n🏆 " + inimigo.getNome() + " foi derrotado!");
            jogador.setNivel(jogador.getNivel() + 1);
            System.out.println("✨ " + jogador.getNome() + " subiu para o nível " + jogador.getNivel() + "!");
            jogador.getInventario().adicionar(new Item("Poção de Cura", "Restaura 30 de vida.", Efeito.CURA, 1));
        }
    }

    private void encontrarArmadilha() {
        int dano = dado.rolar(10) + 5;
        jogador.receberDano(dano);
        System.out.println("💥 Uma armadilha antiga é acionada! Você perde " + dano + " de vida!");
    }

    private void encontrarItem() {
        Item item = new Item("Elixir Místico", "Restaura toda a vida.", Efeito.CURA, 1);
        jogador.getInventario().adicionar(item);
        System.out.println("✨ Você encontrou um " + item.getNome() + "!");
    }

    private void usarItem() {
        System.out.println(jogador.getInventario());
        System.out.print("Digite o nome do item para usar: ");
        String nome = scanner.nextLine();

        if (jogador.getInventario().remover(nome, 1)) {
            if (nome.toLowerCase().contains("poção")) jogador.receberCura(30);
            else if (nome.toLowerCase().contains("elixir")) jogador.receberCura(jogador.getMaxPontosVida());
            else System.out.println("Você sente um poder misterioso fluindo...");
        } else {
            System.out.println("Item não encontrado!");
        }
    }

    private boolean tentarFugir() {
        int resultado = dado.rolar(6);
        if (resultado >= 4) {
            System.out.println("🏃 Você escapou por pouco!");
            return true;
        } else {
            System.out.println("❌ Falha na fuga! O inimigo te atinge!");
            jogador.receberDano(10);
            return false;
        }
    }

    private void enfrentarDragao() {
        Inimigo dragao = new Inimigo("Val’Rath, o Dragão Ancião", 220, 28, 10, 10);
        System.out.println("\n🔥 O chão racha e chamas saem das profundezas!");
        System.out.println("🐉 " + dragao.getNome() + " ruge, fazendo o mundo tremer!");
        encontrarInimigoFinal(dragao);
    }

    private void encontrarInimigoFinal(Inimigo dragao) {
        while (dragao.getPontosVida() > 0 && jogador.getPontosVida() > 0) {
            System.out.println("\n1) Atacar | 2) Usar Item");
            int acao = scanner.nextInt();
            scanner.nextLine();

            if (acao == 1) jogador.atacar(dragao);
            else usarItem();

            if (dragao.getPontosVida() > 0) dragao.atacar(jogador);
        }

        if (jogador.getPontosVida() > 0) {
            System.out.println("\n🏆🔥 " + jogador.getNome() + " derrotou Val’Rath!");
            System.out.println("🌅 As chamas se dissipam e o sol retorna a Eldarion...");
            System.out.println("🎖️ Sua lenda será lembrada por eras!");
        } else {
            System.out.println("\n💀 O Dragão Ancião triunfa. A esperança de Eldarion perece nas cinzas...");
        }
    }

    private void encerrar() {
        System.out.println("🌙 Você decide descansar e encerrar sua jornada. Que os ventos o guiem, herói.");
        jogoAtivo = false;
    }

    public static void main(String[] args) {
        new Jogo().iniciar();
    }
}
