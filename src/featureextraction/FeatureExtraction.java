package featureextraction;

import array.Array;
import image.ImageProcessing;
import sobel.Sobel;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Jobayer on 10/3/2016.
 */
public class FeatureExtraction {
    Array array = new Array();
    ImageProcessing imageProcessing = new ImageProcessing();
    Sobel sobel = new Sobel();

    public void runExtractFeature() throws IOException {
        String basePath = "G:\\Semester 8\\Image Prcessing\\Workspace\\Image-Processing\\src\\featureextraction\\resources\\";
//        String outputFile = "G:\\Semester 8\\Image Prcessing\\Workspace\\Image-Processing\\src\\feature\\resources\\adaboostData.txt";
//        String basePath = "F:\\Academic\\Programming\\IntelliJ\\Image-Processing\\src\\featureextraction\\resources\\";

//        clearFile(basePath+"train1.txt");
//        extractFeature(basePath+"fold0\\Male\\", 500, 0, basePath+"train1.txt");
//        extractFeature(basePath+"fold0\\Female\\", 500, 1, basePath+"train1.txt");
//        extractFeature(basePath+"fold1\\Male\\", 500, 0, basePath+"train1.txt");
//        extractFeature(basePath+"fold1\\Female\\", 500, 1, basePath+"train1.txt");
//        extractFeature(basePath+"fold2\\Male\\", 500, 0, basePath+"train1.txt");
//        extractFeature(basePath+"fold2\\Female\\", 500, 1, basePath+"train1.txt");
//        extractFeature(basePath+"fold3\\Male\\", 500, 0, basePath+"train1.txt");
//        extractFeature(basePath+"fold3\\Female\\", 500, 1, basePath+"train1.txt");
//        extractFeature(basePath+"fold4\\Male\\", 500, 0, basePath+"train1.txt");
//        extractFeature(basePath+"fold4\\Female\\", 500, 1, basePath+"train1.txt");
    }

    public void extractFeature(String path, int n, int label, String outputFile) throws IOException {
        String fileName = "";

        for (int i = 1; i <= n; i++) {
            // write label in file
            appendInFile(outputFile, label+"");

            fileName = i + ".png";
            BufferedImage bufferedImage = imageProcessing.readImage(path + fileName);
            int[][] gray = imageProcessing.getGray(bufferedImage);
            int width = gray.length;
            int height = gray[0].length;

            int[][] edgeX = sobel.getEdgeX(gray);
            int[][] edgeY = sobel.getEdgeY(gray);
            int[][] edge = sobel.getEdge(edgeX, edgeY);

            int[][] theta = getTheta(edgeX, edgeY);
            int minTheta = array.findMinFrom2d(theta);
            int maxTheta = array.findMaxFrom2d(theta);

//            System.out.println("Max theta: " + maxTheta);
//            System.out.println("Min theta: " + minTheta);

            int[] range = new int[10];
            int increaseAmount = (maxTheta - minTheta) / 10;
            for (int j = 0; j < 10; j++) {
                range[j] = increaseAmount*(j+1);
            }

            int[] histogram = new int[10];
            int[][][] imgSeg = new int[64][8][8];
            int counter = 0;
            for (int j = 0; j < width; j = j+8) {
                for (int k = 0; k < height; k = k+8) {
                    for (int l = 0; l < 8; l++) {
                        for (int m = 0; m < 8; m++) {
                            if (theta[j+l][k+m] <= range[0])
                                histogram[0] += edge[j+l][k+m];
                            else if (theta[j+l][k+m] <= range[1])
                                histogram[1] = edge[j+l][k+m];
                            else if (theta[j+l][k+m] <= range[2])
                                histogram[2] = edge[j+l][k+m];
                            else if (theta[j+l][k+m] <= range[3])
                                histogram[3] = edge[j+l][k+m];
                            else if (theta[j+l][k+m] <= range[4])
                                histogram[4] = edge[j+l][k+m];
                            else if (theta[j+l][k+m] <= range[5])
                                histogram[5] = edge[j+l][k+m];
                            else if (theta[j+l][k+m] <= range[6])
                                histogram[6] = edge[j+l][k+m];
                            else if (theta[j+l][k+m] <= range[7])
                                histogram[7] = edge[j+l][k+m];
                            else if (theta[j+l][k+m] <= range[8])
                                histogram[8] = edge[j+l][k+m];
                            else
                                histogram[9] = edge[j+l][k+m];
                        }
                    }
                    counter++;
                    appendArrayInFile(outputFile, histogram);
                }
            }
            appendInFile(outputFile, "\n");
        }
    }

    public int[][] getTheta(int[][] edgeX, int[][] edgeY) {
        int width = edgeX.length;
        int height = edgeX[0].length;
        int[][] theta = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                theta[i][j] = (int) Math.toDegrees(Math.atan2(edgeY[i][j], edgeX[i][j]));
            }
        }
        return theta;
    }

    public void writeInFile(String fileName, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(text);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void appendInFile(String fileName, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(text);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void appendArrayInFile(String fileName, int[] data) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        int size = data.length;
        for (int i = 0; i < size; i++) {
            bufferedWriter.write(" " + data[i]);
        }

        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void clearFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("");
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
