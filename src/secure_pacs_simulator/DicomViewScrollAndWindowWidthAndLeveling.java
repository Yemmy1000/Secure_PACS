package secure_pacs_simulator;

/**
 *
 * @author Kesia ISL
 * https://saravanansubramanian.com/viewingdicomimages/#example-of-viewing-dicom-images
 */
import java.awt.Color;
import javax.swing.JFrame;
import com.pixelmed.display.SourceImage;

public class DicomViewScrollAndWindowWidthAndLeveling {
    
//        public static void main(String[] args) {
        public static void showImage(String fileName) {
//         String dicomInputFile = "dicom_sample.DCM";
         String dicomInputFile = fileName;
         try {
             JFrame frame = new JFrame();
             SourceImage sImg = new SourceImage(dicomInputFile);
             System.out.println("Number of frames: " + sImg.getNumberOfFrames());
             DicomOverriddenSingleImagePanel singleImagePanel = new DicomOverriddenSingleImagePanel(sImg);
             frame.add(singleImagePanel);
             frame.setBackground(Color.BLACK);
             frame.setSize(sImg.getWidth(),sImg.getHeight());
             frame.setTitle("Demo for view, scroll and window width/level operations");
             frame.setVisible(true);

         } catch (Exception e) {
             e.printStackTrace(); //in real life, do something about this exception
         }
     }
}
