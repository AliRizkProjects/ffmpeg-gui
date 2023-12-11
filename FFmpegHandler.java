import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class FFmpegHandler extends SwingWorker<Void, String>{
    FFmpegGUI gui;

    public FFmpegHandler(FFmpegGUI gui) {
        this.gui = gui;
    }
     
    protected Void doInBackground() throws Exception{
        Process process = null;
        
        String videoFilePath = gui.getVideoPathTextField().getText();
        String videoOutputPath = gui.getOutputPathTextField().getText(); 
        String crfOption = (String) gui.getCrfComboBox().getSelectedItem();

        // check if selected outputname already exists in selected outputfolder
        String checkFilename = gui.getOutputName().getText();
        Path checkFilepath = Paths.get(videoOutputPath, checkFilename);
        if (Files.exists(checkFilepath)) {
            JOptionPane.showMessageDialog(gui.getMainframe(),
                    "Sorry, but the chosen filename already exists. Please pick a different name.",
                    "File already exists", JOptionPane.WARNING_MESSAGE);
        } else {
            // create and run process
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", videoFilePath, "-vcodec", "libx264", "-crf",
                    crfOption, checkFilepath.toString());
            pb.redirectErrorStream(true);
            try {
                process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while (!isCancelled() && (line = reader.readLine()) != null) {
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

