package kmeans;

import array.Array;
import image.ImageProcessing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Jobayer on 8/22/2016.
 */
public class Kmeans {

    ImageProcessing imageProcessing = new ImageProcessing();
    String basePath = "D:\\Image-Processing\\src\\kmeans\\resources\\";
    String filename = "river";

    Array array = new Array();

    public void applyKmeans() throws IOException {
        BufferedImage image = imageProcessing.readImage(basePath + filename + ".jpg");

        int[][] gray = imageProcessing.getGray(image);
        int width = gray.length;
        int height = gray[0].length;

        int maxIntensity = array.findMaxFrom2d(gray);
        int minIntensity = array.findMinFrom2d(gray);
        int intensityAverage = (maxIntensity - minIntensity) / 4;
        int k[] = {
            minIntensity+intensityAverage,
            minIntensity+2*intensityAverage,
            minIntensity+3*intensityAverage,
            minIntensity+4*intensityAverage
        };
        int ksize = k.length;

        // select k regions
        int[][] kRegions = selectMeanRegions(gray, k);

        // calculate new kmeans
        int[] newKmeans = getKmeans(kRegions, gray, ksize);

        // calculate till difference is zero
        int[] oldKmeans = new int[ksize];
        int meansDifference = 1;
        int count = 0;
        while (meansDifference != 0){
            kRegions = selectMeanRegions(gray, newKmeans);
            oldKmeans = array.copyArray(newKmeans);
            newKmeans = getKmeans(kRegions, gray, 4);
            meansDifference = array.getSumOfDifference(oldKmeans, newKmeans);
            count++;
        }
        array.print("Total iteration: " + count);

        // color image according to k means
        BufferedImage outputImage = setColorInRegions(kRegions, ksize);

        imageProcessing.writeImage(outputImage, basePath + filename + "out.jpg");
    }

    public int[][] selectMeanRegions(int[][] data, int[]k) {
        int width = data.length;
        int height = data[0].length;
        int ksize = k.length;

        int[][] output = new int[width][height];
        int[] d = new int[ksize];
        int index = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int l = 0; l < ksize; l++) {
                    d[l] = Math.abs(k[l]-data[i][j]);
                }
                index = array.findIndexOfSmallestNumber(d);
                index = index*(-1);
                output[i][j] = index;
            }
        }
        return output;
    }

    public int[] getKmeans(int[][] kdata, int[][] gray, int ksize) {
        int width = kdata.length;
        int height = kdata[0].length;
        int[] k = new int[ksize];
        int sum = 0;
        int count = 0;

        for (int l = 0; l < ksize; l++) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if ((l*(-1)) == kdata[i][j]) {
                        sum = sum + gray[i][j];
                        count++;
                    }
                }
            }
            k[l] = sum / count;
            sum = 0;
            count = 0;
        }

        return k;
    }

    public BufferedImage setColorInRegions(int[][] kRegions, int ksize) {
        int width = kRegions.length;
        int height = kRegions[0].length;
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] rgb = new int[ksize];
        int v = 255/ksize;
        for (int i = 0; i < ksize; i++) {
            rgb[i] = 200 << 16 | i*v << 8 | 0;
        }
//        rgb[0] = 255 << 16 | 0 << 8 | 0;
//        rgb[1] = 0 << 16 | 255 << 8 | 0;
//        rgb[2] = 255 << 16 | 0 << 8 | 255;
//        rgb[3] = 255 << 16 | 255 << 8 | 0;
//        rgb[4] = 0 << 16 | 0 << 8 | 255;
//        rgb[5] = 128 << 16 | 0 << 8 | 0;
//        rgb[6] = 0 << 16 | 128 << 8 | 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < ksize; k++) {
                    if (kRegions[i][j] == k*(-1))
                        outputImage.setRGB(i,j,rgb[k]);
                }
            }
        }

        return outputImage;
    }
}
