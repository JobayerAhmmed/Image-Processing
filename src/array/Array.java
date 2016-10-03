package array;

import java.util.ArrayList;

/**
 * Created by Jobayer on 8/26/2016.
 */
public class Array {

    public void print(String data) {
        System.out.println(data);
    }
    public void print(int data) {
        System.out.println(data);
    }
    public void print(float data) {
        System.out.println(data);
    }
    public void print(double data) {
        System.out.println(data);
    }

    public void print1d(int[] data) {
        int size = data.length;
        for (int i = 0; i < size; i++) {
            System.out.println(data[i]);
        }
    }

    public void print2d(int[][] data) {
        int m = data.length;
        int n = data[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int findIndexOfSmallestNumber(int[] data) {
        int index = 0;
        int min = data[0];
        int size = data.length;
        for (int i = 1; i < size; i++) {
            if (min > data[i]) {
                min = data[i];
                index = i;
            }
        }
        return index;
    }

    public int findMaxFrom2d(int[][] data) {
        int width = data.length;
        int height = data[0].length;
        int max = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (max < data[i][j])
                    max = data[i][j];
            }
        }
        return max;
    }

    public int findMin(int[] data) {
        int size = data.length;
        int min = 255255255;
        for (int i = 0; i < size; i++) {
            if (min > data[i])
                min = data[i];
        }
        return min;
    }

    public int findMax(int[] data) {
        int size = data.length;
        int max = 0;
        for (int i = 0; i < size; i++) {
            if (max < data[i])
                max = data[i];
        }
        return max;
    }

    public int findMinFrom2d(int[][] data) {
        int width = data.length;
        int height = data[0].length;
        int min = 255255256;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (min > data[i][j])
                    min = data[i][j];
            }
        }
        return min;
    }

    public int[] copyArray(int[] input) {
        int size = input.length;
        int[] output = new int[size];
        for (int i = 0; i < size; i++) {
            output[i] = input[i];
        }
        return output;
    }

    public int getSumOfDifference(int[] x, int[] y) {
        int sum = 0;
        int size = x.length;
        for (int i = 0; i < size; i++) {
            sum += Math.abs(x[i] - y[i]);
        }
        return sum;
    }

    public int[] getUniqueElements(int[] data) {
        int size = data.length;
        ArrayList<Integer> store = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            if (!store.contains(data[i]))
                store.add(data[i]);
        }

        int storeSize = store.size();
        int[] output = new int[storeSize];
        for (int i = 0; i < storeSize; i++) {
            output[i] = store.get(i);
        }

        return output;
    }
}
