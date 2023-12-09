import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FfmpegGUI {

    private JFrame mainFrame;
    private JButton compressButton;
    private JButton cancelButton;
    private JButton browseButton;

    private JPanel bottomPanel;
    private JPanel pfadPanel;
    private JLabel pfadLabel;
    private JFileChooser fileChooser;
    private JTextField videoPath;
    private JProgressBar pbar;

    public FfmpegGUI() {
        mainFrame = new JFrame("FFMPEG Interface");

        fileChooser = new JFileChooser();

        pbar = new JProgressBar();

        // Panel
        pfadPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel(new GridLayout(1, 2));
        
        // Buttons
        browseButton = new JButton("Browse...");
        cancelButton = new JButton("Close");
        compressButton = new JButton("Compress");

        videoPath = new JTextField();

        pfadLabel = new JLabel("Filepath: ");

        // Darstellung vom Pfad inkl. Button
        pfadLabel.setLabelFor(videoPath);
        videoPath.setEditable(false);
        videoPath.setFont(new Font("Arial", Font.PLAIN, 18));

        pfadPanel.setPreferredSize(new Dimension(50,40));
        pfadPanel.add(pfadLabel, BorderLayout.WEST);
        pfadPanel.add(browseButton, BorderLayout.EAST);
        pfadPanel.add(videoPath);
        pfadPanel.setBorder(new EmptyBorder(5, 5, 5, 20));

        // Darstellung Cancel + Compress Button

        cancelButton.setPreferredSize(new Dimension(100,40));
        compressButton.setPreferredSize(new Dimension(100,40));
        bottomPanel.add(compressButton);
        bottomPanel.add(cancelButton);
        bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));



        mainFrame.add(pfadPanel, BorderLayout.NORTH);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        mainFrame.setSize(1000, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        new ButtonHandler(this);
    }

    public static void main(String[] args) {
        new FfmpegGUI();
    }

    public JButton getCompressButton() {
        return compressButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }

    public JTextField getVideoPath() {
        return videoPath;
    }
    public JFrame getMainframe(){
        return mainFrame;
    }
    public JFileChooser getFileChooser(){
        return fileChooser;
    }
}

