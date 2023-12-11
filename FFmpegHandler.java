import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
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
        String command = "ffmpeg -i \"" + videoFilePath + "\" -vcodec libx264 -crf "+gui.getCrfComboBox().getSelectedItem()+" \""+ videoOutputPath +"\\"+gui.getOutputName().getText()+"\"";
        

        // check if selected outputname already exists in selected outputfolder
        String checkFilename = gui.getOutputName().getText();
        Path checkFilepath = Paths.get(videoOutputPath, checkFilename);
        if (Files.exists(checkFilepath)){
            JOptionPane.showMessageDialog(gui.getMainframe(), "Filename already exists, please choose a different name","Same file", JOptionPane.WARNING_MESSAGE);
            }
        else {
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
        }
        return null;
    }
}

