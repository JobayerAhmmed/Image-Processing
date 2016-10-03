package colorimage;

import array.Array;
import image.ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Jobs on 8/29/2016.
 * Using HSI
 */
public class RedEye {

    Array array = new Array();
    ImageProcessing imageProcessing = new ImageProcessing();
    String basePath = "D:\\Image-Processing\\src\\colorimage\\resources\\";
    String fileName = "eye";

    public void detectRedEye() throws IOException {
        BufferedImage inputImage = imageProcessing.readImage(basePath + fileName + ".jpg");
        int[][][] rgb = imageProcessing.getRGB(inputImage);
        int width = rgb.length;
        int height = rgb[0].length;
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        int r = 0, g = 0, b = 0;
        double intensity = 0, saturation = 0, hue = 0, theta = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                r = rgb[i][j][0];
                g = rgb[i][j][1];
                b = rgb[i][j][2];
                intensity = (r + g + b) / 3.0;
                saturation = 1 - ((3.0 / intensity) * array.findMin( new int[]{r,g,b}));
                theta = Math.acos( ( ((r-g)+(r-b))/2.0 ) / Math.sqrt( (r-g)*(r-g) + (r-b)*(g-b) ) );

                if (g >= b)
                    hue = theta;
                else if (g < b)
                    hue = 2*Math.PI - theta;

                // set color
                int pixelRgb = 0;
                if (hue > ((-1)*Math.PI/4.0) && hue < (Math.PI/4.0) && saturation > 0.3) {
                    pixelRgb = 0 << 16 | 0 << 8 | 0;
                    outputImage.setRGB(i, j, pixelRgb);
                }
                else {
                    pixelRgb = rgb[i][j][0] << 16 | rgb[i][j][1] << 8 | rgb[i][j][2];
                    outputImage.setRGB(i, j, pixelRgb);
                }
            }
        }

        imageProcessing.writeImage(outputImage, basePath+fileName+"Out.jpg");
    }
}
