/* LFSRExeception:
 * ---------------
 * Simple exception tossed by our LFSR generator. */

package secure_pacs_simulator;

/**
 *
 * @author Kesia ISL
 */


public class LFSRException extends Exception {
   public LFSRException (String s) {
      super (s);
   }
}
