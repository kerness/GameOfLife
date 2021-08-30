package life;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Universe universe = new Universe();
        universe.setUniverseProperties();
        universe.populate();
        universe.showUniverse(universe.getCurrentUniverse());
        universe.evolve();
        universe.showUniverse(universe.getNextGeneration());
        universe.getGenAlg().createTmpGen();
        universe.getGenAlg().printGenTmp();
        universe.getGenAlg().evolveOneGeneration();
    }
}

class Universe {
    private final Scanner scanner = new Scanner(System.in);
    private int size;
    private int seed;
    private char[][] currentUniverse;
    private char[][] nextGeneration;
    private final generationAlgorithm genAlg = new generationAlgorithm();

    public void setSize(int size) {
        this.size = size;
    }
    public void setSeed(int seed) { this.seed = seed; }

    public char[][] getCurrentUniverse() {
        return currentUniverse;
    }
    public char[][] getNextGeneration() {
        return nextGeneration;
    }

    generationAlgorithm getGenAlg() {
        return this.genAlg;
    }

    void setUniverseProperties() {
        System.out.println("setUniverseProp");
        setSize(scanner.nextInt());
        setSeed(scanner.nextInt());
    }

    void populate() {
        System.out.println("Populate");
        Random random = new Random(seed);
        this.currentUniverse = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    currentUniverse[i][j] = 'O';
                } else {
                    currentUniverse[i][j] = '-';
                }
            }
        }
    }

    void evolve() {
        genAlg.copyCurrToNext(this.currentUniverse, this.size);
        nextGeneration = genAlg.getNextGen();
    }

    // i - columns ; j - rows
    public void showUniverse(char[][] uni) {
        System.out.println("Showing universe: ");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                System.out.print(uni[i][j] + " ");
            }
            System.out.println();
        }
    }
}


class generationAlgorithm {

    private int neighbors;
    private char[][] currGen;
    private char[][] nextGen;
    private char[][] tmpGen;

    char[][] getNextGen() {
        return this.nextGen;
    }
    char[][] getCurrGen() {
        return this.currGen;
    }
    public char[][] getTmpGen() {
        return tmpGen;
    }

    void copyCurrToNext(char[][] currentGeneration, int size) {
        this.currGen = currentGeneration;
        this.nextGen = new char[size][size];
        for (int i = 0; i < currentGeneration.length; i++) {
            for (int j = 0; j < currentGeneration.length; j++) {
                this.nextGen[i][j] = currentGeneration[i][j];
            }
        }
    }

    void createTmpGen() {
        // create new array with added rows on the edges
        this.tmpGen = new char[currGen.length + 2][currGen.length + 2];

        for (int i = 0; i < currGen.length + 2; i++) {
            for (int j = 0; j < currGen.length + 2; j++) {

                // FILL CORNERS
                // fill the top-left corner with down-right
                if (i == 0 && j == 0)
                    tmpGen[i][j] = currGen[currGen.length - 1][currGen.length - 1];
                    // fill the top-right corner with down-left
                else if (i == 0 && j == currGen.length + 1)
                    tmpGen[i][j] = currGen[currGen.length - 1][0];
                    // fill the down-right corner with the top-left
                else if (i == currGen.length + 1 && j == currGen.length + 1)
                    tmpGen[i][j] = currGen[0][0];
                    // fill the down-left corner with the top right
                else if (i == currGen.length + 1 && j == 0)
                    tmpGen[i][j] = currGen[0][currGen.length - 1];

                    // FILL COLS AND ROWS
                    // fill the tmpGen up with currGen down
                else if (i == 0)
                    tmpGen[i][j] = currGen[currGen.length - 1][j - 1];
                    // fill the tmpGen down with currGen up
                else if (i == currGen.length + 1)
                    tmpGen[i][j] = currGen[0][j - 1];
                    // fill the tmpGen left with currGen right
                else if (j == 0)
                    tmpGen[i][j] = currGen[i - 1][currGen.length - 1];
                    // fill the tmpGen right with currGen left
                else if (j == currGen.length + 1)
                    tmpGen[i][j] = currGen[i - 1][0];
                    // fill the inside
                else
                    tmpGen[i][j] = currGen[i - 1][j - 1];
            }
        }

    }

    int countElementNeighbors(char[][] element) {
        // count alive neighbors - elements with 'o'
        int count = 0;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (element[k][l] == 'O')
                    count++;
            }
        }
        return count;
    }

    // iterate through tmpGen - and count neighbors in each 3x3 element
    void evolveOneGeneration() {
        int countOfAlive = 0;
        for (int i = 1; i < tmpGen.length - 1; i++) {
            for (int j = 1; j < tmpGen.length - 1; j++) {
                // fill the element
                char[][] element = {
                        {tmpGen[i - 1][j - 1], tmpGen[i - 1][j], tmpGen[i - 1][j + 1]},
                        {tmpGen[i][j - 1], tmpGen[i][j], tmpGen[i][j + 1]},
                        {tmpGen[i + 1][j - 1], tmpGen[i + 1][j], tmpGen[i + 1][j + 1]}
                };
                System.out.println("This is element created based on [" + i + ", " + j + "] index: ");
                printOneElement(element);

                countOfAlive = countElementNeighbors(element);

                // logic for progression
                System.out.println("Alive neighbors: " + countOfAlive);
                if (countOfAlive == 3) {

                    if (tmpGen[i][j] == '-')
                        System.out.println("Reborn!");
                    else
                        System.out.println("Survives");
                } else if (countOfAlive == 2) {
                    System.out.println("Survives");
                } else {
                    System.out.println("Dead");
                }

            }
        }
    }

    void printOneElement(char[][] element) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(element[i][j] + " ");
            }
            System.out.println();
        }
    }

    void printGenTmp() {
        System.out.println("TMP GEN");
        for (int i = 0; i < currGen.length + 2; i++) {
            for (int j = 0; j < currGen.length + 2; j++) {
                System.out.print(tmpGen[i][j] + " ");
            }
            System.out.println();
        }
    }
}



