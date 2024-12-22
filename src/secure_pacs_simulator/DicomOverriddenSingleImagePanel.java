package secure_pacs_simulator;

/**
 *
 * @author Kesia ISL
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import com.pixelmed.display.SingleImagePanel;
import com.pixelmed.display.SourceImage;
import com.pixelmed.display.event.FrameSelectionChangeEvent;
import com.pixelmed.event.ApplicationEventDispatcher;
import com.pixelmed.event.EventContext;

public class DicomOverriddenSingleImagePanel extends SingleImagePanel{
    
            //initialize these to some default values
        private static final long serialVersionUID = 1L;
        private int frameIndex = 0;
        private int MaxFrames = 1;

        public DicomOverriddenSingleImagePanel(SourceImage sImg) {
            super(sImg);
            MaxFrames = sImg.getNumberOfFrames();
            this.setSideAndViewAnnotationString(getTextToDisplay(1),30, "SansSerif",Font.BOLD, 14, Color.WHITE,true);
        }

        private String getTextToDisplay(int frameIndexNumber) {
            return " Window Width->" + (int) this.windowWidth
                    + " Level(or Center)->" + (int) this.windowCenter
                    + " Frame Index->" + frameIndexNumber;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if(frameIndex == MaxFrames){ //this is to reset the frame loop
                frameIndex = 0;
            }
            ApplicationEventDispatcher.getApplicationEventDispatcher()
            .processEvent(new FrameSelectionChangeEvent(new EventContext("Pass info here"), frameIndex++));
            UpdateDisplayInformation();

        }

        private void UpdateDisplayInformation() {
            this.sideAndViewAnnotationString = getTextToDisplay(frameIndex);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            UpdateDisplayInformation();
        }

}
