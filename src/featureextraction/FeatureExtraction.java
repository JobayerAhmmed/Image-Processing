package featureextraction;

import image.ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by bsse0 on 10/3/2016.
 */
public class FeatureExtraction {
    ImageProcessing imageProcessing = new ImageProcessing();

    public void callExtractFeature() throws IOException {
        String basePath = "G:\\Semester 8\\Image Prcessing\\Workspace\\Image-Processing\\src\\feature\\resources\\";
        String outputFile = "G:\\Semester 8\\Image Prcessing\\Workspace\\Image-Processing\\src\\feature\\resources\\adaboostData.txt";

        extractFeature(basePath+"fold1\\Male2\\", 2051, outputFile);
//        extractFeature(basePath+"fold1\\Female2\\", 595, outputFile);
//        extractFeature(basePath+"fold2\\Male2\\", 2051, outputFile);
//        extractFeature(basePath+"fold2\\Female2\\", 595, outputFile);
//        extractFeature(basePath+"fold3\\Male2\\", 2051, outputFile);
//        extractFeature(basePath+"fold3\\Female2\\", 595, outputFile);
//        extractFeature(basePath+"fold4\\Male2\\", 2051, outputFile);
//        extractFeature(basePath+"fold4\\Female2\\", 595, outputFile);
    }

    public void extractFeature(String path, int n, String outputFile) throws IOException {
        String fileName = "";

        for (int i = 1; i <= n; i++) {
            fileName = i + ".png";
            BufferedImage bufferedImage = imageProcessing.readImage(path + fileName);
            int[][] grayImage = imageProcessing.getGray(bufferedImage);
//            int[][] sobelX = sobel.getX(grayImage);
        }
    }
}
