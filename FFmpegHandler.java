import java.io.BufferedReader;
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
        Process process = null;
        
        String videoFilePath = gui.getVideoPathTextField().getText();
        String videoOutputPath = gui.getOutputPathTextField().getText(); 

        // command to be executed
        String command = "ffmpeg -i \"" + videoFilePath + "\" -vcodec libx264 -crf "+gui.getCrfComboBox().getSelectedItem()+" \""+ videoOutputPath +"\\"+gui.getOutputName().getText()+"\"";
        

        // check if selected outputname already exists in selected outputfolder
        String checkFilename = gui.getOutputName().getText();
        Path checkFilepath = Paths.get(videoOutputPath, checkFilename);
        if (Files.exists(checkFilepath)){
            JOptionPane.showMessageDialog(gui.getMainframe(), "Sorry, but the chosen filename already exists. Please pick a different name.","File already exists", JOptionPane.WARNING_MESSAGE);
            }
        else {
        // create and run process
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.redirectErrorStream(true);
            try {
                process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while (!isCancelled() && (line = reader.readLine()) != null){
                    System.out.println(line);
                    gui.getCmdArea().append(line);
                    gui.getCmdArea().setCaretPosition(gui.getCmdArea().getDocument().getLength());
                }
            } finally {
                if (isCancelled()){
                    process.destroy();
                    gui.getCmdArea().append("\n!!!!! PROCESS STOPPED !!!!!");
                    gui.getCmdArea().setCaretPosition(gui.getCmdArea().getDocument().getLength());
                }
            }
        }
        return null;
    }
}

