import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ButtonHandler implements ActionListener {
    private FFmpegGUI gui;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("MP4 Files (*.mp4)", "mp4");
    private FFmpegHandler compressionWorker;

    public ButtonHandler(FFmpegGUI gui){
        this.gui = gui;
        gui.getCloseButton().addActionListener(this);
        gui.getStopButton().addActionListener(this);
        gui.getBrowseButton().addActionListener(this);
        gui.getCompressButton().addActionListener(this);
        gui.getOutputButton().addActionListener(this);
        gui.getFileChooser().setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==gui.getCloseButton()){
            if (compressionWorker != null) {
                compressionWorker.cancel(true);
            }
            gui.getMainframe().dispose();
        }
        if (e.getSource()==gui.getStopButton()){
            if (compressionWorker != null) {
                compressionWorker.cancel(true);
            }
        }
        if (e.getSource()==gui.getBrowseButton()){
            int file = gui.getFileChooser().showOpenDialog(gui.getMainframe());
            if (file == JFileChooser.APPROVE_OPTION){
                File selectedFile = gui.getFileChooser().getSelectedFile();
                if (selectedFile.getName().toLowerCase().endsWith(".mp4")){
                    gui.getVideoPath().setText(selectedFile.getAbsolutePath());
                    File parentDirectory = selectedFile.getParentFile();
                    new VideoStats(gui, selectedFile.getAbsolutePath());
                    if (parentDirectory != null){
                        gui.getOutputPath().setText(parentDirectory.getAbsolutePath());
                    }
                }
                else {
                    JOptionPane.showMessageDialog(gui.getMainframe(), "Please choose a .mp4 file", "Wrong file", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        if (e.getSource()==gui.getOutputButton()){
            gui.getFolderChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int folder = gui.getFolderChooser().showOpenDialog(gui.getMainframe());
            if (folder == JFileChooser.APPROVE_OPTION){
                File selectedDirectory = gui.getFolderChooser().getSelectedFile();
                gui.getOutputPath().setText(selectedDirectory.getAbsolutePath());
            }
        }   
        if (e.getSource()==gui.getCompressButton()){
            compressionWorker = new FFmpegHandler(gui);
            compressionWorker.execute();
        }
    }
}