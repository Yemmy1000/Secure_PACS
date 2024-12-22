
package secure_pacs_simulator;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class getFile {
    
    private static final String DEFAULT_ENCODING = "UTF-8";
    
    
      public static String getFileContent(String path) throws IOException {
        return getFileContent(path, DEFAULT_ENCODING);
    }

    /**
     * Reads the file content at the given path, and returns its content in
     * String format using the given encoding.
     *
     * @param path The path to the file
     * @param encoding encoding of the file
     * @return content of the file at the given path
     * @throws IOException if an error occurs while reading the file
     * @throws IllegalArgumentException if the file does not exists at the given
     * path
     */
    public static String getFileContent(String path, String encoding) throws IOException {
        File file = new File(path);
        if (!file.exists() || !file.canRead()) {
            throw new IllegalArgumentException(
                    "The supplied file: " + path + " either does not exists, or is not readable");
        }

        return readStream(new FileInputStream(file), encoding);
    }

    /**
     * Reads the InputStream and tries to interpret its content as String using
     * UTF-8 encoding
     * @param is the inputstream to read
     * @return The string representation of the inputstreams content
     * @throws IOException if there was an error while reading the stream
     */
    public static String readStream(InputStream is) throws IOException {
        return readStream(is, DEFAULT_ENCODING);
    }

    /**
     * Reads the InputStream and tries to interpret its content as String using
     * the given encoding
     * @param is the inputstream to read
     * @param encoding the encoding to be used
     * @return The string representation of the inputstreams content
     * @throws IOException if there was an error while reading the stream
     */
    public static String readStream(InputStream is, String encoding) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, encoding));
            while ((line = br.readLine()) != null) {
                content.append(line).append('\n');
            }
        } finally {
            closeIfNotNull(br);
        }
        return content.toString();
    }

    /**
     * Closes the passed in resource if not null.
     *
     * @param closeable The resource that needs to be closed.
     */
    public static void closeIfNotNull(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
    
//   public static byte[] loadBinaryFile(Path filePath) throws IOException{
       


       
       
       
       
//   }
   
    
}
