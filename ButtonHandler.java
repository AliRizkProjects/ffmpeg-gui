import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ButtonHandler implements ActionListener {
    FfmpegGUI gui;
    FileNameExtensionFilter filter = new FileNameExtensionFilter("MP4 Files (*.mp4)", "mp4");

    public ButtonHandler(FfmpegGUI gui){
        this.gui = gui;
        gui.cancelButton.addActionListener(this);
        gui.browseButton.addActionListener(this);
        gui.compressButton.addActionListener(this);
        gui.fileChooser.setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==gui.cancelButton){
            gui.frame.dispose();
        }
        if (e.getSource()==gui.browseButton){
            int result = gui.fileChooser.showOpenDialog(gui.frame);
            if (result == JFileChooser.APPROVE_OPTION){
                File selectedFile = gui.fileChooser.getSelectedFile();
                if (selectedFile.getName().toLowerCase().endsWith(".mp4")){
                    gui.textField.setText(selectedFile.getAbsolutePath());
                }
                else {
                    JOptionPane.showMessageDialog(gui.frame, "Please choose a .mp4 file", "Wrong file", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}