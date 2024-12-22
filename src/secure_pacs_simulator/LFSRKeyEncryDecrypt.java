
package secure_pacs_simulator;

/**
 *
 * @author Kesia ISL
 */
public class LFSRKeyEncryDecrypt {
    
    
    
    public byte[] getEncryptedLFSRKey(int id, String username) throws Exception{
        
        RSA rsa = new RSA();
        
        SQLiteDBSystem db = new SQLiteDBSystem();
        db.connect();
        
        String key = db.SelectLFSRKey(id);
//        System.out.printf("%s \t %s \n", "LFSR key: ", key);
//        String cipher_lfsr_key = rsa.encrypt_(key, pub_key);
        String key_filename = "public_"+username+".key";
//        byte[] cipher_lfsr_key = rsa.encrypt(key, key_filename);  
        
     
        
//        System.out.printf("%s \t %s \n", "Encrypted LFSR key: ", cipher_lfsr_key);
        return null;
    }
    
}
