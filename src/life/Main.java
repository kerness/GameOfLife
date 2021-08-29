package life;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        run();
    }

    public static void createWorld() {
        int size = scanner.nextInt();
        int seed = scanner.nextInt();
        Random random = new Random(seed);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (random.nextBoolean()) {
                    System.out.print("O");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

    public static void run() {
        World world = new World();
        world.setUniverseProperties();
        world.populate();
        world.showUniverse();
    }
}

