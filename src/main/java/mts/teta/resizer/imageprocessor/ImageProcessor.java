package mts.teta.resizer.imageprocessor;

import mts.teta.resizer.ResizerApp;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.*;
import net.coobird.thumbnailator.resizers.configurations.AlphaInterpolation;
import net.coobird.thumbnailator.resizers.configurations.Antialiasing;
import net.coobird.thumbnailator.resizers.configurations.Dithering;
import net.coobird.thumbnailator.resizers.configurations.Rendering;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import marvin.image.MarvinImage;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;


public class ImageProcessor {

    public void processImage(BufferedImage originalImage, ResizerApp resizerApp) throws IOException, BadAttributesException {
        Rectangle cropArea = null;

        try {
            if (resizerApp.getCropWidth() != null && resizerApp.getCropHeight() != null &&
                    resizerApp.getCropX() != null && resizerApp.getCropY() != null) {
                cropArea = new Rectangle(resizerApp.getCropX(), resizerApp.getCropY(),
                        resizerApp.getCropWidth(), resizerApp.getCropHeight());
            }
            else {
                cropArea = new Rectangle(0, 0, originalImage.getWidth(), originalImage.getHeight());
            }
            BufferedImage outputImage = Thumbnails.of(originalImage)
                    .forceSize(resizerApp.getResizeWidth() != null ? resizerApp.getResizeWidth() : originalImage.getWidth(),
                            resizerApp.getResizeHeight() != null ? resizerApp.getResizeHeight() : originalImage.getHeight())
                    .resizer(Resizers.BILINEAR)
                    .antialiasing(Antialiasing.OFF)
                    .rendering(Rendering.SPEED)
                    .alphaInterpolation(AlphaInterpolation.SPEED)
                    .dithering(Dithering.DISABLE)
                    .sourceRegion(cropArea)
                    .asBufferedImage();
            if (resizerApp.getBlur() != null) {
                MarvinImage outputMarvinImage = new MarvinImage(outputImage);
                GaussianBlur gaussianBlur = new GaussianBlur();
                gaussianBlur.load();
                gaussianBlur.setAttribute("radius", resizerApp.getBlur());
                gaussianBlur.process(outputMarvinImage, outputMarvinImage);
                outputImage = outputMarvinImage.getBufferedImageNoAlpha();
            }
            Thumbnails.of(outputImage)
                    .scale(1)
                    .outputQuality(resizerApp.getQuality() != null ? Double.valueOf(resizerApp.getQuality())  / 100 : 1)
                    .outputFormat(resizerApp.getOutFormat() == null ? getFileExtension(resizerApp.getInputFile()) : resizerApp.getOutFormat())
                    .toFile(getFileWithoutExtension(resizerApp.getOutputFile()));
        }
        catch (IllegalArgumentException | RasterFormatException | UnsupportedFormatException e) {
            throw new BadAttributesException("Please check params!");
        }
    }

    public static File getFileWithoutExtension(File file) {
        int i = file.getName().lastIndexOf('.');

        String name = file.getName().substring(0,i);
        return new File(file.getParent(), name);
    }

    public static String getFileExtension(File file) {
        int i = file.getName().lastIndexOf('.');

        return file.getName().substring(i + 1);
    }
}

