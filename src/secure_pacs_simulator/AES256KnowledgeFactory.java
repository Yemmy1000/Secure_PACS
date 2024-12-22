/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secure_pacs_simulator;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
/**
 *
 * @author Life Learners Ltd
 */
public class AES256KnowledgeFactory {
//    private static final String SECRET_KEY = "my_super_secret_key";
    static String SECRET_KEY = "";
    private static final String SALT = "ssshhhhhhhhhhh!!!!";

    public static String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public static void setSECRET_KEY(String SECRET_KEY) {
        AES256KnowledgeFactory.SECRET_KEY = SECRET_KEY;
    }
       
    
    public static String encrypt(String strToEncrypt) {
        try {
          byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
          IvParameterSpec ivspec = new IvParameterSpec(iv);
        // file to be encrypted
        FileInputStream inFile = new FileInputStream(strToEncrypt);
//        String encryptedfile = strToEncrypt+".enc";
        String encryptedfile = strToEncrypt+".enc";
        // encrypted file
        FileOutputStream outFile = new FileOutputStream(encryptedfile);

          SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
          KeySpec spec = new PBEKeySpec(getSECRET_KEY().toCharArray(), SALT.getBytes(), 65536, 256);
          SecretKey tmp = factory.generateSecret(spec);
          SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
          cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
          
		byte[] input = new byte[64];
		int bytesRead;

		while ((bytesRead = inFile.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, bytesRead);
			if (output != null)
				outFile.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			outFile.write(output);

		inFile.close();
		outFile.flush();
		outFile.close();
        } catch (Exception e) {
          System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
  }
    
    public static String encrypt_old(String strToEncrypt) {
        try {
          byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
          IvParameterSpec ivspec = new IvParameterSpec(iv);

          SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
          KeySpec spec = new PBEKeySpec(getSECRET_KEY().toCharArray(), SALT.getBytes(), 65536, 256);
          SecretKey tmp = factory.generateSecret(spec);
          SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
          cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
          return Base64.getEncoder()
              .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
          System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
  }    
    
    public static String decrypt(String strToDecrypt) {
       String file_name = "Dicom_decrypted.DCM";
    try {
      byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      IvParameterSpec ivspec = new IvParameterSpec(iv);
 
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      KeySpec spec = new PBEKeySpec(getSECRET_KEY().toCharArray(), SALT.getBytes(), 65536, 256);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
 
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
      FileInputStream fis = new FileInputStream(strToDecrypt);
      
      FileOutputStream fos = new FileOutputStream(file_name);     
      
      byte[] in = new byte[64];
      int read;
      while ((read = fis.read(in)) != -1) {
              byte[] output = cipher.update(in, 0, read);
              if (output != null)
                      fos.write(output);
      }

      byte[] output = cipher.doFinal();
      if (output != null)
              fos.write(output);
      fis.close();
      fos.flush();
      fos.close();
      System.out.println("File Decrypted.");
//      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return file_name;
  }
    
   public static String decrypt_old(String strToDecrypt) {
    try {
      byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      IvParameterSpec ivspec = new IvParameterSpec(iv);
 
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      KeySpec spec = new PBEKeySpec(getSECRET_KEY().toCharArray(), SALT.getBytes(), 65536, 256);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
 
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }


    
}





