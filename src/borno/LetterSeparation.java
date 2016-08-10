package borno;

import image.ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by Jobayer on 8/6/2016.
 */
public class LetterSeparation {

    public static void separateLetter() throws IOException {
        String basePath = "F:\\Academic\\semester 8\\Image Processing\\Workspace\\Assignments\\src\\borno\\resources\\";
        BufferedImage image = ImageProcessing.readImage(basePath + "alo.jpg");

        int[][][] rgb = ImageProcessing.getRGB(image);
        int[][] gray = ImageProcessing.rgbToGray(rgb);
        int[][] blackAndWhite = ImageProcessing.grayToBlackAndWhite(gray, 200);
        int width = blackAndWhite.length;
        int height = blackAndWhite[0].length;
        int[][] binary = new int[width][height];
        int[] frequencyPerRow = new int[height];

        // find frequency per row
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (blackAndWhite[i][j] == 255)
                    frequencyPerRow[j] += 1;
            }
        }

        // find max frequency
        int maxFrequency = 0;
        for (int i = 0; i < height; i++) {
            if (frequencyPerRow[i] > maxFrequency)
                maxFrequency = frequencyPerRow[i];
        }

        // remove matra
        for (int j = 0; j < height; j++) {
            if (frequencyPerRow[j] > 500) {
                for (int i = 0; i < width; i++) {
                    blackAndWhite[i][j] = 0;
                }
            }
        }

        // binary
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (blackAndWhite[i][j] == 255)
                    binary[i][j] = 1;
                else
                    binary[i][j] = 0;
            }
        }

        // find region
        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                if (binary[i][j] == 1 && (binary[i][j-1] == 0 || binary[i][j+1] == 0 || binary[i-1][j] == 0 || binary[i+1][j] == 0)) {
                    blackAndWhite[i][j] = 100;
                }
            }
        }
//        print2dMatrix(blackAndWhite);

        // find imin, imax, jmin, jmax
        Stack<Integer> stack = new Stack<Integer>();
        int imin = 0, imax = 0, jmin = 0, jmax = 0;
        int[] itemp = new int[width];
        int[] jtemp = new int[height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (blackAndWhite[i][j] == 100) {
                    stack.push(i);
                    stack.push(j);
//                    imin = 50000; imax = 0; jmin = 50000; jmax = 0;
                    itemp = zeros(width);
                    jtemp = zeros(height);
                    int countTemp = 0;
                    while(!stack.empty()) {
                        int indexJ = stack.pop();
                        int indexI = stack.pop();
                        blackAndWhite[indexI][indexJ] = 1;

                        /*imin = getMin(imin, indexI);
                        imax = getMax(imax, indexI);
                        jmin = getMin(jmin, indexJ);
                        jmax = getMax(jmax, indexJ);*/

                        itemp[countTemp] = indexI;
                        jtemp[countTemp] = indexJ;
                        countTemp++;

                        if (blackAndWhite[indexI-1][indexJ] == 100) {
                            stack.push(indexI-1);
                            stack.push(indexJ);
                        }
                        if (blackAndWhite[indexI+1][indexJ] == 100) {
                            stack.push(indexI+1);
                            stack.push(indexJ);
                        }
                        if (blackAndWhite[indexI][indexJ-1] == 100) {
                            stack.push(indexI);
                            stack.push(indexJ-1);
                        }
                        if (blackAndWhite[indexI][indexJ+1] == 100) {
                            stack.push(indexI);
                            stack.push(indexJ+1);
                        }
                    }

                    // crop image
                    imin = findMin(itemp);
                    imax = findMax(itemp);
                    jmin = findMin(jtemp);
                    jmax = findMax(jtemp);
                    System.out.println(imin + " " + imax + " " + jmin + " " + jmax);
                }
            }
        }


        BufferedImage grayImage = ImageProcessing.grayToImage(gray);
        BufferedImage rgbImage = ImageProcessing.rgbToImage(rgb);
        BufferedImage blackAndWhiteImage = ImageProcessing.grayToImage(blackAndWhite);
//        ImageProcessing.writeImage(grayImage, basePath + "grayImage.png");
//        ImageProcessing.writeImage(rgbImage, basePath + "flowerOut.png");
//        ImageProcessing.writeImage(blackAndWhiteImage, basePath + "blackAndWhiteImage.png");
//        System.out.println(maxFrequency);
    }

    public static int getMax(int x, int y) {
        return x > y ? x : y;
    }

    public static int getMin(int x, int y) {
        return x < y ? x : y;
    }

    public static int findMax(int[] data) {
        int size = data.length;
        int max = data[0];
        for (int i = 1; i < size; i++) {
            if (max < data[i])
                max = data[i];
        }
        return max;
    }

    public static int findMin(int[] data) {
        int size = data.length;
        int min = data[0];
        for (int i = 1; i < size; i++) {
            if (min > data[i])
                min = data[i];
        }
        return min;
    }

    public static void getRegionIndex(int[][] data, int indexI, int indexJ) {
        int size = data.length;
        int[][] regionIndex = zeros2d(2, size);
    }

    public static int dfs(int[][] data, int indexI, int indexJ) {
        return 0;
    }

    public static int[][] zeros2d(int width, int height) {
        int[][] matrix = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    public static int[] zeros(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = 0;
        }
        return array;
    }

    public static void print2dMatrix(int[][] mat) {
        int width = mat.length;
        int height = mat[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
