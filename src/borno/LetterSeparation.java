package borno;

import image.ImageProcessing;
import otsu.Otsu;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by Jobayer on 8/6/2016.
 */
public class LetterSeparation {

    int imin = 50000, imax = 0, jmin = 50000, jmax = 0;
    String basePath = "F:\\Academic\\Programming\\IntelliJ\\Image-Processing\\src\\borno\\resources\\";
    ImageProcessing imageProcessing = new ImageProcessing();
    BufferedImage image = imageProcessing.readImage(basePath + "alo.jpg");

    int[][][] rgb = imageProcessing.getRGB(image);
    int[][] gray = imageProcessing.rgbToGray(rgb);

    Otsu otsu = new Otsu();
    int threshold = otsu.getOtsuThreshold(gray);
    int[][] blackAndWhite = grayToBlackAndWhite(gray, threshold);

    public LetterSeparation() throws IOException {
    }

    public void separateLetter() throws IOException {

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
//        print(String.valueOf(maxFrequency));

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

        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                if (blackAndWhite[i][j] == 100) {
                    blackAndWhite[i][j] = 1;
                    imin = i; jmin = j; imax = i; jmax = j;
                    checkConnection(i, j);
                    cropImage(imin, jmin, imax-imin, jmax-jmin);
                    imin = 0; jmin = 0; imax = 0; jmax = 0;
                }
            }
        }

        // find imin, imax, jmin, jmax
       /* Stack<Integer> stack = new Stack<Integer>();
        int imin = 0, imax = 0, jmin = 0, jmax = 0;
        int[] itemp = new int[width];
        int[] jtemp = new int[height];
//        System.out.println(itemp[0]);
        int countTemp = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (blackAndWhite[i][j] == 100) {
                    stack.push(i);
                    stack.push(j);
                    imin = 50000; imax = 0; jmin = 50000; jmax = 0;
//                    itemp = zeros(width);
//                    jtemp = zeros(height);
//                    int countTemp = 0;
                    int whilecount = 0;
                    while(!stack.empty()) {
                        int indexJ = stack.pop();
                        int indexI = stack.pop();
                        blackAndWhite[indexI][indexJ] = 1;

                        imin = getMin(imin, indexI);
                        imax = getMax(imax, indexI);
                        jmin = getMin(jmin, indexJ);
                        jmax = getMax(jmax, indexJ);
                        whilecount++;
//                        print(imin + " " + imax + " " + jmin + " " + jmax);
//                        countTemp++;

//                        itemp[countTemp] = indexI;
//                        jtemp[countTemp] = indexJ;
//                        countTemp++;

                        if (blackAndWhite[indexI-1][indexJ] == 100) {
                            stack.push(indexI-1);
                            stack.push(indexJ);
                            print("Done 1");
                        }
                        if (blackAndWhite[indexI+1][indexJ] == 100) {
                            stack.push(indexI+1);
                            stack.push(indexJ);
                            print("Done 2");
                        }
                        if (blackAndWhite[indexI][indexJ-1] == 100) {
                            stack.push(indexI);
                            stack.push(indexJ-1);
                            print("Done 3");
                        }
                        if (blackAndWhite[indexI][indexJ+1] == 100) {
                            stack.push(indexI);
                            stack.push(indexJ+1);
                            print("Done 4");
                        }
                    }
//                    print(String.valueOf(whilecount));
//                    print2dMatrix(blackAndWhite);
                    return;

                    // crop image
//                    imin = findMin(itemp);
//                    imax = findMax(itemp);
//                    jmin = findMin(jtemp);
//                    jmax = findMax(jtemp);
//                    System.out.println(imin + " " + imax + " " + jmin + " " + jmax + " " + countTemp++);
//                    return;
                }
            }
        }*/


        BufferedImage grayImage = imageProcessing.grayToImage(gray);
        BufferedImage rgbImage = imageProcessing.rgbToImage(rgb);
        BufferedImage blackAndWhiteImage = imageProcessing.grayToImage(blackAndWhite);
//        imageProcessing.writeImage(grayImage, basePath + "grayImage.png");
//        ImageProcessing.writeImage(rgbImage, basePath + "flowerOut.png");
        imageProcessing.writeImage(blackAndWhiteImage, basePath + "blackAndWhiteImage.png");
//        System.out.println(maxFrequency);
    }

    public void checkConnection(int i, int j) {
        if (blackAndWhite[i-1][j] == 100) {
            setMinMax(i-1,j);
            checkConnection(i-1,j);
        }
    }

    public void setMinMax(int a, int b) {

    }

    public void cropImage(int x, int y, int w, int h) {

    }

    public int getMax(int x, int y) {
        return x > y ? x : y;
    }

    public int getMin(int x, int y) {
        return x < y ? x : y;
    }

    public int findMax(int[] data) {
        int size = data.length;
        int max = 0;
        for (int i = 0; i < size; i++) {
            if (max < data[i])
                max = data[i];
        }
        return max;
    }

    public int findMin(int[] data) {
        int size = data.length;
        int min = 50000;
        for (int i = 0; i < size; i++) {
            if (min > data[i])
                min = data[i];
        }
        return min;
    }

    public void getRegionIndex(int[][] data, int indexI, int indexJ) {
        int size = data.length;
        int[][] regionIndex = zeros2d(2, size);
    }

    public int dfs(int[][] data, int indexI, int indexJ) {
        return 0;
    }

    public int[][] zeros2d(int width, int height) {
        int[][] matrix = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    public int[] zeros(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = 0;
        }
        return array;
    }

    public void print2dMatrix(int[][] mat) {
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

    public void print(String str) {
        System.out.println(str);
    }

    public int[][] grayToBlackAndWhite(int[][] gray, int threshold) {
        int width = gray.length;
        int height = gray[0].length;
        int[][] blackAndWhite = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (gray[i][j] > threshold)
                    blackAndWhite[i][j] = 0;
                else
                    blackAndWhite[i][j] = 255;
            }
        }
        return blackAndWhite;
    }
}
