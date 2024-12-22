package secure_pacs_simulator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Kesia ISL
 */

public class LFSR extends Random{

    public boolean[] seed;          // length of register
    private BigInteger rand_bits;                        
    int TAP = 8;                   // tap position
    
    public LFSR(){
        SecureRandom rand = new SecureRandom();  
        setRand_bits(randomBitsNonZero(128, rand));
        this.seed = getSeed();
    }

    public BigInteger getRand_bits() {
        return rand_bits;
    }

    public void setRand_bits(BigInteger rand_bits) {
        this.rand_bits = rand_bits;
    }

    
    public static BigInteger randomBitsNonZero(int numBits, Random r){
        BigInteger bits = new BigInteger(numBits,r);
        while (bits.equals(BigInteger.ZERO)){
            bits = new BigInteger(numBits, r);
        }
        return bits;
    }
       
       
        public boolean[] getSeed(){        
            String mStr = getRand_bits().toString(2);
//            System.out.printf("%s \t %s \n", "Inner Print len", mStr.length());
//            System.out.printf("%s \t %s \n", "Inner Print String", mStr);
            boolean[] ax = new boolean[mStr.length()];
            for(int j=0; j<mStr.length(); j++) {
                ax[j] = ('1' == mStr.charAt(j)) ? true : false;
            }

            return ax;
        }
        
       // Simulate operation of shift register.
        public String Simulate(){
            int bit_count = seed.length;
            String long_str = "";
            for (int t = 0; t < bit_count; t++) {

            // Simulate one shift-register step.
            boolean next = (seed[bit_count-1] ^ seed[TAP]);  // Compute next bit.

            for (int i = bit_count-1; i > 0; i--)
                seed[i] = seed[i-1];                  // Shift one position.

            seed[0] = next;                       // Put next bit on right end.

            if (next) long_str += "1";
            else     long_str += "0";
        }
            int diff = 128 - long_str.length();
            if(diff < 128){
                for(int i = 0; i < diff; i++){
                    long_str += "0";
                }
            }        
            return long_str;
   
        }     
        

}


