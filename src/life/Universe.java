
package life;


import java.util.Random;
import java.util.Scanner;

public class Universe {
    private final Scanner scanner = new Scanner(System.in);
    private int size;
    private long seed;
    private int iterations;
    private char[][] currGen;


    private final generationAlgorithm genAlg = new generationAlgorithm();

    public void setCurrGen(char[][] currGen) {
        this.currGen = currGen;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public void setSeed(int seed) {
        this.seed = seed;
    }
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    public int getIterations() {
        return iterations;
    }
    public char[][] getCurrentUniverse() {
        return currGen;
    }


    void setUniverseProperties() {
        setSize(scanner.nextInt());
        setSeed(scanner.nextInt());
        setIterations(scanner.nextInt());
    }

    void initUniverse() {
        Random random = new Random(seed);
        this.currGen = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    currGen[i][j] = 'O';
                } else {
                    currGen[i][j] = ' ';
                }
            }
        }
    }

    // i - columns ; j - rows
    public void showUniverse(char[][] uni) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(uni[i][j]);
            }
            System.out.println();
        }
    }

    void evolveUniverse() {
        genAlg.createTmpGen(currGen);
        this.setCurrGen((genAlg.evolveOneGeneration(currGen)));

    }
}