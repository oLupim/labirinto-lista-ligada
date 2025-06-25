package org.example;

import java.util.Scanner;

import org.example.models.Labirinto;
import org.example.models.Jogador;
import org.example.models.Sala;
import org.example.models.TypeSala;

public class Main {
    private static int numbersSalas = 1;

    public static void main(String[] args) {

        Labirinto labirinto = new Labirinto();

        Scanner scanner = new Scanner(System.in);

        boolean autoCreate = true;
        int salasCriadosByUser = 10;

        System.out.println("____Desafio do Labirinto____");
        System.out.println("1 - Iniciar");
        System.out.println("0 - Sair");
        int option = scanner.nextInt();

        boolean game = false;
        boolean formSalaGame = true;

        Sala sala0 = new Sala(0, TypeSala.Normal, "Sala central do labirinto");
        Sala sala1 = new Sala(1, TypeSala.Normal, "Entrada escura de pedra úmida.");
        Sala sala2 = new Sala(2, TypeSala.Trap, "Chão falso com espinhos escondidos.");
        Sala sala4 = new Sala(3, TypeSala.Normal, "Corredor com tochas apagadas.");
        Sala sala5 = new Sala(4, TypeSala.Award, "Sala com um baú de ouro reluzente.");
        Sala sala6 = new Sala(5, TypeSala.Trap, "Armadilha de flechas nas paredes.");
        Sala sala7 = new Sala(6, TypeSala.Normal, "Passagem estreita cheia de teias de aranha.");
        Sala sala8 = new Sala(7, TypeSala.Award, "Fonte mágica que restaura a vida.");
        Sala sala9 = new Sala(8, TypeSala.Trap, "Estátuas que disparam laser quando ativadas.");
        Sala sala10 = new Sala(99, TypeSala.Exit, "Porta rúnica que leva à saída da dungeon.");

        labirinto.createSala(sala0);
        labirinto.createSala(sala1);
        labirinto.createSala(sala2);
        labirinto.createSala(sala4);
        labirinto.createSala(sala5);
        labirinto.createSala(sala6);
        labirinto.createSala(sala7);
        labirinto.createSala(sala8);
        labirinto.createSala(sala9);

        Jogador jogador = new Jogador(null, sala0);

        if (option == 1) {
            jogador.changingSala(labirinto, sala0);
            game = true;
        } else {
            formSalaGame = false;
        }
        while (formSalaGame) {

            System.out.println("  _| Criar Salas |_");
            System.out.println("1 - Criar uma sala");
            if (autoCreate)
                System.out.println("2 - AutoCriar 9 Salas");
            System.out.println("9 - Começar o jogo");
            int optionSala = scanner.nextInt();

            switch (optionSala) {
                case 2:
                    if (autoCreate) {
                        System.out.println("Salas criadas com sucesso!");

                        clearConsole();
                        numbersSalas += 9;
                        autoCreate = false;
                    } else {
                        System.out.println("Você já usou o seu auto create");
                    }
                    break;

                case 1:
                    System.out.println("Digite nome da sala: ");
                    String salaName = scanner.next();
                    System.out.println();

                    System.out.println("Selecione o tipo da sala");
                    int i = 0;
                    for (TypeSala type : TypeSala.values()) {
                        i++;
                        System.out.println(i + " - para: " + type);
                    }
                    String selectType = "";
                    int typeSalaOption = scanner.nextInt();

                    switch (typeSalaOption) {
                        case 1:
                            selectType = TypeSala.Normal.name();
                            break;
                        case 2:
                            selectType = TypeSala.Trap.name();
                            break;

                        case 3:
                            selectType = TypeSala.Award.name();
                            break;

                        case 4:
                            selectType = TypeSala.Exit.name();
                            break;
                    }

                    salasCriadosByUser++;

                    Sala newSala = new Sala(salasCriadosByUser, TypeSala.valueOf(selectType), salaName);

                    labirinto.createSala(newSala);
                    System.out.println();
                    System.out.println("Sala criada: " + salaName);
                    clearConsole();
                    numbersSalas++;
                    break;

                case 9:
                    if (numbersSalas >= 10) {
                        formSalaGame = false;
                    } else {
                        System.out.println();
                        System.out
                                .println("Para iniciar o jogo, é necessário criar no minimo 10 salas, você tem apenas "
                                        + numbersSalas + " salas criadas");
                    }
                    break;
            }
            clearConsole();

        }
        if (autoCreate) {
            labirinto.removeSala(1);
            labirinto.removeSala(2);
            labirinto.removeSala(3);
            labirinto.removeSala(4);
            labirinto.removeSala(5);
            labirinto.removeSala(6);
            labirinto.removeSala(7);
            labirinto.removeSala(8);

            numbersSalas -= 8;
        } else {
            labirinto.createSala(sala10);}

        while (game) {
            System.out.println("Start Game");
            String mensageName = (jogador.name == null) ? "1 - Inserir nome do jogador"
                    : "1 - Editar nome do jogador";
            System.out.println(mensageName);
            System.out.println("2 - Mudar de Sala");
            System.out.println("3 - Avançar de Sala");
            System.out.println("4 - Voltar uma Sala");
            System.out.println("5 - Sala atual do Jogador");
            System.out.println("6 - Listar todas as Salas");
            System.out.println("7 - Listar salas já visitadas");
            System.out.println("0 - Sair");
            int optionGame = scanner.nextInt();

            switch (optionGame) {
                case 1:
                    System.out.println("Insira o nome do seu jogador:");
                    String name = scanner.next();
                    jogador.name = name;
                    clearConsole();
                    break;

                case 2:
                    Sala newSala = null;
                    System.out.println("Escolha o número da sala que deseja se mover: ");
                    int numberSalaChosen = scanner.nextInt();
                    newSala = labirinto.getSalaNumber(numberSalaChosen);
                    jogador.changingSala(labirinto, newSala);
                    jogador.getSala();
                    clearConsole();
                    break;

                case 3:
                    Sala nextSala = labirinto.nextSala(jogador.sala);
                    jogador.changingSala(labirinto, nextSala);
                    jogador.getSala();
                    game = jogador.win();
                    clearConsole();
                    break;

                case 4:
                    Sala previusSala = labirinto.previusSala(jogador.sala);
                    jogador.changingSala(labirinto, previusSala);
                    jogador.getSala();
                    game = jogador.win();
                    break;

                case 5:
                    jogador.getSala();
                    break;

                case 6:
                    labirinto.showSalas();
                    break;

                case 7:
                    jogador.salasVisitadas();
                    break;

                case 0:
                    game = false;
                    System.out.println("Obrigado por jogar!");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
        scanner.close();
    }

    public final static void clearConsole() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public final static int getNumbersSalasCreated(){
        return numbersSalas;
    }
}