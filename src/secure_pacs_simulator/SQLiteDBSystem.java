package secure_pacs_simulator;

/**
 *
 * @author Kesia ISL
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
/**
 *
 * @author sqlitetutorial.net
 */
public class SQLiteDBSystem {
     /**
     * SQLiteDBSystem to a sample database
     */
    private static final String URL = "jdbc:sqlite:pacs.db";
    Connection conn = null;
    Statement stmt = null;
    
    public SQLiteDBSystem(){
        
    }
    
    
    public void connect() {
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(URL);
            
            System.out.println("Connection to SQLite has been established.");            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    

    public void CreateTable(){
        try {           
            conn = DriverManager.getConnection(URL);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE User " +
                           "(ID INTEGER PRIMARY KEY AUTOINCREMENT    NOT NULL," +
                           " USERNAME          TEXT    NOT NULL, " + 
                           " PASSWORD          TEXT    NOT NULL)"; 
            
            String sql2 = "CREATE TABLE  Data" +
                            "(ID INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL," +
                            " IMAGE           BLOB, " + 
                            " LFSR_KEY        TEXT)"; 
            
            String sql3 = "CREATE TABLE  Binarydata" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL," +
                "FID INTEGER  NOT NULL," +
                "FILENAME   TEXT    NOT NULL, " +
                "FILE_SIZE_IN_KB    INTEGER, " +
                "FILE_EXT TEXT, " +
                "FILE_CONTENT BLOB)"; 
            
            String sql4 = "CREATE TABLE  Keydata" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL," +
                "FID INTEGER  NOT NULL," +
                "USERNAME   TEXT, " +
                "LFSR_KEY   TEXT    NOT NULL)"; 
            
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.executeUpdate(sql4);
            stmt.close();
            conn.close();
            System.out.println("Tables created successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     *
     * @param Usname
     * @param Pwd
     * @param Sec_key
     * @param Pub_key
     */
    public void InsertUser(String Usname, String Pwd ){
        try{
            conn = DriverManager.getConnection(URL);
//            stmt = conn.createStatement();
//            String sql = "INSERT INTO User (ID, USERNAME, PASSWORD, SECRET_KEY, PUBLIC_KEY)" +
//                    "VALUES (NULL, 'usname', 'Pwd', 'Sec_key', 'Pub_key');";  
            //PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY);
            
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM 'User' WHERE USERNAME= :username");
//            pstmt.setString(1, NULL );
            pstmt.setString(1, Usname);
            ResultSet rs = pstmt.executeQuery();
//            boolean st = false;
//            st = rs.next();
            if(!rs.next()){
                pstmt = conn.prepareStatement("INSERT INTO 'User' ( USERNAME, PASSWORD) VALUES (?,?)");
  //            pstmt.setString(1, NULL );
                pstmt.setString(1, Usname);
                pstmt.setString(2, Pwd);
//                pstmt.setString(3, Sec_key);
//                pstmt.setString(4,Pub_key );
                pstmt.executeUpdate();    
            }          
            
//            stmt.executeUpdate(sql);
            pstmt.close();
            conn.close();
            System.out.println("User inserted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    
    }
    
    /**
     *
     * @param image
     * @param lfsr_key
     */
    public void InsertData(String image, String lfsr_key){
        try{
            conn = DriverManager.getConnection(URL);
            stmt = conn.createStatement();
//            String sql = "INSERT INTO Data (ID, IMAGE, LFSR_KEY)" +
//                    "VALUES (NULL, image, lfsr_key);";          
                
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO 'Data' ( IMAGE, LFSR_KEY) VALUES (?,?)");
//            pstmt.setString(1, NULL );
            pstmt.setString(1, image);
            pstmt.setString(2, lfsr_key);
            pstmt.executeUpdate();
//            stmt.executeUpdate(sql);
            pstmt.close();
            conn.close();                     
            System.out.println("Data inserted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    
    }
    
    /**
     *
     * @param fid
     * @param username
     * @param lfsr_key
     */
    public void InsertKeyDataDb(int fid, String username, String lfsr_key){
        try{
            conn = DriverManager.getConnection(URL);
            stmt = conn.createStatement();
//            String sql = "INSERT INTO Data (ID, IMAGE, LFSR_KEY)" +
//                    "VALUES (NULL, image, lfsr_key);";          
                
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO 'Keydata' (FID, USERNAME, LFSR_KEY) VALUES (?, ?, ?)");
            pstmt.setInt(1, fid);
            pstmt.setString(2, username);
            pstmt.setString(3, lfsr_key);
            pstmt.executeUpdate();
//            stmt.executeUpdate(sql);
            pstmt.close();
            conn.close();                     
            System.out.println("Key data inserted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    
    }
    
    
    /**
     *
     * @param binary_file
     * @param image
     * @param lfsr_key
     * @throws java.io.FileNotFoundException
     */
    public void InsertBinaryData(String binary_file, int fid) throws FileNotFoundException{
        
//	Path dir = Paths.get("InputFiles");
        try{
            conn = DriverManager.getConnection(URL);
//            stmt = conn.createStatement();
//            String SQL="INSERT INTO BinaryData (file_name, file_size_in_kb, file_extension, file_content)VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement("INSERT INTO 'Binarydata' (FID, FILENAME, FILE_SIZE_IN_KB, FILE_EXT, FILE_CONTENT) VALUES (?,?,?,?,?)");

            Path path = Paths.get(binary_file);
            System.out.println(path.getFileName());
            File file = path.toFile();
            String fileName = file.getName();
            Long fileLength = file.length();
            Long fileLengthInKb = fileLength/1024;
            int file_l = fileLength.intValue();
            
            System.out.printf("%s \t %s \n", "Filename: ", fileName);
            System.out.printf("%s \t %s \n", "File Length: ", fileLength);
            System.out.printf("%s \t %s \n", "File Length In Kb: ", fileLengthInKb);
            System.out.printf("%s \t %s \n", "File Length In Int: ", file_l);

            ps.setInt(1, fid);
            ps.setString(2, fileName);
            ps.setLong(3, fileLengthInKb);
//            ps.setString(3, "enc");
            ps.setString(4, fileName.substring(fileName.lastIndexOf(".")+1));	
            FileInputStream fis = new FileInputStream(file);   
            ps.setBinaryStream(5, fis, file_l);
            ps.executeUpdate();
            System.out.println("Binary Data inserted successfully!");
            
            ps.close();
            conn.close();                     
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    
    }
    
    
    public ArrayList getBinaryFileNamesFromDatabase() throws SQLException, IOException {
               String URL = "jdbc:sqlite:pacs.db";
               Connection conn = null;
               ArrayList<String> datainfo = new ArrayList<String>();
               conn = DriverManager.getConnection(URL);
               PreparedStatement ps = conn.prepareStatement("SELECT * FROM Binarydata");
//               ps.setInt(1, 1);
               ResultSet rs = ps.executeQuery();

               while (rs.next()) {
                   
                   int id = rs.getInt("ID");
                   int fileId = rs.getInt("FID");
                   String fileName = rs.getString("FILENAME");
                   long fileSizeInKb = rs.getLong("FILE_SIZE_IN_KB");
//                   String fileExtension = rs.getString("FILE_EXT");
                   
                   datainfo.add(id +" | "+ fileId +" | "+ fileName +" | "+ fileSizeInKb);
//                   datainfo.add(""+fileSizeInKb);
//                   datainfo.add(fileName);
//                   System.out.println("File Id:"+fileId);;
//                   System.out.println("File Name:"+fileName);
//                   System.out.println("File Size In KB:"+fileSizeInKb);
//                   System.out.println("File Extension:"+fileExtension);
                   
//                   InputStream blob = rs.getBinaryStream("FILE_CONTENT");
//                   InputStream inputStream = blob.getBinaryStream();
                   
                   System.out.println("-----------------------------------");
//                   System.out.println("File Content: "+blob);
//                   Files.copy(blob, Paths.get("download"+fileName));
               }
               
               return datainfo;               
	}
    
    
        public String SelectBinarydata(int fid) throws SQLException, IOException {
               String URL = "jdbc:sqlite:pacs.db";
               Connection conn = null;
//               ArrayList<String> datainfo = new ArrayList<String>();
               conn = DriverManager.getConnection(URL);
               PreparedStatement ps = conn.prepareStatement("SELECT * FROM Binarydata WHERE FID = :fidd");
               ps.setInt(1, fid);
               ResultSet rs = ps.executeQuery();
               String NewfileName = null;
               while (rs.next()) {
                   
                   int id = rs.getInt("ID");
                   int fileId = rs.getInt("FID");
                   String fileName = rs.getString("FILENAME");
                   long fileSizeInKb = rs.getLong("FILE_SIZE_IN_KB");
                   String fileExtension = rs.getString("FILE_EXT");
                   
//                   datainfo.add(id +" | "+ fileId +" | "+ fileName +" | "+ fileSizeInKb);
//                   datainfo.add(""+fileSizeInKb);
//                   datainfo.add(fileName);
//                   System.out.println("File Id:"+fileId);;
//                   System.out.println("File Name:"+fileName);
//                   System.out.println("File Size In KB:"+fileSizeInKb);
//                   System.out.println("File Extension:"+fileExtension);
                   NewfileName = fileName;
                   InputStream blob = rs.getBinaryStream("FILE_CONTENT");
//                   InputStream inputStream = blob.getBinaryStream();
                   
                   System.out.println("-----------------------------------");
//                   System.out.println("File Content: "+blob);
                    File f = new File(fileName); 
                    if(!f.exists()) {
                        Files.copy(blob, Paths.get(fileName));
                    }
                   
               }
               
               return NewfileName;               
	}
    
    
     /**
     *
     * @return 
     */
    public String[] SelectAllUser(){
        String user[] = new String[4];
        try{
            conn = DriverManager.getConnection(URL);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM User;"; 
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                user[0] = rs.getString(1);
                user[1] = rs.getString(2);
//                lst[2] = rs.getString(3);
                user[2] = rs.getString(4);
                user[3] = rs.getString(5);
            }
            
            stmt.close();
            conn.close();
            stmt.executeUpdate(sql);
            System.out.println("User selected successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    return user;
    }
    
     /**
     *
     * @param Usname
     * @return 
     */
    public String[] SelectUser(String Usname){
        String user[] = new String[4];
        try{
            conn = DriverManager.getConnection(URL);
//            stmt = conn.createStatement();  
                
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM 'User' WHERE USERNAME= :username");
//            pstmt.setString(1, NULL );
            pstmt.setString(1, Usname);
            ResultSet rs = pstmt.executeQuery();
//            boolean st = false;
//            st = rs.next();
            if(rs.next()){
                user[0] = rs.getString("USERNAME");
                user[1] = rs.getString("PASSWORD");
                user[2] = rs.getString("SECRET_KEY");
                user[3] = rs.getString("PUBLIC_KEY");
            }
         
            pstmt.executeUpdate();
            conn.close();
            
            System.out.println("User selected successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    return user;
    }
    
     /**
     *
     * @return 
     */
    public String[] SelectAllData(){
        String data[] = new String[5];
        try{
            conn = DriverManager.getConnection(URL);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Data;"; 
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
            }
            
            stmt.close();
            conn.close();
            stmt.executeUpdate(sql);
            System.out.println("Data selected successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    return data;
    }
    
    
    /**
     *
     * @param data_id
     * @return 
     */

    public String SelectData(int data_id){
        String data = "";
        try{
            conn = DriverManager.getConnection(URL);
//            stmt = conn.createStatement();  
                
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM 'Data' WHERE ID= :dataid");
//            pstmt.setString(1, NULL );
            pstmt.setInt(1, data_id);
            ResultSet rs = pstmt.executeQuery();
//            boolean st = false;
//            st = rs.next();
            if(rs.next()){
                data = rs.getString("IMAGE");
            }
         
            pstmt.executeUpdate();
            conn.close();
            
            System.out.println("User selected successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    return data;
    }
    
    
 
            
    /**
     *
     * @param fid
     * @param username
     * @return 
     */

    public byte[] SelectLFSRBinary(int fid, String username){
        byte[] LFSR = null;
        try{
            conn = DriverManager.getConnection(URL);
//            stmt = conn.createStatement();  
                
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM 'Keydata' WHERE FID= :fileid AND USERNAME = :user");
            pstmt.setInt(1, fid);
            pstmt.setString(2, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                
                LFSR = rs.getBytes("LFSR_KEY");
//                System.out.println(rs.getInt("FID"));
//                System.out.println("Heeeeeeeeeyyyyyyyyyyyyyyyy");
            }
         
            pstmt.executeUpdate();
            conn.close();
            
            System.out.println("User selected successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    return LFSR;
    }
    
    public String SelectEncryptedLFSRKey(int fid, String username){
        String LFSR = null;
        try{
            conn = DriverManager.getConnection(URL);
//            stmt = conn.createStatement();  
                
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM 'Keydata' WHERE FID= :fileid AND USERNAME = :user");
            pstmt.setInt(1, fid);
            pstmt.setString(2, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                
                LFSR = rs.getString("LFSR_KEY");
//                System.out.println(rs.getInt("FID"));
//                System.out.println("Heeeeeeeeeyyyyyyyyyyyyyyyy");
            }
         
            pstmt.executeUpdate();
            conn.close();
            
            System.out.println("User selected successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    return LFSR;
    }
                
    
    /**
     *
     * @param key_id
     * @return 
     */
    public String SelectLFSRKey(int key_id){
        String lfsr_key = "";
        try{
            conn = DriverManager.getConnection(URL);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Data WHERE ID= "
                    + key_id +";"; 
            ResultSet rs = stmt.executeQuery(sql);
            
//            while(rs.next()){
                lfsr_key = rs.getString(3);

//            }
            stmt.close();
            conn.close();
            stmt.executeUpdate(sql);
            System.out.println("Data selected successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
    return lfsr_key;
    }
    
    
}