package life;

public  class generationAlgorithm {

    private char[][] tmpGen;

    void createTmpGen(char[][] currentGeneration) {
        // create new array with added rows on the edges
        this.tmpGen = new char[currentGeneration.length + 2][currentGeneration.length + 2];

        for (int i = 0; i < currentGeneration.length + 2; i++) {
            for (int j = 0; j < currentGeneration.length + 2; j++) {

                // FILL CORNERS
                // fill the top-left corner with down-right
                if (i == 0 && j == 0)
                    tmpGen[i][j] = currentGeneration[currentGeneration.length - 1][currentGeneration.length - 1];
                    // fill the top-right corner with down-left
                else if (i == 0 && j == currentGeneration.length + 1)
                    tmpGen[i][j] = currentGeneration[currentGeneration.length - 1][0];
                    // fill the down-right corner with the top-left
                else if (i == currentGeneration.length + 1 && j == currentGeneration.length + 1)
                    tmpGen[i][j] = currentGeneration[0][0];
                    // fill the down-left corner with the top right
                else if (i == currentGeneration.length + 1 && j == 0)
                    tmpGen[i][j] = currentGeneration[0][currentGeneration.length - 1];

                    // FILL COLS AND ROWS
                    // fill the tmpGen up with currGen down
                else if (i == 0)
                    tmpGen[i][j] = currentGeneration[currentGeneration.length - 1][j - 1];
                    // fill the tmpGen down with currGen up
                else if (i == currentGeneration.length + 1)
                    tmpGen[i][j] = currentGeneration[0][j - 1];
                    // fill the tmpGen left with currGen right
                else if (j == 0)
                    tmpGen[i][j] = currentGeneration[i - 1][currentGeneration.length - 1];
                    // fill the tmpGen right with currGen left
                else if (j == currentGeneration.length + 1)
                    tmpGen[i][j] = currentGeneration[i - 1][0];
                    // fill the inside
                else
                    tmpGen[i][j] = currentGeneration[i - 1][j - 1];
            }
        }
    }

    private int countElementNeighbors(char[][] element) {
        // count alive neighbors - elements with 'o'
        int count = 0;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (element[k][l] == 'O')
                    count++;
            }
        }

        if (element[1][1] == 'O')
            count--;

        return count;
    }

    // iterate through tmpGen - and count neighbors in each 3x3 element
    char[][] evolveOneGeneration(char[][] currentGeneration) {

        for (int i = 1; i < tmpGen.length - 1; i++) {
            for (int j = 1; j < tmpGen.length - 1; j++) {
                // fill the element
                char[][] element = {
                        {tmpGen[i - 1][j - 1], tmpGen[i - 1][j], tmpGen[i - 1][j + 1]},
                        {tmpGen[i][j - 1], tmpGen[i][j], tmpGen[i][j + 1]},
                        {tmpGen[i + 1][j - 1], tmpGen[i + 1][j], tmpGen[i + 1][j + 1]}
                };

                int countOfAlive = countElementNeighbors(element);

                if (tmpGen[i][j] == 'O') {
                    // Cell survives
                    if (countOfAlive == 2 || countOfAlive == 3) {
                        currentGeneration[i-1][j-1] = 'O';
                    } else {
                        currentGeneration[i-1][j-1] = ' ';
                    }
                }
                if (tmpGen[i][j] == ' ') {
                    if (countOfAlive == 3) {
                        currentGeneration[i-1][j-1] = 'O';
                    }
                }

            }
        }
        return currentGeneration;
    }
}