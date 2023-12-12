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

        double videoDuration = Double.parseDouble(videoStats.getVideoDuration(videoFilePath));
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
                    // System.out.println(line);
                    cmdArea.append(line);
                    cmdArea.setCaretPosition(cmdArea.getDocument().getLength());

                    // forward cmd output to the progressbar
                    // get processed time via pattern matching
                    String pattern = "time=(\\d{2}):(\\d{2}):(\\d{2}.\\d{2})";
                    Pattern regex = Pattern.compile(pattern);
                    String time = line.toString();
                    Matcher matcher = regex.matcher(time);
            
                    if (matcher.find()) {
                        int processedHours = Integer.parseInt(matcher.group(1));
                        int processedMinutes = Integer.parseInt(matcher.group(2));
                        double processedSeconds = Double.parseDouble(matcher.group(3));
                        double processedTime = processedHours * 3600 + processedMinutes * 60 + processedSeconds; 
                        double progress = processedTime / videoDuration;
                        progressBar.setValue(processedMinutes);
                        System.out.println(processedTime);
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
