import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartFfmpeg {

    FfmpegGUI gui;
    public StartFfmpeg(FfmpegGUI gui) {
        this.gui = gui;
        String videoFilePath = gui.textField.getText();

        // comand to be executed
        String command = "ffmpeg -i \"" + videoFilePath + "\" -vcodec libx264 -crf 28 \"C:\\Users\\Ali\\Videos\\output.mp4\"";
        System.out.println("Befehl: "+ command);
        // create and run process
        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Exitcode: "+ exitCode);
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        } 
    }
}

