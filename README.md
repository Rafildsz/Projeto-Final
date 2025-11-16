# Projeto_Final_POO_G06

# ELDARION: RPG de Texto (Java/POO)

Este projeto é um RPG de texto simples desenvolvido em Java, focado na aplicação e demonstração de conceitos fundamentais da Programação Orientada a Objetos (POO), como Herança, Polimorfismo, Composição e Encapsulamento.

## Como Compilar e Executar

O projeto é composto por múltiplas classes Java interconectadas. Para compilar e executar, siga os passos abaixo:

### 1. Compilação
Salve a pasta com os arquivos do projeto e a abra no site GDB e execute(Run).

### 2. Descrição das classes 
- Personagem:	Abstrata, define a base e o contrato de combate para todas as entidades.
- Guerreiro, Mago, Arqueiro:	Concreta, classes jogáveis com lógicas de ataque e atributos específicos.
- Inimigo: Concreta,	entidade a ser combatida no jogo.
- Item:	Concreta,	objeto consumível/equipável.
- Inventario:	Concreta,	gerencia a coleção de itens.
- Jogo:	Principal, contém o loop principal do jogo, navegação e eventos.
- Combate:	Utilitária, implementa a lógica de turno entre duas entidades.
- Dado:	Utilitária/Estática, centraliza a rolagem de dados para aleatoriedade.