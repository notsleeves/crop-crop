package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
//import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;

@CommandLine.Command(
        name = "convert",
        version = "convert 0.0.1",
        sortOptions = false,
        headerHeading = "Version: Image convert 0.0.1 https://github.com/notsleeves/crop-crop%n" +
                        "Available formats: @|magenta *.jpeg *.png |@%n",
        synopsisHeading = "Usage: ",
        customSynopsis = "convert @|green <Input file>|@ @|yellow [OPTIONS...]|@ @|green <Output file>|@",
        abbreviateSynopsis = true,
        optionListHeading = "Options:%n",
        separator = " ",
        usageHelpWidth = 90
)

public class ConsoleAttributes {

    private File inputFile;
    private File outputFile;
    private Integer resizeWidth;
    private Integer resizeHeight;
    private Integer cropWidth;
    private Integer cropHeight;
    private Integer cropX;
    private Integer cropY;
    private Integer blurRadius;
    private String  outFormat;
    private Integer quality;

    @CommandLine.Spec
    private CommandSpec spec;

    @Option(names = {"-r", "--resize"}, arity = "2", hideParamSyntax = true, paramLabel = "<w> <h>", order = 0,
            description = "Resize the image to dimension of width <w> height <h> (maintaining the aspect ratio of the original image)")
    void setResizeParam(int[] dimension) {
        setResizeWidth(dimension[0]);
        setResizeHeight(dimension[1]);
    }

    public void setResizeWidth(int resizeWidth) {
//        if (resizeWidth <= 0) {
//            throw new ParameterException(spec.commandLine(),
//                    String.format("Resize width '%s' equal or less zero.",
//                            resizeWidth));
//        }
        this.resizeWidth = resizeWidth;
    }

    public Integer getResizeWidth() {
        return resizeWidth;
    }

    public void setResizeHeight(int resizeHeight) {
//        if (resizeHeight <= 0) {
//            throw new ParameterException(spec.commandLine(),
//                    String.format("Resize height '%s' equal or less zero.",
//                            resizeHeight));
//        }
        this.resizeHeight = resizeHeight;
    }

    public Integer getResizeHeight() {
        return resizeHeight;
    }

    @Option(names = {"-q", "--quality"}, arity = "1", hideParamSyntax = true, paramLabel = "<v>", order = 1,
            description = "Image compression level <v> from 1 (lowest image quality and highest compression) to 100 (best quality but least effective compression)")
    void setQuality(int quality) {
        this.quality = quality;
    }

    public Integer getQuality() {
        return quality;
    }

    @Option(names = {"-c", "--crop"}, arity = "4", hideParamSyntax = true, paramLabel = "<w> <h> <x> <y>", order = 2,
            description = "Cut out one rectangular area of width <w> height <h> start from coordinate <x> <y> of the image")
    void setCropParam(int[] cropParameters) {
        setCropWidth(cropParameters[0]);
        setCropHeight(cropParameters[1]);
        setCropX(cropParameters[2]);
        setCropY(cropParameters[3]);
    }

    void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    void setCropX(int cropX) {
        this.cropX = cropX;
    }

    void setCropY(int cropY) {
        this.cropY = cropY;
    }

    public Integer getCropWidth() {
        return cropWidth;
    }

    public Integer getCropHeight() {
        return cropHeight;
    }

    public Integer getCropX() {
        return cropX;
    }

    public Integer getCropY() {
        return cropY;
    }

    @Option(names = {"-b", "--blur"}, arity = "1", hideParamSyntax = true, paramLabel = "{radius}", order = 3,
            description = "Reduce image noise detail levels")
    void setBlur(int blurRadius) {
        this.blurRadius = blurRadius;
    }

    public Integer getBlur() {
        return blurRadius;
    }

    @Option(names = {"-f", "--format"}, arity = "1", hideParamSyntax = true, paramLabel = "\"outputFormat\"", order = 4,
            description = "The image format type (choose only JPG or PNG)")
    void setOutFormat(String outFormat) {
        this.outFormat = outFormat;
    }

    public String getOutFormat() {
        return outFormat;
    }

    @Option(names = {"-h", "--help"}, usageHelp = true, order = 5, description = "Display this help message")
    public boolean usageHelpRequested;

    @Parameters(index = "0",  paramLabel = "<Input file>", hidden = true)
    void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getInputFile() {
        return inputFile;
    }

    @Parameters(index = "1", paramLabel = "<Output file>", hidden = true)
    void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }
}