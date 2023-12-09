import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;




public class ButtonHandler implements ActionListener {
    private FfmpegGUI gui;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("MP4 Files (*.mp4)", "mp4");
    private StartFfmpeg compressionWorker;

    public ButtonHandler(FfmpegGUI gui){
        this.gui = gui;
        gui.getCancelButton().addActionListener(this);
        gui.getBrowseButton().addActionListener(this);
        gui.getCompressButton().addActionListener(this);
        gui.getFileChooser().setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==gui.getCancelButton()){
            if (compressionWorker != null) {
                compressionWorker.cancel(true);
            }
            gui.getMainframe().dispose();
        }
        if (e.getSource()==gui.getBrowseButton()){
            int result = gui.getFileChooser().showOpenDialog(gui.getMainframe());
            if (result == JFileChooser.APPROVE_OPTION){
                File selectedFile = gui.getFileChooser().getSelectedFile();
                if (selectedFile.getName().toLowerCase().endsWith(".mp4")){
                    gui.getVideoPath().setText(selectedFile.getAbsolutePath());
                }
                else {
                    JOptionPane.showMessageDialog(gui.getMainframe(), "Please choose a .mp4 file", "Wrong file", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        if (e.getSource()==gui.getCompressButton()){
            compressionWorker = new StartFfmpeg(gui);
            compressionWorker.execute();
        }
    }
}