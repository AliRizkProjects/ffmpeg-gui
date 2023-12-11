import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class FFmpegHandler extends SwingWorker<Void, String> {
    private String videoFilePath;
    private String videoOutputPath;
    private String videoOutputFilename;
    private String crfOption;

    private JFrame mainFrame;
    private JTextArea cmdArea;

    public FFmpegHandler(JFrame mainFrame, JTextArea cmdArea, String videoFilePath, String videoOutputPath,
            String videoOutputFilename, String crfOption) {
        this.mainFrame = mainFrame;
        this.cmdArea = cmdArea;
        this.videoFilePath = videoFilePath;
        this.videoOutputPath = videoOutputPath;
        this.videoOutputFilename = videoOutputFilename;
        this.crfOption = crfOption;
    }

    protected Void doInBackground() throws Exception {
        // check if selected outputname already exists in selected outputfolder
        Path videoOutputFilepath = Paths.get(videoOutputPath, videoOutputFilename);
        if (Files.exists(videoOutputFilepath)) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Sorry, but the chosen filename already exists. Please pick a different name.",
                    "File already exists", JOptionPane.WARNING_MESSAGE);
        } else {
            // create and run process
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", videoFilePath, "-vcodec", "libx264", "-crf",
                    crfOption, videoOutputFilepath.toString());
            pb.redirectErrorStream(true);

            Process process = null;
            try {
                process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while (!isCancelled() && (line = reader.readLine()) != null) {
                    System.out.println(line);
                    cmdArea.append(line);
                    cmdArea.setCaretPosition(cmdArea.getDocument().getLength());
                }
            } finally {
                if (isCancelled()) {
                    if (process != null) {
                        process.destroy();
                        cmdArea.append("PROCESS STOPPED");
                    }
                }
            }
        }
        return null;
    }
}
