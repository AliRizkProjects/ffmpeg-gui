import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class FFmpegHandler extends SwingWorker<Void, String> {
    private String videoFilePath;
    private String videoOutputPath;
    private String videoOutputFilename;
    private String crfOption;

    private JFrame mainFrame;
    private JTextArea cmdArea;
    private VideoStats videoStats;
    private JProgressBar progressBar;

    public FFmpegHandler(JFrame mainFrame, JTextArea cmdArea, String videoFilePath, String videoOutputPath,
            String videoOutputFilename, String crfOption, JProgressBar progresBar) {
        this.mainFrame = mainFrame;
        this.cmdArea = cmdArea;
        this.videoFilePath = videoFilePath;
        this.videoOutputPath = videoOutputPath;
        this.videoOutputFilename = videoOutputFilename;
        this.crfOption = crfOption;
        this.progressBar = progresBar;
        this.videoStats = new VideoStats(videoFilePath);
    }

    protected Void doInBackground() throws Exception {
        // check if selected outputname already exists in selected outputfolder
        Path videoOutputFilepath = Paths.get(videoOutputPath, videoOutputFilename);
        double videoFrames = Double.parseDouble(videoStats.getVideoFrames(videoFilePath));
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
                    // System.out.println(line);
                    cmdArea.append(line);
                    cmdArea.setCaretPosition(cmdArea.getDocument().getLength());

                    // forward cmd output to the progressbar
                    // get processed frames via pattern matching
                    String pattern = "frame=\\s*(\\d{1,6})";
                    Pattern regex = Pattern.compile(pattern);
                    String processedFrames = line.toString();
                    Matcher matcher = regex.matcher(processedFrames);
            
                    if (matcher.find()) {
                        int frame = Integer.parseInt(matcher.group(1));
                        double progress = (frame / videoFrames) * 100;
                        progressBar.setValue((int) progress);
                    }
                }
            } finally {
                if (isCancelled()) {
                    if (process != null) {
                        process.destroy();
                    }
                    cmdArea.append("\n!!!!! PROCESS STOPPED !!!!!");
                    cmdArea.setCaretPosition(cmdArea.getDocument().getLength());
                }
            }
        }
        return null;
    }
}
