package colorimage;

import array.Array;
import image.ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Jobs on 8/29/2016.
 * Using RGB and HSI
 */
public class SkinDetection {

    Array array = new Array();
    ImageProcessing imageProcessing = new ImageProcessing();
    String basePath = "D:\\Image-Processing\\src\\colorimage\\resources\\";
    String fileName = "face";

    public void detectSkinByRgb() throws IOException {
        BufferedImage inputImage = imageProcessing.readImage(basePath + fileName + ".jpg");
        int[][][] rgb = imageProcessing.getRGB(inputImage);
        int width = rgb.length;
        int height = rgb[0].length;
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int r = 0, g = 0, b = 0, max = 0, min = 0, pixelRgb = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                r = rgb[i][j][0];
                g = rgb[i][j][1];
                b = rgb[i][j][2];
                max = array.findMax(new int[]{r,g,b});
                min = array.findMin(new int[]{r,g,b});
                if (r > 95 && g > 40 && b > 20 && (max - min) > 15 && Math.abs(r-g) > 15 & r > g & r > b) {
                    pixelRgb = 255 << 16 | 255 << 8 | 255;
                    outputImage.setRGB(i, j, pixelRgb);
                }
                else {
                    pixelRgb = 0 << 16 | 0 << 8 | 0;
                    outputImage.setRGB(i, j, pixelRgb);
                }
            }
        }

        imageProcessing.writeImage(outputImage, basePath + fileName + "Out.jpg");
    }

    public void detectSkinByHsi() {

    }
}
