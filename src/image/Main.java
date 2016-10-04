package image;

import array.Array;
import borno.LetterSeparation;
import colorimage.RedEye;
import colorimage.SkinDetection;
import featureextraction.FeatureExtraction;
import kmeans.Kmeans;
import meanshift.MeanShift;
import otsu.Otsu;
import sobel.Sobel;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        Sobel sobel = new Sobel();
//        sobel.runSobel();
//        CornerDetection.detectCorner();

//        LetterSeparation letterSeparation = new LetterSeparation();
//        letterSeparation.separateLetter();

//        Kmeans kmeans = new Kmeans();
//        kmeans.applyKmeans();

//        MeanShift meanShift = new MeanShift();
//        meanShift.applyMeanshift();

//        Otsu otsu = new Otsu();
//        otsu.testOtsu();

//        SkinDetection skinDetection = new SkinDetection();
//        skinDetection.detectSkinByRgb();
//
//        RedEye redEye = new RedEye();
//        redEye.detectRedEye();
        FeatureExtraction featureExtraction = new FeatureExtraction();
        featureExtraction.runExtractFeature();
    }
}
