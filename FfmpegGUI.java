import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class FfmpegGUI {
     
    private JFrame mainFrame;
    
    private JPanel buttonPanel;
    private JPanel pathPanel;
    private JPanel propcmdPanel;
    private JPanel crfPanel;

    private JButton compressButton;
    private JButton cancelButton;
    private JButton browseButton;
    private JButton outputButton;

    private JComboBox<String> crfDropDown;

    private JProgressBar pb;

    private JLabel pathLabel;
    private JLabel compression;
    
    private JFileChooser fileChooser;
    private JFileChooser folderChooser;
    
    private JTextField videoPath;
    private JTextField outputPath;
    
    private JScrollPane cmdScroll;
    private JTextArea cmdArea;
    private JTextArea propArea;

    public FfmpegGUI() {
        mainFrame = new JFrame("FFMPEG Interface");

        fileChooser = new JFileChooser();
        folderChooser = new JFileChooser();

        String[] crfValues = {"20 (Very Low)", "24 (Low)", "28 (Medium)", "32 (High)", "36 (Very High)"};
        crfDropDown = new JComboBox<>(crfValues);

        // Panel
        pathPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        propcmdPanel = new JPanel();
        buttonPanel = new JPanel();
        crfPanel = new JPanel();

        
        // Buttons
        browseButton = new JButton("Browse...");
        cancelButton = new JButton("Close");
        compressButton = new JButton("Compress");
        outputButton = new JButton("Choose Output Location");
        outputButton.setAlignmentX(1);

        // Texts
        videoPath = new JTextField();
        outputPath = new JTextField();
        cmdArea = new JTextArea();
        cmdScroll = new JScrollPane(cmdArea);
        propArea = new JTextArea();
        pathLabel = new JLabel("File: ");
        compression = new JLabel("Compression Intensity");

        pb = new JProgressBar();

        // design Textfields
        pathLabel.setLabelFor(videoPath);
        videoPath.setEditable(false);
        videoPath.setFont(new Font("Arial", Font.PLAIN, 14));
        outputPath.setEditable(false);
        outputPath.setFont(new Font("Arial", Font.PLAIN, 14));
        outputPath.setMaximumSize(new Dimension(300,18));
        outputPath.setAlignmentX(1);

        // properties area
        
        propArea.setPreferredSize(new Dimension(200, 80));
        propArea.setMinimumSize(new Dimension(200, 80));
        propArea.setMaximumSize(new Dimension(200, 80));
        propArea.setBackground(mainFrame.getBackground());
        propArea.setEditable(false);
        propArea.setBorder(BorderFactory.createTitledBorder("Properties"));
        propArea.setAlignmentX(0);
        propArea.setText("Name: \nSize: \nLength: ");

        // cmd area
        // cmdArea.setPreferredSize(new Dimension(300,100));
        // cmdArea.setMinimumSize(new Dimension(300,100));
        // cmdArea.setMaximumSize(new Dimension(300, 200));
        cmdArea.setBackground(mainFrame.getBackground());
        cmdArea.setEditable(true);
        cmdArea.setFont(new Font("Consolas", Font.PLAIN, 10));
        cmdArea.setLineWrap(true);
        cmdArea.setWrapStyleWord(true);
        cmdScroll.setBorder(BorderFactory.createTitledBorder("Console"));
        cmdScroll.setAlignmentX(0);
        cmdScroll.setPreferredSize(new Dimension(350,100));
        cmdScroll.setMinimumSize(new Dimension(350,100));
        cmdScroll.setMaximumSize(new Dimension(350, 200));
        cmdScroll.setBackground(mainFrame.getBackground());

        // progress bar
        pb.setMinimumSize(new Dimension(350, 25));
        pb.setPreferredSize(new Dimension(350,25));
        pb.setMaximumSize(new Dimension(350,25));
        pb.setAlignmentX(0);
        pb.setValue(0);
        pb.setStringPainted(true);
  
        // cancel + compress button design

        cancelButton.setPreferredSize(new Dimension(100,40));
        compressButton.setPreferredSize(new Dimension(100,40));

        // dropdown menu crf value
        crfDropDown.setMinimumSize(new Dimension(120, 25));
        crfDropDown.setMaximumSize(new Dimension(120, 25));
        crfDropDown.setPreferredSize(new Dimension(120, 25));
        crfDropDown.setToolTipText("<html>Adjust the CRF (Constant Rate Factor) value for compression<br/>Higher values result in stronger compression<br/>You also have the option to set this value manually</html>");
        crfDropDown.setAlignmentX(1);
        crfDropDown.setBorder(new EmptyBorder(0, 0, 5, 0));
        crfDropDown.setEditable(true);
        compression.setLabelFor(crfDropDown);
        compression.setAlignmentX(1);
        
        // panels
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(compressButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        pathPanel.setPreferredSize(new Dimension(50,30));
        pathPanel.add(pathLabel, BorderLayout.WEST);
        pathPanel.add(browseButton, BorderLayout.EAST);
        pathPanel.add(videoPath);
        pathPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        propcmdPanel.setLayout(new BoxLayout(propcmdPanel, BoxLayout.Y_AXIS));
        propcmdPanel.add(propArea);
        propcmdPanel.add(cmdScroll);
        propcmdPanel.add(pb);

        crfPanel.setLayout(new BoxLayout(crfPanel, BoxLayout.Y_AXIS));
        crfPanel.add(compression);
        crfPanel.add(crfDropDown);
        // crfPanel.add(Box.createVerticalGlue());
        crfPanel.add(outputPath);
        crfPanel.add(Box.createVerticalStrut(5));
        crfPanel.add(outputButton);
        crfPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // frame
        mainFrame.add(pathPanel, BorderLayout.NORTH);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.add(propcmdPanel, BorderLayout.WEST);
        mainFrame.add(crfPanel, BorderLayout.EAST);
        mainFrame.setSize(550, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        new ButtonHandler(this);
    }

    // Getter Methods
    public JButton getCompressButton() {
        return compressButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }
    public JButton getOutputButton(){
        return outputButton;
    }

    public JTextField getVideoPath() {
        return videoPath;
    }
    public JTextField getOutputPath(){
        return outputPath;
    }
    public JFrame getMainframe(){
        return mainFrame;
    }
    public JFileChooser getFileChooser(){
        return fileChooser;
    }
    public JFileChooser getFolderChooser(){
        return folderChooser;
    }
    public JTextArea getCmdArea(){
        return cmdArea;
    }
    public JTextArea getPropArea(){
        return propArea;
    }
    public JProgressBar getProgressBar(){
        return pb;
    }
    public JComboBox<String> getCrfComboBox(){
        return crfDropDown;
    }

    public static void main(String[] args) {
        new FfmpegGUI();
    }
}

