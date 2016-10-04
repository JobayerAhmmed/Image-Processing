package sobel;

import array.Array;
import image.ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by bsse0 on 10/3/2016.
 */
public class Sobel {

    ImageProcessing imageProcessing = new ImageProcessing();
    String basePath = "F:\\Academic\\Programming\\IntelliJ\\Image-Processing\\src\\sobel\\resources\\";
    String fileName = "taj";
    String fileExt = ".jpg";
    Array array = new Array();

    public void runSobel() throws IOException {
        BufferedImage bufferedImage = imageProcessing.readImage(basePath + fileName + fileExt);
        int[][] gray = imageProcessing.getGray(bufferedImage);

        int[][] edgeX = getEdgeX(gray);
        int[][] edgeY = getEdgeY(gray);
        int[][] edge = getEdge(edgeX, edgeY);

        BufferedImage imageX = imageProcessing.grayToImage(edgeX);
        BufferedImage imageY = imageProcessing.grayToImage(edgeY);
        BufferedImage image = imageProcessing.grayToImage(edge);

        imageProcessing.writeImage(imageX, basePath + fileName + "X" + fileExt);
        imageProcessing.writeImage(imageY, basePath + fileName + "Y" + fileExt);
        imageProcessing.writeImage(image, basePath + fileName + "Out" + fileExt);
    }

    public int[][] getEdgeX(int[][] gray) {
        int width = gray.length;
        int height = gray[0].length;
        int[][] edgeX = new int[width][height];
        int[][] maskX = new int[][] {
                {-1, -2, -1},
                {0, 0, 0},
                {1, 2, 1}
        };

        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                int gx = gray[i-1][j-1]*maskX[0][0] + gray[i-1][j]*maskX[0][1] + gray[i-1][j+1]*maskX[0][2]
                        + gray[i][j-1]*maskX[1][0] + gray[i][j]*maskX[1][1] + gray[i][j+1]*maskX[1][2]
                        + gray[i+1][j-1]*maskX[2][0] + gray[i+1][j]*maskX[2][1] + gray[i+1][j+1]*maskX[2][2];

                if (gx > 255)
                    gx = 255;
                else if (gx < 0)
                    gx = 0;

                edgeX[i][j] = gx;
            }
        }

        return edgeX;
    }

    public int[][] getEdgeY(int[][] gray) {
        int width = gray.length;
        int height = gray[0].length;
        int[][] edgeY = new int[width][height];
        int[][] maskY = new int[][] {
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };

        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                int gy = gray[i-1][j-1]*maskY[0][0] + gray[i-1][j]*maskY[0][1] + gray[i-1][j+1]*maskY[0][2]
                        + gray[i][j-1]*maskY[1][0] + gray[i][j]*maskY[1][1] + gray[i][j+1]*maskY[1][2]
                        + gray[i+1][j-1]*maskY[2][0] + gray[i+1][j]*maskY[2][1] + gray[i+1][j+1]*maskY[2][2];

                if (gy > 255)
                    gy = 255;
                else if (gy < 0)
                    gy = 0;

                edgeY[i][j] = gy;
            }
        }

        return edgeY;
    }

    public int[][] getEdge(int[][] edgeX, int[][] edgeY) {
        int width = edgeX.length;
        int height = edgeX[0].length;
        int[][] edge = new int[width][height];

        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                edge[i][j] = (int) Math.sqrt(edgeX[i][j]*edgeX[i][j] + edgeY[i][j]*edgeY[i][j]);
            }
        }

        return edge;
    }
}
