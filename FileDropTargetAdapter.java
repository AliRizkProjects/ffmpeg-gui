import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class FileDropTargetAdapter extends DropTargetAdapter {
    JFrame mainFrame;
    JTextField outputPathTextField;
    JTextField videoPathTextField;
    JTextArea propArea;

    public FileDropTargetAdapter(JFrame mainFrame, JTextField outputPathTextField, JTextField videoPathTextField, JTextArea propArea) {
        this.mainFrame = mainFrame;
        this.outputPathTextField = outputPathTextField;
        this.videoPathTextField = videoPathTextField;
        this.propArea = propArea;
    }

    public void drop(DropTargetDropEvent event) {
        event.acceptDrop(DnDConstants.ACTION_COPY);

        try {
            Transferable transferable = event.getTransferable();
            DataFlavor[] flavors = transferable.getTransferDataFlavors();

            for (DataFlavor flavor : flavors) {
                if (flavor.isFlavorJavaFileListType()) {

                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) transferable.getTransferData(flavor);

                    if (!files.isEmpty()) {
                        File droppedFile = files.get(0);
                        if (droppedFile.getName().toLowerCase().endsWith(".mp4")) {
                            String droppedFilePath = droppedFile.getAbsolutePath();
                            videoPathTextField.setText(droppedFilePath);
                            File parentDirectory = droppedFile.getParentFile();
                            VideoStats stats = new VideoStats(droppedFilePath);
                            propArea.setText(stats.getDisplayText());
                            if (parentDirectory != null) {
                                outputPathTextField.setText(parentDirectory.getAbsolutePath());
                            }
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Please choose a .mp4 file", "Not a mp4 file",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
