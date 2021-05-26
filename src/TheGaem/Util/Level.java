package TheGaem.Util;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

public class Level {

    private static int xLen;
    private static int yLen;
    private static int arrayXLen;
    private static int arrayYLen;
    private static int spawnX;
    private static int spawnY;
    private static int mapExitX;
    private static int mapExitY;
    private static int numberOfEnemies;
    private static int[][] finalArray;

    public Level(String filePath, int xOfLevel, int yOfLevel) {
        xLen = xOfLevel;
        yLen = yOfLevel;
        arrayXLen = 5 * xLen;
        arrayYLen = 5 * yLen;
        int zeros = 0;
        int[][] array = new int[yLen][xLen];
        Random r = new Random();
        int bound = xLen * yLen * 2;
        System.out.print("Bound: " + bound);
        for(int y = 0; y < yLen; y++) {
            for(int x = 0; x < xLen; x++) {
                if(r.nextInt(bound) > (zeros * 5)) {
                    array[y][x] = 0;
                    zeros++;
                } else {
                    array[y][x] = 1;
                }
            }
        }
        numberOfEnemies = r.nextInt(arrayXLen / 4);
        System.out.println(" Zeros: " + zeros);
        appendUsingPrintWriter(filePath, array);
    }

    public void setTile(int x, int y, int val) {
        finalArray[y][x] = val;
    }

    public int getArrayXLen() { return arrayXLen;}
    public int getArrayYLen() { return arrayYLen; }
    public int getNumberOfEnemies() { return numberOfEnemies; }

    public static void appendUsingPrintWriter(String filePath, int[][] array) {
        int[][] nextArray = new int[arrayYLen][arrayXLen];
        finalArray = new int[arrayYLen + 2][arrayXLen + 2];
        for(int y = 0; y < arrayYLen; y++) {
            for(int x = 0; x < arrayXLen; x++) {
                nextArray[y][x] = array[y / 5][x / 5];
            }
        }
        nextArray = linkingRooms(nextArray);
        nextArray = addingWalls(nextArray);
        nextArray = leavingTreasures(nextArray);
        for(int y = 0; y < arrayYLen + 2; y++) {
            for (int x = 0; x < arrayXLen + 2; x++) {
                if(y == 0 || y == arrayYLen + 1 || x == 0 || x == arrayXLen + 1) {
                    finalArray[y][x] = 2;
                } else {
                    finalArray[y][x] = nextArray[y - 1][x - 1];
                }
            }
        }
        finalArray = setExit(finalArray);
        try {
            PrintStream print = new PrintStream(filePath);
            for(int y = 0; y < arrayYLen + 2; y++) {
                for (int x = 0; x < arrayXLen + 2; x++) {
                    print.print(finalArray[y][x]);
                }
                print.println();
            }
        } catch (IOException e) {
            System.out.println("ERROR: could not load file: " + filePath);
        }
    }

    public static int[][] addingWalls(int[][] array) {
        for(int y = 0; y < arrayYLen - 1; y++) {
            for(int x = 0; x < arrayXLen - 1; x++) {
                try {
                    if (array[y][x] == 0) {
                        if (array[y + 1][x] != 0) {
                            array[y + 1][x] = 2;
                        }
                        if (array[y - 1][x] != 0) {
                            array[y - 1][x] = 2;
                        }
                        if (array[y][x + 1] != 0) {
                            array[y][x + 1] = 2;
                        }
                        if (array[y][x - 1] != 0) {
                            array[y][x - 1] = 2;
                        }
                        if (array[y + 1][x + 1] != 0) {
                            array[y + 1][x + 1] = 2;
                        }
                        if (array[y - 1][x - 1] != 0) {
                            array[y - 1][x - 1] = 2;
                        }
                        if (array[y - 1][x + 1] != 0) {
                            array[y - 1][x + 1] = 2;
                        }
                        if (array[y + 1][x - 1] != 0) {
                            array[y + 1][x - 1] = 2;
                        }
                    }
                } catch (Exception e) {}
            }
        }
        return array;
    }

    public static int[][] linkingRooms(int[][] array) {
        for(int y = 2; y < arrayYLen - 2; y += 5) {
            for(int x = 2; x < arrayXLen - 2; x += 5) {
                try {
                    if((array[y + 6][x + 1] == 0 || array[y - 4][x + 1] == 0) && (array[y + 6][x + 1] == 0 && array[y - 4][x + 1] == 0)) {
                    } else if (array[y + 1][x + 1] == 0) {
                        for (int distance = arrayXLen - 3; distance > x + 5; distance -= 5) {
                            if (array[y][distance] == 0) {
                                for (int i = x + 2; i < distance; i++) {
                                    array[y][i] = 0;
                                }
                                break;
                            }
                        }
                    }
                    if((array[y + 1][x + 6] == 0 || array[y + 1][x - 4] == 0) && (array[y + 1][x + 6] == 0 && array[y + 1][x - 4] == 0)) {
                    } else if(array[y + 1][x + 1] == 0) {
                        for(int distance = arrayYLen - 3; distance > y + 5; distance -= 5) {
                            if (array[distance][x] == 0) {
                                for (int i = y + 2; i < distance; i++) {
                                    array[i][x] = 0;
                                }
                            }
                            break;
                        }
                    }
                } catch (Exception e) {}
            }
        }
        return array;
    }

    public int getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= arrayXLen || y >= arrayYLen) {
            return 1;
        }
        return finalArray[y][x];
    }

    public static int[][] setExit(int[][] array) {
        int exitX, exitY;
        Random r = new Random();
        do {
            exitX = r.nextInt(arrayXLen - 2) + 1;
            exitY = r.nextInt(arrayYLen - 2) + 1;
        } while(array[exitY][exitX] != 0 || (array[exitY + 1][exitX] != 0 && array[exitY - 1][exitX] != 0) || (array[exitY][exitX + 1] != 0 && array[exitY][exitX - 1] != 0));
        mapExitX = exitX;
        mapExitY = exitY;
        array[mapExitY][mapExitX] = 3;
        do {
            exitX = r.nextInt(arrayXLen - 2) + 1;
            exitY = r.nextInt(arrayYLen - 2) + 1;
        } while(array[exitY][exitX] != 0);
        spawnX = exitX;
        spawnY = exitY;
        array[spawnY][spawnX] = 0;

        return array;
    }

    public static int[][] leavingTreasures(int[][] array) {
        int exitX, exitY;
        int counter;
        Random r = new Random();
        counter = r.nextInt(xLen) + yLen - 2;
        for(int i = 0; i < counter; i++) {
            do {
                exitX = r.nextInt(arrayXLen - 2) + 1;
                exitY = r.nextInt(arrayYLen - 2) + 1;
            } while(array[exitY][exitX] != 0 || (array[exitY + 1][exitX] != 0 && array[exitY - 1][exitX] != 0) || (array[exitY][exitX + 1] != 0 && array[exitY][exitX - 1] != 0));
            array[exitY][exitX] = 4;
        }
        return array;
    }

    public int getSpawnX() { return spawnX; }
    public int getSpawnY() { return spawnY; }
}
