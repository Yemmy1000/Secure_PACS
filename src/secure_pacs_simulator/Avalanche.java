package secure_pacs_simulator;

/**
 *
 * @author Kesia ISL
 */
public class Avalanche {
    
//    private static final String key1 = "0100100110000010100000011100001100010101110110001110100111001111010100100110101110000011100010110001001101110111101101000100100";
//    private static final String key2 = "10011111100110010001010011011101010001000000111101111010011001001010011110000001010010110010011101101100101111000011110000101001";
//    private static final String key3 = "10001010100011101010000110011101000000101010100110010110001101011011111110000010011101101001100011000000111100011101100010110011";
//    private static final String key4 = "00101010111011010101101110111010010100000001011100111110000010011111101000001111100000000110110110100111110101101100010010000111";
//    private static final String key5 = "01101011100100010010001111000011001010101001011000010101001100000011001011101001110111000111010001000010011000110011001101011000";
//    private static final String key6 = "001010101111011110101011000100110110010010001001110000110100000111111011110001001101111100111001001111110001011011111110100110";
//    private static final String key7 = "00001000011001101001010101111111100111101011001000101000000100010101101111010101011001010000000011000110000101011110100001111001";
//    private static final String key8 = "00100100001110010011011010101100110011001111111001101110011010111001111011000110110110011111110101101100110000010010100000101111";
//    private static final String key9 = "01010011100011101011001000010010000010111000110010101111100110001111011001011011110011110111111001100100001100101000011010101100";
//    private static final String key10 = "10011010110100100000111100000110110011110101100010011110111110111111110111110110011100011101110110110100100101100101101101100100";
    
    private static double bitCompare(String first_key, String second_key){
        double match = 0;
        
        for(int i=0; i<first_key.length(); i++){
            if(first_key.charAt(i) == second_key.charAt(i))
                match ++;
        }
        
        return match;
    }

    public static void main(String[] args){
        String key1 = "10100001010111011000000100000011111001111101010010101000111100111000010000000000110111110100111010001010000111011001111000100110";
        String key2 = "10011111100110010001010011011101010001000000111101111010011001001010011110000001010010110010011101101100101111000011110000101001";
        String key3 = "10001010100011101010000110011101000000101010100110010110001101011011111110000010011101101001100011000000111100011101100010110011";
        String key4 = "00101010111011010101101110111010010100000001011100111110000010011111101000001111100000000110110110100111110101101100010010000111";
        String key5 = "01101011100100010010001111000011001010101001011000010101001100000011001011101001110111000111010001000010011000110011001101011000";
        String key6 = "11100100011100000000111110001101110011010101001001000110111110000000000001100010010110111100001101111011100001110111111111011000";
        String key7 = "00001000011001101001010101111111100111101011001000101000000100010101101111010101011001010000000011000110000101011110100001111001";
        String key8 = "00100100001110010011011010101100110011001111111001101110011010111001111011000110110110011111110101101100110000010010100000101111";
        String key9 = "01010011100011101011001000010010000010111000110010101111100110001111011001011011110011110111111001100100001100101000011010101100";
        String key10 ="10011010110100100000111100000110110011110101100010011110111110111111110111110110011100011101110110110100100101100101101101100100";

        String keys[] = {key1, key2, key3, key4, key5, key6, key7, key8, key9, key10};
        
        for(int i=0; i< keys.length-1; i++){
            double result = (bitCompare(keys[i], keys[i+1])/key1.length())*100;
            System.out.println(result);
        }
        
        System.out.println(bitCompare(key1, key2));
    }
    
}
