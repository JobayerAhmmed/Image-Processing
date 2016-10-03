package otsu;

import array.Array;
import image.ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Otsu {

    Array p = new Array();
    ImageProcessing image = new ImageProcessing();
    String basePath = "D:\\Image-Processing\\src\\otsu\\resources\\";
    String fileName = "alo";

    public void testOtsu() throws IOException {
        BufferedImage inputImage = image.readImage(basePath + fileName + ".jpg");
        int[][] gray = image.getGray(inputImage);

        int threshold = getOtsuThreshold(gray);
        System.out.println("Threshold: " + threshold);

        int[][] blackAndWhite = image.grayToBlackAndWhite(gray, threshold);
        BufferedImage outputImage = image.grayToImage(blackAndWhite);
        image.writeImage(outputImage, basePath + fileName + "out.jpg");
    }

    public int getOtsuThreshold(int[][] gray) {
        int[] histogram = image.getImageHistogram(gray);

        // calculate MN
        int mn = 0;
        for (int i = 0; i < 256; i++) {
            mn += histogram[i];
        }

        // calculate p-i
        double[] pi = new double[256];
        for (int i = 0; i < 256; i++) {
            pi[i] = histogram[i] / (double)mn;
        }

        // calculate sigma2B
        double P1 = 0, m1 = 0, m2 = 0, sigma = 0, sigmaMax = 0;
        int threshold = 0;
        for (int k = 1; k < 255; k++) {
            for (int i = 0; i <= k; i++) {
                P1 += pi[i];
            }
            for (int i = 0; i <= k; i++) {
                m1 += i * pi[i];
            }
            if (P1 != 0)
                m1 = m1 / P1;

            for (int i = k+1; i <= 255; i++) {
                m2 += i * pi[i];
            }
            m2 = m2 / (1-P1);

            sigma = P1 * (1-P1) * (m1 - m2) * (m1 - m2);

            if (sigma > sigmaMax) {
                sigmaMax = sigma;
                threshold = k;
            }

            P1 = 0; m1 = 0; m2 = 0;
        }
        return threshold;
    }
}
