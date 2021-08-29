package life;

import java.util.Random;
import java.util.Scanner;

public class World {
    private final Scanner scanner = new Scanner(System.in);
    private int size;
    private int seed;
    private char[][] world;

    public void setSize(int size) {
        this.size = size;
    }
    public void setSeed(int seed) {
        this.seed = seed;
    }

    void setUniverseProperties(){
        System.out.println("setUniverseProp");
        setSize(scanner.nextInt());
        setSeed(scanner.nextInt());
    }

    void populate() {
        System.out.println("Populate");
        Random random = new Random(seed);
        this.world = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    world[i][j] = 'O';
                }
                else {
                    world[i][j] = ' ';
                }
            }
        }


    }



    public void showUniverse() {
        System.out.println("Showing universe: ");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(world[i][j] + "");
            }
            System.out.println();
        }
    }

}
