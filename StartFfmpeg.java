import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.SwingWorker;

public class StartFfmpeg extends SwingWorker<Void, String>{
    FfmpegGUI gui;
    private String line;
    public StartFfmpeg(FfmpegGUI gui) {
        this.gui = gui;
    }
     
    protected Void doInBackground() throws Exception{
        
        String videoFilePath = gui.getVideoPath().getText();
        String videoOutputPath = gui.getOutputPath().getText(); 

        // comand to be executed
        String command = "ffmpeg -i \"" + videoFilePath + "\" -vcodec libx264 -crf 28 \""+ videoOutputPath +"\\output.mp4\"";
        System.out.println("Befehl: "+ command);
        // create and run process
        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null){
                System.out.println(line);
                gui.getCmdArea().append(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Exitcode: "+ exitCode);
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        } 
        return null;
    }
}

