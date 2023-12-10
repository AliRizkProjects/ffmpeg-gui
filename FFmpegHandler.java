import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.SwingWorker;

public class FFmpegHandler extends SwingWorker<Void, String>{
    FFmpegGUI gui;
    private String line;
    public FFmpegHandler(FFmpegGUI gui) {
        this.gui = gui;
    }
     
    protected Void doInBackground() throws Exception{
        
        String videoFilePath = gui.getVideoPath().getText();
        String videoOutputPath = gui.getOutputPath().getText(); 

        // command to be executed
        String command = "ffmpeg -i \"" + videoFilePath + "\" -vcodec libx264 -crf 28 \""+ videoOutputPath +"\\"+gui.getOutputName().getText()+"\"";
    
        // create and run process
        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null){
                System.out.println(line);
                gui.getCmdArea().append(line);
                gui.getCmdArea().setCaretPosition(gui.getCmdArea().getDocument().getLength());
            }
            int exitCode = process.waitFor();
            System.out.println("Exitcode: "+ exitCode);
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        } 
        return null;
    }
}

