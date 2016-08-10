package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jobayer on 8/6/2016.
 */
public class ImageProcessing {

    public static BufferedImage readImage(String path) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(path));
        return bufferedImage;
    }

    public static void writeImage(BufferedImage bufferedImage, String path) throws IOException {
//        int index = path.lastIndexOf('.');
//        String format = (index == -1) ? "png" : path.substring(index);
        ImageIO.write(bufferedImage, "png", new File(path));
    }

    public static int[][][] getRGB(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[][][] rgb = new int[width][height][3];
        Color color = null;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                color = new Color(bufferedImage.getRGB(i,j));
                rgb[i][j][0] = color.getRed();
                rgb[i][j][1] = color.getGreen();
                rgb[i][j][2] = color.getBlue();
            }
        }
        return rgb;
    }

    public static int[][] getGray(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[][] gray = new int[width][height];
        int[][][] rgb = getRGB(bufferedImage);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gray[i][j] = (int) (0.30*rgb[i][j][0] + 0.59*rgb[i][j][1] + 0.11*rgb[i][j][2]);
            }
        }
        return gray;
    }

    public static int[][] rgbToGray(int[][][] rgb) {
        int width = rgb.length;
        int height = rgb[0].length;
        int[][] gray = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gray[i][j] = (int) (0.30*rgb[i][j][0] + 0.59*rgb[i][j][1] + 0.11*rgb[i][j][2]);
            }
        }
        return gray;
    }

    public static int[][] grayToBinary(int[][] gray, int threshold) {
        int width = gray.length;
        int height = gray[0].length;
        int[][] binary = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (gray[i][j] > threshold)
                    binary[i][j] = 1;
                else
                    binary[i][j] = 0;
            }
        }
        return binary;
    }

    public static int[][] grayToBlackAndWhite(int[][] gray, int threshold) {
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

    public static BufferedImage grayToImage(int[][] gray) {
        int width = gray.length;
        int height = gray[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = gray[i][j] << 16 | gray[i][j] << 8 | gray[i][j];
                image.setRGB(i, j, rgb);
            }
        }
        return image;
    }

    public static BufferedImage rgbToImage(int[][][] rgb) {
        int width = rgb.length;
        int height = rgb[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = rgb[i][j][0] << 16 | rgb[i][j][1] << 8 | rgb[i][j][2];
                image.setRGB(i, j, pixel);
            }
        }
        return image;
    }
}
