package Horses;

import java.util.ArrayList;

public class Hippodrome {
    public ArrayList<Horse> horses = new ArrayList<>();
    public static Hippodrome game;

    public static void main(String[] args) {
        game = new Hippodrome();

        Horse horse1 = new Horse("Кобылка", 3, 0);
        Horse horse2 = new Horse("Юлий", 3, 0);
        Horse horse3 = new Horse("Плотва", 3, 0);
        Horse horse4 = new Horse("Гарбунёк", 3, 0);

        game.getHorses().add(horse1);
        game.getHorses().add(horse2);
        game.getHorses().add(horse3);
        game.getHorses().add(horse4);

        game.run();
        game.printWinner();
    }

    public ArrayList<Horse> getHorses() {
        return this.horses;
    }

    public void move() {
        for (Horse horse : horses ) {
            horse.move();
        }
    }

    public void print() {
        System.out.println("_______________________________________________________________________________________________________________________________________________________=====");

        for (Horse horse : horses ) {
            horse.print();
        }
        System.out.println("_______________________________________________________________________________________________________________________________________________________=====");
    }

    public void run() {
        for(int i = 1; i <= 100; i++) {
            move();
            print();
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                System.out.println("oops");
            }
        }
    }

    public Horse getWinner() {
        Horse winner = new Horse("NoName", 0, 0);
        for (Horse horse : horses) {
            if (horse.distance > winner.distance) {
                winner = horse;
            }
        }
        return winner;
    }

    public void printWinner() {
        System.out.println("Winner is " + getWinner().getName() + "!");
    }
}
