import java.util.*;

public class Jogo {

    // Scanner para entrada do jogador
    private final Scanner scanner = new Scanner(System.in);

    // Objeto Dado para rolagens aleatórias
    private final Dado dado = new Dado();

    // Referência ao personagem principal do jogador
    private final Personagem jogador;

    // Controla se o jogo ainda está rodando
    private boolean jogoAtivo = true;

    // CONSTRUTOR PRINCIPAL DO JOGO 
    public Jogo() {
        System.out.println("🌌 Bem-vindo ao mundo de ELDARION — terras de magia, aço e destino!");

        // Define nome do personagem
        System.out.print("Digite o nome do seu herói: ");
        String nome = scanner.nextLine();

        // Variável temporária para o jogador
        Personagem tempJogador = null;
        boolean classeValida = false;

        // Escolha de classe pelo jogador
        while (!classeValida) {
            System.out.println("""
            Escolha sua classe:
            1) Guerreiro ⚔️ — Força e honra nas batalhas corpo a corpo.
            2) Mago 🔮 — Manipulador do poder arcano.
            3) Arqueiro 🏹 — Mestre da precisão e da emboscada.
            """);

            System.out.print("Digite o número da sua escolha: ");
            String entrada = scanner.nextLine().trim();

            // Escolha baseada em texto
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

        // Define o jogador final
        jogador = tempJogador;

        // Dá um item inicial ao jogador
        jogador.getInventario().adicionar(
            new Item("Poção de Cura", "Restaura 30 de vida.", Efeito.CURA, 2)
        );

        // Mensagem de introdução narrativa
        System.out.println("\n🗡️ " + jogador.getNome() + " iniciou sua jornada como " + jogador.descreverClasse());
        System.out.println("O sol nasce sobre Eldarion... sua lenda começa agora!\n");
    }

    // INÍCIO DO LOOP PRINCIPAL DO JOGO
    public void iniciar() {
        while (jogoAtivo && jogador.getPontosVida() > 0) {

            // Menu de ações
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
            scanner.nextLine(); // limpa buffer

            // Ações escolhidas
            switch (opcao) {
                case 1 -> explorar();
                case 2 -> usarItem();
                case 3 -> tentarFugir();
                case 4 -> System.out.println(jogador);
                case 5 -> encerrar();
                default -> System.out.println("Opção inválida.");
            }

            // Evento especial: despertar do dragão
            if (jogador.getNivel() >= 5 && jogoAtivo) {
                System.out.println("\n🔥 As montanhas estremecem... o Dragão Ancião desperta!");
                enfrentarDragao();
                return;
            }
        }

        // Caso o jogador morra
        if (jogador.getPontosVida() <= 0) {
            System.out.println("☠️ " + jogador.getNome() + " tombou em batalha. As trevas tomam Eldarion...");
        }
    }

    // SISTEMA DE EXPLORAÇÃO — EVENTOS ALEATÓRIOS
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

    // Encontro com NPCs aleatórios
    private void encontrarNPC() {
        int tipo = dado.rolar(3);

        switch (tipo) {
            case 1 -> {
                System.out.println("\n🧙 Um velho andarilho concede uma bênção!");
                jogador.receberCura(10);
                jogador.setAtaque(jogador.getAtaque() + 1);
            }

            case 2 -> {
                System.out.println("\n💰 Um mercador aparece com itens raros.");
                if (jogador instanceof Guerreiro)
                    jogador.getInventario().adicionar(new Item("Espada Rúnica", "Aumenta seu ataque.", Efeito.BUFF_ATAQUE, 1));
                else if (jogador instanceof Mago)
                    jogador.getInventario().adicionar(new Item("Grimório Antigo", "Amplia seu poder mágico.", Efeito.BUFF_MAGIA, 1));
                else
                    jogador.getInventario().adicionar(new Item("Arco Élfico", "Melhora sua precisão.", Efeito.BUFF_PRECISAO, 1));
            }

            case 3 -> {
                System.out.println("\n👻 Um espírito avisa sobre o dragão e fortalece seu espírito.");
                jogador.setNivel(jogador.getNivel() + 1);
            }
        }
    }

    // Ruína misteriosa com chance de item raro
    private void encontrarRuina() {
        System.out.println("\n🏰 Você encontra uma ruína antiga...");

        if (dado.rolar(6) > 3) {
            Item item = new Item("Amuleto do Destino", "Aumenta todos os atributos.", Efeito.BUFF_TOTAL, 1);
            jogador.getInventario().adicionar(item);
            System.out.println("✨ Você obtém o " + item.getNome() + "!");
        } else {
            System.out.println("Nada acontece...");
        }
    }

    // Encontro de inimigos com dificuldade baseada no nível do jogador
    private void encontrarInimigo() {
        Inimigo inimigo;

        // Escalonamento de inimigos
        if (jogador.getNivel() < 2)
            inimigo = new Inimigo("Lobo Faminto", 40, 10, 2, 1);
        else if (jogador.getNivel() < 3)
            inimigo = new Inimigo("Bandido", 60, 12, 4, 2);
        else if (jogador.getNivel() < 4)
            inimigo = new Inimigo("Necromante", 80, 15, 6, 3);
        else
            inimigo = new Inimigo("Guardião de Lava", 100, 18, 8, 4);

        System.out.println("\n⚔️ Um " + inimigo.getNome() + " aparece!");

        // Loop de combate básico
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

            if (inimigo.getPontosVida() > 0)
                inimigo.atacar(jogador);
        }

        // Vitória do jogador
        if (jogador.getPontosVida() > 0) {
            System.out.println("\n🏆 " + inimigo.getNome() + " foi derrotado!");
            jogador.setNivel(jogador.getNivel() + 1);
            System.out.println("✨ Você subiu para o nível " + jogador.getNivel() + "!");
            jogador.getInventario().adicionar(new Item("Poção de Cura", "Restaura 30 de vida.", Efeito.CURA, 1));
        }
    }

