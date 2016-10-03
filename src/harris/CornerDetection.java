package harris;

import image.ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Jobs on 8/8/2016.
 */
public class CornerDetection {

    public static void detectCorner() throws IOException {
        String basePath = "G:\\Semester 8\\Image Prcessing\\Workspace\\Assignments\\src\\harris\\resources\\";
        ImageProcessing imageProcessing = new ImageProcessing();
        BufferedImage image = imageProcessing.readImage(basePath + "building.jpg");
        int[][] gray = imageProcessing.getGray(image);
        int width = gray.length;
        int height = gray[0].length;
        float[][] data = intToFloat(gray);

        int[][] maskx = new int[][] {
                {-1, -2, -1},
                {0, 0, 0,},
                {1, 2, 1}
        };
        int[][] masky = new int[][] {
                {-1, 0, 1},
                {-2, 0, 2,},
                {-1, 0, 1}
        };

       /* for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(maskx[i][j] + " ");
            }
            System.out.println();
        }*/

        int[][] edgeX = zeros(width, height);
        int[][] edgeY = zeros(width, height);
        int[][] edgeXY = zeros(width, height);
        float gx = 0;
        float gy = 0;

        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                gx = (data[i-1][j-1]*maskx[0][0]) + (data[i-1][j]*maskx[0][1]) + (data[i-1][j+1]*maskx[0][2])
                    +(data[i][j-1]*maskx[1][0]) + (data[i][j]*maskx[1][1]) + (data[i][j+1]*maskx[1][2])
                    +(data[i+1][j-1]*maskx[2][0]) + (data[i+1][j]*maskx[2][1]) + (data[i+1][j+1]*maskx[2][2]);

                gy = (data[i-1][j-1]*masky[0][0]) + (data[i-1][j]*masky[0][1]) + (data[i-1][j+1]*masky[0][2])
                    +(data[i][j-1]*masky[1][0]) + (data[i][j]*masky[1][1]) + (data[i][j+1]*masky[1][2])
                    +(data[i+1][j-1]*masky[2][0]) + (data[i+1][j]*masky[2][1]) + (data[i+1][j+1]*masky[2][2]);

                if(gx > 255)
                    gx = 255;
                else if(gx < 0)
                    gx = 0;

                if (gy > 255)
                    gy = 255;
                else if (gy < 0)
                    gy = 0;

                edgeX[i][j] = (int) gx;
                edgeY[i][j] = (int) gy;
                edgeXY[i][j] = (int) Math.sqrt(gx*gx +gy*gy);
            }
        }

       /* BufferedImage imageX = ImageProcessing.grayToImage(edgeX);
        BufferedImage imageY = ImageProcessing.grayToImage(edgeY);
        BufferedImage imageXY = ImageProcessing.grayToImage(edgeXY);
        ImageProcessing.writeImage(imageX, basePath+"imageX.png");
        ImageProcessing.writeImage(imageY, basePath+"imageY.png");
        ImageProcessing.writeImage(imageXY, basePath+"imageXY.png");*/

        int[][] xx = multiplyMatrixPointToPoint(edgeX, edgeX);
        int[][] yy = multiplyMatrixPointToPoint(edgeY, edgeY);
        int[][] xy = multiplyMatrixPointToPoint(edgeX, edgeY);
        int[][] xxg = gaussianMask(xx);
        int[][] yyg = gaussianMask(yy);
        int[][] xyg = gaussianMask(xy);
        int[][] determinant = subtractMatrixPointToPoint(multiplyMatrixPointToPoint(xxg, yyg), multiplyMatrixPointToPoint(xyg, xyg));
        int[][] normalized = divideMatrixPointToPoint(determinant, addMatrixPointToPoint(xxg, yyg));

        BufferedImage cornerImage = imageProcessing.grayToImage(normalized);
        imageProcessing.writeImage(cornerImage, basePath + "cornerImage.png");
    }

    private static int[][] gaussianMask(int[][] data) {
        int width = data.length;
        int height = data[0].length;
        int[][] guassian = new int[width][height];
        int[][] mask = new int[][] {
                {1,4,7,4,1},
                {4,16,26,16,4},
                {7,26,41,26,7},
                {4,16,26,16,4},
                {1,4,7,4,1}
        };

        for (int i = 2; i < width - 2; i++) {
            for (int j = 2; j < height - 2; j++) {
                int g = 0;
                int m = i-2;
                for (int k = 0; k < 5; k++) {
                    int n = j-2;
                    for (int l = 0; l < 5; l++) {
                        g = g + data[m][n]*mask[k][l];
                        n++;
                    }
                    m++;
                }

                g = g / 273;
                if (g > 255)
                    g = 255;
                else if (g < 0)
                    g = 0;

                guassian[i][j] = g;
            }
        }
        return guassian;
    }

    private static int[][] addMatrixPointToPoint(int[][] mat1, int[][] mat2) {
        int width = mat1.length;
        int height = mat1[0].length;
        int[][] result = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = mat1[i][j] + mat2[i][j];
            }
        }
        return result;
    }

    private static int[][] subtractMatrixPointToPoint(int[][] mat1, int[][] mat2) {
        int width = mat1.length;
        int height = mat1[0].length;
        int[][] result = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = mat1[i][j] - mat2[i][j];
            }
        }
        return result;
    }

    private static int[][] multiplyMatrixPointToPoint(int[][] mat1, int[][] mat2) {
        int width = mat1.length;
        int height = mat1[0].length;
        int[][] result = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = mat1[i][j] * mat2[i][j];
            }
        }
        return result;
    }

    private static int[][] divideMatrixPointToPoint(int[][] mat1, int[][] mat2) {
        int width = mat1.length;
        int height = mat1[0].length;
        int[][] result = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (mat2[i][j] == 0) {
                    mat2[i][j] = 1;
                    mat1[i][j] = 0;
                }
                result[i][j] = mat1[i][j] / mat2[i][j];
            }
        }
        return result;
    }

    private static int[][] zeros(int width, int height) {
        int[][] matrix = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    private static float[][] intToFloat(int[][] input) {
        int width = input.length;
        int height = input[0].length;
        float[][] output = new float[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                output[i][j] = (float)input[i][j];
            }
        }
        return output;
    }
}
