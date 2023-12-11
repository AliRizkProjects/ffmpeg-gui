import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.dnd.DropTarget;

public class FFmpegGUI {

    private JFrame mainFrame;
    
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JButton compressButton;
    private JButton closeButton;
    private JButton stopButton;
    private JButton browseButton;
    private JButton outputButton;

    private JComboBox<String> crfComboBox;

    private JProgressBar pb;

    private JLabel pathLabel;
    private JLabel compressionLabel;
    private JLabel outputLabel;
    
    private JFileChooser fileChooser;
    private JFileChooser folderChooser;
    
    private JTextField videoPathTextField;
    private JTextField outputPathTextField;
    private JTextField outputName;
    
    private JScrollPane cmdScroll;
    private JTextArea cmdArea;
    private JTextArea propArea;

    public FFmpegGUI() {

        mainFrame = new JFrame("FFMPEG Interface");

        fileChooser = new JFileChooser();
        folderChooser = new JFileChooser();

        String[] crfValues = {"20", "24", "28", "32", "36"};
        crfComboBox = new JComboBox<>(crfValues);

        // Panel
        topPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel();
        leftPanel = new JPanel();
        bottomPanel = new JPanel();
        rightPanel = new JPanel();
// test
        
        // Buttons
        browseButton = new JButton("Browse...");
        closeButton = new JButton("Close");
        stopButton = new JButton("Stop Process");
        compressButton = new JButton("Compress");
        outputButton = new JButton("Choose Output Location");
        outputButton.setAlignmentX(1);

        // Texts
        videoPathTextField = new JTextField();
        outputPathTextField = new JTextField();
        outputName = new JTextField();
        cmdArea = new JTextArea();
        cmdScroll = new JScrollPane(cmdArea);
        propArea = new JTextArea();
        pathLabel = new JLabel("File: ");
        compressionLabel = new JLabel("Compression Intensity (CRF Value)");
        outputLabel = new JLabel("Set Output Name");

        pb = new JProgressBar();

        // design Textfields
        pathLabel.setLabelFor(videoPathTextField);
        videoPathTextField.setEditable(false);
        videoPathTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        outputPathTextField.setEditable(false);
        outputPathTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        outputPathTextField.setMaximumSize(new Dimension(300,18));
        outputPathTextField.setAlignmentX(1);

        // properties area
        
        propArea.setPreferredSize(new Dimension(300, 80));
        propArea.setMinimumSize(new Dimension(300, 80));
        propArea.setMaximumSize(new Dimension(300, 80));
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
        cmdArea.setForeground(Color.WHITE);
        cmdArea.setBackground(Color.BLACK);
        cmdArea.setLineWrap(true);
        cmdArea.setWrapStyleWord(true);
        cmdScroll.setBorder(BorderFactory.createTitledBorder("Console output"));
        cmdScroll.setAlignmentX(0);
        cmdScroll.setPreferredSize(new Dimension(350,100));
        cmdScroll.setMinimumSize(new Dimension(350,100));
        cmdScroll.setMaximumSize(new Dimension(500, 300));
        cmdScroll.setBackground(mainFrame.getBackground());

        // progress bar
        pb.setMinimumSize(new Dimension(350, 25));
        pb.setPreferredSize(new Dimension(350,25));
        pb.setMaximumSize(new Dimension(350,25));
        pb.setAlignmentX(0);
        pb.setValue(0);
        pb.setStringPainted(true);
  
        // cancel + compress button design

        closeButton.setPreferredSize(new Dimension(100,40));
        compressButton.setPreferredSize(new Dimension(100,40));

        // dropdown menu crf value
        outputLabel.setAlignmentX(1);
        outputName.setAlignmentX(1);
        crfComboBox.setAlignmentX(1);
        compressionLabel.setAlignmentX(1);

        crfComboBox.setMinimumSize(new Dimension(50, 25));
        crfComboBox.setMaximumSize(new Dimension(50, 25));
        crfComboBox.setPreferredSize(new Dimension(50, 25));
        crfComboBox.setToolTipText("<html>Adjust the CRF (Constant Rate Factor) value for compression<br/>Higher values result in stronger compression<br/>You also have the option to set this value manually</html>");
        crfComboBox.setBorder(new EmptyBorder(0, 0, 5, 0));
        crfComboBox.setEditable(true);

        compressionLabel.setLabelFor(crfComboBox);

        outputName.setText("output.mp4");
        outputName.setFont(new Font("Arial", Font.PLAIN, 14));
        outputName.setMaximumSize(new Dimension(300,18));

        
        // panels
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(compressButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(closeButton);
        bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        topPanel.setPreferredSize(new Dimension(50,30));
        topPanel.add(pathLabel, BorderLayout.WEST);
        topPanel.add(browseButton, BorderLayout.EAST);
        topPanel.add(videoPathTextField);
        topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(propArea);
        leftPanel.add(cmdScroll);
        leftPanel.add(pb);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(compressionLabel);
        rightPanel.add(crfComboBox);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(outputPathTextField);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(outputButton);
        rightPanel.add(Box.createVerticalStrut(50));
        rightPanel.add(outputName);
        rightPanel.add(outputLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // frame
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(rightPanel, BorderLayout.EAST);
        mainFrame.setSize(750, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        new ButtonHandler(this);
        new DropTarget(mainFrame, new FileDropTargetAdapter(this));
        new DropTarget(cmdArea, new FileDropTargetAdapter(this));
        new DropTarget(videoPathTextField, new FileDropTargetAdapter(this));
        new DropTarget(outputPathTextField, new FileDropTargetAdapter(this));
        new DropTarget(propArea, new FileDropTargetAdapter(this));
        new DropTarget(outputName, new FileDropTargetAdapter(this));
        // new DropTarget(cmdArea, new FileDropTargetAdapter(this));
    }

    // Getter Methods
    public JButton getCompressButton() {
        return compressButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }
    
    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }
    public JButton getOutputButton(){
        return outputButton;
    }

    public JTextField getVideoPathTextField() {
        return videoPathTextField;
    }
    public JTextField getOutputPathTextField(){
        return outputPathTextField;
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
        return crfComboBox;
    }
    public JTextField getOutputName(){
        return outputName;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FFmpegGUI());
    }
}
