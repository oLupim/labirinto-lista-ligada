package org.example.models;

public class Labirinto<T> {

    public class SalaKnot<T> {
        public T sala;
        public SalaKnot<T> previous;
        public SalaKnot<T> next;

        public SalaKnot(T sala) {
            this.sala = sala;
            this.previous = null;
            this.next = null;
        }
    }

    private SalaKnot<Sala> startLabirinto;
    private SalaKnot<Sala> endLabirinto;

    public Labirinto() {
        this.startLabirinto = null;
        this.endLabirinto = null;
    }

    public void createSala(Sala sala) {
        SalaKnot<Sala> newKnot = new SalaKnot<>(sala);
        if (startLabirinto == null) {
            startLabirinto = newKnot;
            endLabirinto = newKnot;
        } else {
            endLabirinto.next = newKnot;
            newKnot.previous = endLabirinto;
            endLabirinto = newKnot;
        }
    }

    public void showSalas() {
        if (startLabirinto == null) {
            System.out.println("Não há salas para visualização");
            return;
        }

        SalaKnot current = startLabirinto;
        while (current != null) {
            System.out.print("[" + ((Sala) current.sala).getSalaNumber() + "] " + ((Sala)current.sala).getSalaDescription() + "\n");
            current = current.next;
        }
        System.out.println();
    }

    public void removeSala(int salaNumber) {
        if (startLabirinto == null)
            return;

        SalaKnot<Sala> current = startLabirinto;
        while (current != null) {
            if (current.sala.salaNumber == salaNumber) {
                if (current == startLabirinto) {
                    startLabirinto = current.next;
                    if (startLabirinto != null)
                        startLabirinto.previous = null;
                } else if (current == endLabirinto) {
                    endLabirinto = current.previous;
                    if (endLabirinto != null)
                        endLabirinto.next = null;
                } else {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                }
                return;
            }
            current = current.next;
        }
    }

    public Sala getHubSala() {
        SalaKnot<Sala> current = startLabirinto;
        while (current != null) {
            if (current.sala.getSalaNumber() == 0) {
                return current.sala;
            }
            current = current.next;
        }
        return null;
    }

    public Sala getSalaNumber(int number) {
        SalaKnot<Sala> current = startLabirinto;
        while (current != null) {
            if (current.sala.getSalaNumber() == number) {
                return current.sala;
            }
            current = current.next;
        }
        return null;
    }

    public Sala nextSala(Sala currentSala) {
        SalaKnot<Sala> atual = startLabirinto;
        while (atual != null) {
            if (atual.sala.salaNumber == currentSala.salaNumber) {
                return (atual.next != null) ? atual.next.sala : null;
            }
            atual = atual.next;
        }
        if (currentSala == null)
            return null;
        return currentSala.next;
    }

    public Sala previusSala (Sala currentSala){
        SalaKnot<Sala> atual = endLabirinto;
        while (atual != null) {
            if (atual.sala.salaNumber == currentSala.salaNumber) {
                return (atual.previous != null) ? atual.previous.sala : null;
            }
            atual = atual.previous;
        }
        if (currentSala == null)
            return null;
        return currentSala.previous;
    }
}