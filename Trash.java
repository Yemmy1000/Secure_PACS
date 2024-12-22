package com.infotech.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.infotech.util.DBUtil;
/**
 * @author KK JavaTutorials
 *JDBC program to write or save binary data/BLOB data in database
 */
public class SaveBinaryFilesInDBClientTest {
	
	public static void main(String[] args) throws SQLException   {
		saveBinaryFilesInDatabase();
	}
	private static void saveBinaryFilesInDatabase() throws SQLException {
		String SQL="INSERT INTO storebinaryfile_table (file_name,file_size_in_kb,file_extension,file_content)VALUES(?,?,?,?)";
		Path dir = Paths.get("InputFiles");
		try(Stream<Path> list = Files.list(dir);Connection connection = DBUtil.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			/*
			 *Below line belongs to JDK 1.8 API so make sure you are running this code on JDK 1.8.
			 *And you IDE pointing to compiler version 1.8
			 */
			List<Path> pathList = list.collect(Collectors.toList());
			System.out.println("Following files are saved in database..");
			for (Path path : pathList) {
				System.out.println(path.getFileName());
				File file = path.toFile();
				String fileName = file.getName();
				long fileLength = file.length();
				long fileLengthInKb=fileLength/1024;
				
				ps.setString(1, fileName);
				ps.setLong(2, fileLengthInKb);
				
				ps.setString(3, fileName.substring(fileName.lastIndexOf(".")+1));
				
				FileInputStream fis = new FileInputStream(file);
				ps.setBinaryStream(4, fis, fileLength);
				
				ps.addBatch();
			}
			System.out.println("----------------------------------------");
			int[] executeBatch = ps.executeBatch();
			for (int i : executeBatch) {
				System.out.println(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}








package com.infotech.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.infotech.util.DBUtil;
/**
 * @author KK JavaTutorials
 *JDBC program to read binary data/BLOB data from database and write into local disk.
 */
public class DownloadBinaryFilesFromDBClientTest {
	
	public static void main(String[] args) throws SQLException   {
		downloadBinaryFilesFromDatabase();
	}
	
	private static void downloadBinaryFilesFromDatabase() throws SQLException {

		String SQL="SELECT *FROM storebinaryfile_table";
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL);ResultSet 
				rs = ps.executeQuery()) {
			System.out.println("Following flies are downloaded from database..");
			while (rs.next()) {
				int fileId = rs.getInt("file_id");
				String fileName = rs.getString("file_name");
				long fileSizeInKb = rs.getLong("file_size_in_kb");
				String fileExtension = rs.getString("file_extension");
				System.out.println("File Id:"+fileId);
				System.out.println("File Name:"+fileName);
				System.out.println("File Size In KB:"+fileSizeInKb);
				System.out.println("File Extension:"+fileExtension);
				
				Blob blob = rs.getBlob("file_content");
				InputStream inputStream = blob.getBinaryStream();
				
				System.out.println("-----------------------------------");
				Files.copy(inputStream, Paths.get("DownLoadFiles/"+fileName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}






	private static void downloadBinaryFilesFromDatabase() throws SQLException {
 
		String SQL="SELECT *FROM storebinaryfile_table";
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL);ResultSet 
				rs = ps.executeQuery()) {
			System.out.println("Following flies are downloaded from database..");
			while (rs.next()) {
				int fileId = rs.getInt("file_id");
				String fileName = rs.getString("file_name");
				long fileSizeInKb = rs.getLong("file_size_in_kb");
				String fileExtension = rs.getString("file_extension");
				System.out.println("File Id:"+fileId);
				System.out.println("File Name:"+fileName);
				System.out.println("File Size In KB:"+fileSizeInKb);
				System.out.println("File Extension:"+fileExtension);
				
				Blob blob = rs.getBlob("file_content");
				InputStream inputStream = blob.getBinaryStream();
				
				System.out.println("-----------------------------------");
				Files.copy(inputStream, Paths.get("DownLoadFiles/"+fileName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
FileInputStream fis = new FileInputStream(fileName);
byte[] buffer = new byte[10];
StringBuilder sb = new StringBuilder();
while (fis.read(buffer) != -1) {
	sb.append(new String(buffer));
	buffer = new byte[10];
}
fis.close();

String content = sb.toString();