import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Locale;

public class VideoStats {
    String inputFilePath;
    String fileSize;
    String fileName;
    String videoLength;
    String displayText;

    public VideoStats(String inputFilePath) {

        Path path = Paths.get(inputFilePath);

        try {
            this.fileSize = getFileSize(inputFilePath);
            this.fileName = path.getFileName().toString();
            this.videoLength = getVideoDuration(inputFilePath);
            this.displayText = "Name: " + fileName + "\nSize: " + fileSize + "\nLength: " + videoLength + "sec";
        } catch (IOException e) {
            this.displayText = "Video statistics could not be determined.";
            e.printStackTrace();
        }
    }

    public String getDisplayText() {
        return this.displayText;
    }

    private static String getFileSize(String inputFilePath) throws IOException {
        Path path = Paths.get(inputFilePath);

        double fileSize = Files.size(path);
        // format bytes into megabytes
        fileSize = fileSize / (1000*1000);
        String formattedSize = String.format("%.2f" + "MB", fileSize);
        return formattedSize;
    }

    // get video duration
    public String getVideoDuration(String inputFilePath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ffprobe", "-v", "error", "-show_entries",
                    "format=duration", "-of", "default=noprint_wrappers=1", inputFilePath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder durationBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                durationBuilder.append(line);
            }
            process.waitFor();

            // get duration of video via pattern matching
            String pattern = "duration=([\\d.]+)";
            Pattern regex = Pattern.compile(pattern);
            String duration = durationBuilder.toString();
            Matcher matcher = regex.matcher(duration);

            if (matcher.find()) {
                duration = matcher.group(1);
                double formattedDuration = Double.parseDouble(duration);
                duration = String.format(Locale.US, "%.2f", formattedDuration);
                return duration;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVideoFrames(String inputFilePath) {
        //ffprobe -v error -select_streams v:0 -show_entries stream=nb_frames -of default=nokey=1:noprint_wrappers=1
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ffprobe", "-v", "error", "-select_streams",
                    "v:0", "-show_entries", "stream=nb_frames", "-of", "default=nokey=1:noprint_wrappers=1", inputFilePath);
            processBuilder.redirectErrorStream(true);
            
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            StringBuilder frameBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                frameBuilder.append(line);
            }
            process.waitFor();

            String frames = frameBuilder.toString();

            return frames;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
