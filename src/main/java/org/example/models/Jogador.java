package org.example.models;


public class Jogador {
    public String name;
    private String[] inventory;
    private double score;
    public Sala sala;
    private int visitedsalas;
    private String[] descriptionVisitedSalas;

    public Jogador(String name, Sala sala) {
        this.name = name;
        this.sala = sala;
        this.inventory = new String[10];
        this.score = 0.0;
        this.visitedsalas = 0;
        this.descriptionVisitedSalas = new String[100];
    }

    public void getSala() {
        System.out.println();
        System.out.println("====| Sala |====");
        System.out.println(sala.salaDescription);
        System.out.println("Tipo: " + sala.salaType);
    }

    public void getNameAndSala() {
        System.out.println(name);
        if (sala != null) {
            System.out.println("Sala: " + sala.getSalaNumber());
            System.out.println("Informações da sala: " + sala.getSalaDescription());
            System.out.println("Tipo: " + sala.getSalaType());
        } else {
            System.out.println("Erro de execução: Esta sala não existe");
        }
    }

    public void changingSala(Labirinto laririnth, Sala newSala) {
        if (newSala == null) {
            System.out.println("Erro de execução: Você não inicializou o jogo corretamente!");
            return;
        }
        sala = newSala;
        score += 1;
        switch (newSala.salaType) {
            case Normal:
                break;

            case Gift:
                for (int i = 0; i < descriptionVisitedSalas.length; i++) {
                    if (descriptionVisitedSalas[newSala.salaNumber] != newSala.salaDescription) {
                        descriptionVisitedSalas[newSala.salaNumber] = newSala.salaDescription;
                        visitedsalas++;
                        gift();
                        break;
                    }
                }
                break;

            case Trap:
                for (int i = 0; i < descriptionVisitedSalas.length; i++) {
                    if (descriptionVisitedSalas[newSala.salaNumber] != newSala.salaDescription) {
                        descriptionVisitedSalas[newSala.salaNumber] = newSala.salaDescription;
                        visitedsalas++;
                        trap(laririnth);
                        break;
                    }
                }
                break;

            case Exit:
                int numbersVisitedSalas = 0;
                descriptionVisitedSalas[newSala.salaNumber] = newSala.salaDescription;
                visitedsalas++;
                for (int i = 0; i < descriptionVisitedSalas.length; i++) {
                    if (descriptionVisitedSalas[i] != null) {
                        numbersVisitedSalas++;
                    }
                }
                if (numbersVisitedSalas <= 9) {
                    System.out.println("Você chegou a saída, mas ainda não visitou todas as salas");
                    goToHub(laririnth);
                } else {
                    win();
                }
                break;

            default:
                break;
        }

        boolean visited = false;
        for (int i = 0; i < descriptionVisitedSalas.length; i++) {
            if (newSala.getSalaDescription().equals(descriptionVisitedSalas[i])) {
                visited = true;
                break;
            }
        }
        if (!visited) {
            for (int i = 0; i < descriptionVisitedSalas.length; i++) {
                if (descriptionVisitedSalas[i] == null || descriptionVisitedSalas[i].isEmpty()) {
                    descriptionVisitedSalas[i] = newSala.getSalaDescription();
                    visitedsalas++;
                    break;
                }
            }
        }
    }

    public void addItem(String item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                return;
            }
        }
        System.out.println("Inventário cheio. Não foi possível colocar o item: " + item);
    }

    public void trap(Labirinto Labirinto) {
        double trap = Math.random() * 5;
        if (score > trap) {
            score -= trap;
            System.out.println("Você caiu em uma armadilha! Pontuação: " + score);
        } else {
            score = 0.00;
            System.out.println("Você caiu em uma armadilha, mas não tem pontos o suficientes!");
            goToHub(Labirinto);
        }
    }

    public void gift() {
        int luck = (int) (Math.random() * 10);
        if (luck < 5) {
            score += luck;
            System.out.println("Que sorte! Você recebeu " + luck + " pontos");
        } else {
            String[] gifts = new String[] { "Machado", "Espada", "Escudo", "Poção", "Capacete" };
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] == null) {
                    addItem(gifts[luck - 5]);
                    System.out
                            .println("Que sorte! Você recebeu um item " + inventory[i] + " Inventário atual:  " + (i + 1));
                    break;
                }
            }
        }
    }

    public boolean win() {
        boolean game = true;
        if (sala.salaType.name() == "Exit") {
            game = false;

            System.out.println();
            System.out.println("===| Final |===");
            System.out.println(name);
            System.out.println("Pontuação Final: " + score);
            System.out.println("Inventário: ");
            for (int i = 0; i < inventory.length; i++) {
                int itemNumber = i + 1;
                if (inventory[i] != null) {
                    System.out.println("   Item: " + itemNumber + " - " + inventory[i]);
                }
            }
            System.out.println("Quantidade de Salas Visitadas: " + visitedsalas);

            System.out.println("Salas Visitadas:");
            for (int i = 0; i < descriptionVisitedSalas.length; i++) {
                if (descriptionVisitedSalas[i] != null) {
                    System.out.println("Sala: " + descriptionVisitedSalas[i]);
                }
            }
            System.exit(0);
            return game;
        }
        return game;
    }

    public void salasVisitadas() {
        System.out.println();
        System.out.println("Quantidade de Salas Visitadas: " + visitedsalas);

        System.out.println("Salas Visitadas:");
        for (int i = 0; i < descriptionVisitedSalas.length; i++) {
            if (descriptionVisitedSalas[i] != null) {
                System.out.println("Sala: " + descriptionVisitedSalas[i]);
            }
        }
        System.out.println();
    }

    public void goToHub(Labirinto Labirinto) {
        Sala hub = Labirinto.getHubSala();
        if (hub != null) {
            this.sala = hub;
            System.out.println("Retornando à Sala Inicial: " + hub.getSalaDescription());
        } else {
            System.out.println("Sala Inicial não encontrada!");
        }
    }
}