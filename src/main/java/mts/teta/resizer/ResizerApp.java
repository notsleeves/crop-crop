package mts.teta.resizer;

import picocli.CommandLine;
import javax.imageio.ImageIO;
import java.util.concurrent.Callable;
import mts.teta.resizer.imageprocessor.ImageProcessor;

public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {
    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
         CommandLine.Help.ColorScheme colorScheme = new CommandLine.Help.ColorScheme.Builder()
            .ansi(CommandLine.Help.Ansi.ON)
            .options     (CommandLine.Help.Ansi.Style.fg_yellow)
            .parameters  (CommandLine.Help.Ansi.Style.fg_yellow)
            .optionParams(CommandLine.Help.Ansi.Style.italic)
            .optionParams(CommandLine.Help.Ansi.Style.fg_green)
            .errors      (CommandLine.Help.Ansi.Style.fg_red, CommandLine.Help.Ansi.Style.bold)
            .build();
        return new CommandLine(new ResizerApp()).setColorScheme(colorScheme).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(this.getInputFile()), this);
        return 0;
    }
}