    // Armadilha que causa dano direto
    private void encontrarArmadilha() {
        int dano = dado.rolar(10) + 5;
        jogador.receberDano(dano);
        System.out.println("💥 Uma armadilha explode! Você perde " + dano + " de vida!");
    }

    // Item simples encontrado no chão
    private void encontrarItem() {
        Item item = new Item("Elixir Místico", "Restaura toda a vida.", Efeito.CURA, 1);
        jogador.getInventario().adicionar(item);
        System.out.println("✨ Você encontrou um " + item.getNome() + "!");
    }

    // Sistema de uso de itens
    private void usarItem() {
        System.out.println(jogador.getInventario());
        System.out.print("Digite o nome do item para usar: ");

        String nome = scanner.nextLine();

        // Se conseguiu usar
        if (jogador.getInventario().remover(nome, 1)) {

            if (nome.toLowerCase().contains("poção"))
                jogador.receberCura(30);

            else if (nome.toLowerCase().contains("elixir"))
                jogador.receberCura(jogador.getMaxPontosVida());

            else
                System.out.println("Você sente um poder misterioso fluindo...");

        } else {
            System.out.println("Item não encontrado!");
        }
    }

    // Tentativa de fuga durante combate
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

    // CHEFE FINAL: O DRAGÃO
    private void enfrentarDragao() {
        Inimigo dragao = new Inimigo("Val’Rath, o Dragão Ancião", 220, 28, 10, 10);

        System.out.println("\n🔥 A terra treme... o Dragão Ancião desperta!");
        encontrarInimigoFinal(dragao);
    }

    // Combate especial contra o chefe final
    private void encontrarInimigoFinal(Inimigo dragao) {
        while (dragao.getPontosVida() > 0 && jogador.getPontosVida() > 0) {

            System.out.println("\n1) Atacar | 2) Usar Item");
            int acao = scanner.nextInt();
            scanner.nextLine();

            if (acao == 1)
                jogador.atacar(dragao);
            else
                usarItem();

            if (dragao.getPontosVida() > 0)
                dragao.atacar(jogador);
        }

        // Final (bom ou ruim)
        if (jogador.getPontosVida() > 0) {
            System.out.println("\n🏆🔥 " + jogador.getNome() + " derrotou Val’Rath!");
            System.out.println("🌅 A paz retorna a Eldarion...");
            System.out.println("🎖️ Sua lenda ecoará por gerações!");
        } else {
            System.out.println("\n💀 O Dragão Ancião vence. A esperança de Eldarion se extingue...");
        }
    }

    // Encerrar o jogo voluntariamente
    private void encerrar() {
        System.out.println("🌙 Você decide encerrar sua jornada. Até a próxima, herói.");
        jogoAtivo = false;
    }

    // Método principal — inicia o jogo
    public static void main(String[] args) {
        new Jogo().iniciar();
    }
}
