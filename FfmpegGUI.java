import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FfmpegGUI {

    JFrame frame;
    JButton compressButton;
    JButton cancelButton;
    JButton browseButton;

    JPanel bottomPanel;
    JPanel pfadPanel;
    JLabel pfadLabel;
    JFileChooser fileChooser;
    JTextField textField;
    JProgressBar pbar;

    public FfmpegGUI() {
        frame = new JFrame("FFMPEG Interface");

        fileChooser = new JFileChooser();

        pbar = new JProgressBar();

        // Panel
        pfadPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel(new GridLayout(1, 2));
        
        // Buttons
        browseButton = new JButton("Browse...");
        cancelButton = new JButton("Close");
        compressButton = new JButton("Compress");

        textField = new JTextField();

        pfadLabel = new JLabel("Filepath: ");

        // Darstellung vom Pfad inkl. Button
        pfadLabel.setLabelFor(textField);
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 18));

        pfadPanel.setPreferredSize(new Dimension(50,40));
        pfadPanel.add(pfadLabel, BorderLayout.WEST);
        pfadPanel.add(browseButton, BorderLayout.EAST);
        pfadPanel.add(textField);
        pfadPanel.setBorder(new EmptyBorder(5, 5, 5, 20));

        // Darstellung Cancel + Compress Button

        cancelButton.setPreferredSize(new Dimension(100,40));
        compressButton.setPreferredSize(new Dimension(100,40));
        bottomPanel.add(compressButton);
        bottomPanel.add(cancelButton);
        bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));



        frame.add(pfadPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new ButtonHandler(this);
    }

    public static void main(String[] args) {
        new FfmpegGUI();
    }

}

