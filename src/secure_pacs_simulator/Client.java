
package secure_pacs_simulator;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.PublicKey;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kesia ISL
 */



public class Client {
    
    private static String LFSR_key = "";
    
    private static byte[] encryted_LFSR_key = null;
    
    private static String Dicom_data = null;
    
    public static void main(String[] args){
        System.out.println("Welcome here ");
        String[] myinfo = login();
        
//        System.out.printf("%s \t %s \n", "Username: ", myinfo[0]);
//        System.out.printf("%s \t %s \n", "Password: ", myinfo[1]);
//        System.out.printf("%s \t %s \n", "Secret key: ", myinfo[2]);
//        System.out.printf("%s \t %s \n", "Public key: ", myinfo[3]);
        Long start = System.currentTimeMillis();
        try {

            
//            encryted_LFSR_key = getLFSR_key_and_encrypt(1, myinfo[0]);
                       
//            LFSR_key = getDecryptedLFSRKey(getEncryted_LFSR_key(), myinfo[0]);
            
//            Dicom_data = decrypt_dicom_file(1);
            
//            saveDicomFile("dicom1.DCM", getDicom_data());
            downloadBinaryFilesFromDatabase();
            
            DicomViewScrollAndWindowWidthAndLeveling dicom_view = new DicomViewScrollAndWindowWidthAndLeveling();
            DicomViewScrollAndWindowWidthAndLeveling.showImage("plainfile_decrypted.DCM");
            
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
       Long stop = System.currentTimeMillis() - start; 
       
       System.out.printf("%s \t %s \n", "LFSR key Encryption Time: ", stop);
       
       System.out.printf("%s \t %s \n", "LFSR key Encryption: ", getEncryted_LFSR_key());
       
       System.out.printf("%s \t %s \n", "LFSR key Decrypted: ", getLFSR_key());
       
       System.out.printf("%s \t %s \n", "Dicom Data: ", getDicom_data());
       
    }

    public static byte[] getEncryted_LFSR_key() {
        return encryted_LFSR_key;
    }
 
    public static String getLFSR_key() {
        return LFSR_key;
    }

    public static String getDicom_data() {
        return Dicom_data;
    }

        
    public static String[] login(){
        SQLiteDBSystem db = new SQLiteDBSystem();
        db.connect();
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter username: ");
        String myUsername = input.nextLine();
//        System.out.println(myUsername);
        String[] myinfo = db.SelectUser(myUsername);
        return myinfo;
    }
    
    public static byte[] getLFSR_key_and_encrypt(int img_id, String username) throws Exception{
        
        LFSRKeyEncryDecrypt LFSRKeyEncry = new LFSRKeyEncryDecrypt();
        byte[] cipher_lfsr_key = LFSRKeyEncry.getEncryptedLFSRKey(img_id, username);
        
        System.out.printf("%s \t %s \n", "Encrypted LFSR key: ", cipher_lfsr_key);
        
        return cipher_lfsr_key;
        
    }
    
    //Used in the View Dicom FIle Button
    public static String getDecryptedLFSRKey(byte[] cipher, String username) throws Exception{
        
        RSA rsa_d = new RSA(cipher);
        String pri_key_filename = "private_"+username+".key";
        String decrypted_key = rsa_d.decrypt(pri_key_filename);
        
//        System.out.printf("%s \t %s \n", "Encrypted LFSR key: ", cipher_lfsr_key);
        return decrypted_key;
    }
    
    
    public static String decrypt_dicom_file(int img_id){
        
       SQLiteDBSystem db = new SQLiteDBSystem();
       db.connect();
       String data = db.SelectData(img_id);
       
       AES256KnowledgeFactory EAS = new AES256KnowledgeFactory();
       EAS.setSECRET_KEY(getLFSR_key());      
        
       String decrypted_dicom = AES256KnowledgeFactory.decrypt(data);
//             System.out.printf("%s \t %s \n", "Decrypted DICOM string: ", decrypted_dicom);
        return decrypted_dicom;
    }
    

    
     public static void saveDicomFile_old(String fileName, String Dicom_data) throws IOException
    {
        String fileName_ext = fileName+".DCM";
        ObjectOutputStream ObjOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(fileName_ext)));
        try
        {
            ObjOutputStream.writeObject(Dicom_data);
//            ObjOutputStream.writeObject(exponent);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            ObjOutputStream.close();
        }
    }   
     
      public static void saveDicomFile(String fileName, String Dicom_data) throws IOException
      {
        File file = new File(fileName);
        byte[] data = Dicom_data.getBytes(StandardCharsets.UTF_8);
 
        try (FileOutputStream fos = new FileOutputStream(file))
        {
            fos.write(data);
            System.out.println("Successfully written data to the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
      
      
    	public static void downloadBinaryFilesFromDatabase() throws SQLException, IOException {
               String URL = "jdbc:sqlite:pacs.db";
               Connection conn = null;

               conn = DriverManager.getConnection(URL);
               PreparedStatement ps = conn.prepareStatement("SELECT * FROM Binarydata WHERE ID = :id");
               ps.setInt(1, 1);
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                   int fileId = rs.getInt("ID");
                   String fileName = rs.getString("FILENAME");
                   long fileSizeInKb = rs.getLong("FILE_SIZE_IN_KB");
                   String fileExtension = rs.getString("FILE_EXT");
                   System.out.println("File Id:"+fileId);
                   System.out.println("File Name:"+fileName);
                   System.out.println("File Size In KB:"+fileSizeInKb);
                   System.out.println("File Extension:"+fileExtension);
                   
                   InputStream blob = rs.getBinaryStream("FILE_CONTENT");
//                   InputStream inputStream = blob.getBinaryStream();
                   
                   System.out.println("-----------------------------------");
                   System.out.println("File Content: "+blob);
                   Files.copy(blob, Paths.get("download"+fileName));
               }
	}
    
    
    
    
}





