package meanshift;

import array.Array;
import image.ImageProcessing;
import kmeans.Kmeans;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Jobayer on 8/28/2016.
 */
public class MeanShift {

    Array array = new Array();
    ImageProcessing imageProcessing = new ImageProcessing();
    String basePath = "D:\\Image-Processing\\src\\meanshift\\resources\\";
    String fileName = "river";

    public void applyMeanshift() throws IOException {
        BufferedImage image = imageProcessing.readImage(basePath + fileName + ".jpg");
        int[][] gray = imageProcessing.getGray(image);
        int width = gray.length;
        int height = gray[0].length;

        int radius = 10;
        int minIntensity = array.findMinFrom2d(gray);
        int maxIntensity = array.findMaxFrom2d(gray);
        int numberOfMeans = ((maxIntensity-minIntensity) / (radius*2));

        int[] means = new int[numberOfMeans];
        int[] newMeans = new int[numberOfMeans];

        means[0] = minIntensity + radius;
        for (int i = 1; i < numberOfMeans; i++) {
            means[i] = means[i-1] + 20;
        }

        int meanDifference = 1;
        int iteration = 0;
        while (meanDifference != 0){
            for (int i = 0; i < numberOfMeans; i++) {
                newMeans[i] = computeMean(gray, radius, means[i]);
            }
            meanDifference = array.getSumOfDifference(newMeans, means);
            means = array.copyArray(newMeans);
            iteration++;
        }

        int[] finalMeans = array.getUniqueElements(means);
        array.print1d(finalMeans);
        array.print("Iteration: " + iteration);
        array.print("Number of means: " + finalMeans.length);

        Kmeans kmeans = new Kmeans();
        int[][] kRegions = kmeans.selectMeanRegions(gray, finalMeans);

        BufferedImage outputImage = kmeans.setColorInRegions(kRegions, finalMeans.length);
        imageProcessing.writeImage(outputImage, basePath + fileName + "out.jpg");
    }

    public int computeMean(int[][] gray, int radius, int mean) {
        int newMean = 0;
        int sum = 0;
        int count = 0;
        int width = gray.length;
        int height = gray[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.abs(gray[i][j] - mean) <= radius) {
                    sum += gray[i][j];
                    count++;
                }
            }
        }

        if (count != 0)
            newMean = sum / count;

        return newMean;
    }

//    public void applyColorMeanshift() throws IOException {
//        BufferedImage image = imageProcessing.readImage(basePath + fileName + ".jpg");
//        int[][][] rgb = imageProcessing.getRGB(image);
//        int width = rgb.length;
//        int height = rgb[0].length;
//        int[][] rgbData = new int[width][height];
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                rgbData[i][j] = rgb[i][j][0]*1000000 + rgb[i][j][1]*1000 + rgb[i][j][2];
//            }
//        }
//
//        int radius = 5000000;
//        int minIntensity = array.findMinFrom2d(rgbData);
//        int maxIntensity = array.findMaxFrom2d(rgbData);
//        int numberOfMeans = ((maxIntensity-minIntensity) / (radius*2));
////        array.print(minIntensity);
////        array.print(maxIntensity);
////        array.print(numberOfMeans);
//
//        int[] means = new int[numberOfMeans];
//        int[] newMeans = new int[numberOfMeans];
//
//        means[0] = minIntensity + radius;
//        for (int i = 1; i < numberOfMeans; i++) {
//            means[i] = means[i-1] + 2*radius;
//        }
////        array.print1d(means);
//
//        int meanDifference = 1;
//        int iteration = 0;
//        while (iteration < 10){
//            for (int i = 0; i < numberOfMeans; i++) {
//                newMeans[i] = computeMean(rgbData, radius, means[i]);
//            }
//            meanDifference = array.getSumOfDifference(newMeans, means);
//            means = array.copyArray(newMeans);
//            iteration++;
//        }
//
//        int[] finalMeans = array.getUniqueElements(means);
//        array.print1d(finalMeans);
//        array.print("Iteration: " + iteration);
//        array.print("Number of means: " + finalMeans.length);

//        Kmeans kmeans = new Kmeans();
//        int[][] kRegions = kmeans.selectMeanRegions(rgbData, finalMeans);

//        BufferedImage outputImage = kmeans.setColorInRegions(kRegions, finalMeans.length);
//        imageProcessing.writeImage(outputImage, basePath + fileName + "out.jpg");
//    }
}
