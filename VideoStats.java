import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VideoStats {
    private FFmpegGUI gui;
    public VideoStats(String inputFilePath){
        Path path = Paths.get(inputFilePath);

        try {
            String fileSize = getFileSize(inputFilePath);
            String fileName = path.getFileName().toString();
            String videoLength = getVideoDuration(inputFilePath);
            gui.getPropArea().setText("Name: "+fileName+ "\nSize: "+fileSize+"\nLength: "+ videoLength+"sec");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String getFileSize(String inputFilePath) throws IOException {
        Path path = Paths.get(inputFilePath);

        double fileSize = Files.size(path);
        // format bytes into megabytes
        fileSize = fileSize / (1024*1024);
        String formattedSize = String.format("%.2f"+"MB", fileSize);
        return formattedSize;
    }

    // get video duration
    public String getVideoDuration(String inputFilePath){
        try{
            List<String> command = new ArrayList<>();
            command.add("ffprobe");
            command.add("-v");
            command.add("error");
            command.add("-show_entries");
            command.add("format=duration");
            command.add("-of");
            command.add("default=noprint_wrappers=1");
            command.add(inputFilePath);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder durationBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            durationBuilder.append(line);
        }
        process.waitFor();

        String duration = durationBuilder.toString().replace("duration=","");

        double formattedDuration = Double.parseDouble(duration);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        duration = decimalFormat.format(formattedDuration);
        return duration;

    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        return null;
    }
    }
}
