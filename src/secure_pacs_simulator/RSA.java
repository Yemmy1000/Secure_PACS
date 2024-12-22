/**
 *
 * @author Kesia ISL
 */

package secure_pacs_simulator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSA {
    
//    static String plainText = "Plain text which need to be encrypted by Java RSA Encryption in ECB Mode";
    // Get the public and private key
    private PublicKey publicKey ;
    private PrivateKey privateKey ;   
    
    public String encryptedText;
    public String decryptedText;
    private byte[] cipherTextArr;
    
    public RSA() throws Exception
    {
        // Get an instance of the RSA key generator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        
        // Generate the KeyPair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        setPublicKey(keyPair.getPublic());
        setPrivateKey(keyPair.getPrivate());
        
        //        System.out.println("Original Text  : "+plainText);
        
        // Encryption
//        encryptedText = encrypt(plainText, publicKey);
        //        byte[] cipherTextArray = encrypt(plainText, publicKey);
        //        String encryptedText = Base64.getEncoder().encodeToString(cipherTextArray);
        //        System.out.println("Encrypted Text : "+encryptedText);
        
        // Decryption
        //        decryptedText = decrypt(cipherTextArray, privateKey);
        //        System.out.println("DeCrypted Text : "+decryptedText);
    }
    
    public RSA(byte[] cipherTextArray) throws Exception
    {
        setCipherTextArr(cipherTextArray);
    }

    public byte[] getCipherTextArr() {
        return cipherTextArr;
    }

    public void setCipherTextArr(byte[] cipherTextArr) {
        this.cipherTextArr = cipherTextArr;
    }

    
    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
    
    public void saveKeysToFile(String public_fileName, String private_fileName) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException{
                // Get the RSAPublicKeySpec and RSAPrivateKeySpec
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(getPublicKey(), RSAPublicKeySpec.class);
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(getPrivateKey(), RSAPrivateKeySpec.class);
        
        // Saving the Key to the file
        String priv_filename = "private_"+private_fileName+".key";
        String pub_filename = "public_"+public_fileName+".key";
        saveKeyToFile(pub_filename, publicKeySpec.getModulus(), publicKeySpec.getPublicExponent());
        saveKeyToFile(priv_filename, privateKeySpec.getModulus(), privateKeySpec.getPrivateExponent());
    }
    
    public static void saveKeyToFile(String fileName, BigInteger modulus, BigInteger exponent) throws IOException
    {
        ObjectOutputStream ObjOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(fileName)));
        try
        {
            ObjOutputStream.writeObject(modulus);
            ObjOutputStream.writeObject(exponent);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            ObjOutputStream.close();
        }
    }
    
   
    public static Key readKeyFromFile(String keyFileName) throws IOException
    {
        Key key = null;
        InputStream inputStream = new FileInputStream(keyFileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(inputStream));
        try
        {
            BigInteger modulus = (BigInteger) objectInputStream.readObject();
            BigInteger exponent = (BigInteger) objectInputStream.readObject();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            if (keyFileName.startsWith("public"))
                key = keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent));
            else
                key = keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus, exponent));

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            objectInputStream.close();
        }
        return key;
    }


    public static String encrypt(String plainText, String pub_fileName) throws Exception
    {
        Key publicKey = readKeyFromFile(pub_fileName);

        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return new String(Base64.getEncoder().encode(cipherText));
    }
    
    public String decrypt(String priv_fileName) throws Exception
    {
        Key privateKey = readKeyFromFile(priv_fileName);

        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(Base64.getDecoder().decode(getCipherTextArr()));

        return new String(decryptedTextArray);
    }
    
    //Not used
    public String encrypt_ (String plainText, String publicKey ) throws Exception
    {
        PublicKey publicKeey = loadPublicKey(publicKey);
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
        
        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKeey);
        
        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes()) ;
        
//        byte[] cipherTextArray = encrypt(plainText, publicKey);
        String encryptedText = Base64.getEncoder().encodeToString(cipherText);
//        System.out.println("Encrypted Text : "+encryptedText);

        return encryptedText;
    }
    
    public String decrypt (PrivateKey privateKey) throws Exception
    {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
        
        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(getCipherTextArr());
        
        return new String(decryptedTextArray);
    }   
    
    //Not working 
    public static PublicKey loadPublicKey(String pubKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] data = Base64.getMimeDecoder().decode(pubKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        
        return publicKey;
    }
    
    
}
