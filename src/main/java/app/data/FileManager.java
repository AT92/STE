package app.data;


import java.io.*;

/**
 * This class is a file manager.
 * It writes to files or get the content out of them.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class FileManager {
    /**
     * The file, which should be read or written.
     */
    private final File file;

    /**
     * This is the constructor for this class.
     * @param file, the file, which should be read or written.
     */
    public FileManager(File file) {
        this.file = file;
    }

    /**
     * This method reads content out of a file.
     * @return the content as a string out of file.
     * @throws IOException, is thrown when there is an error with reading the file.
     */
    public String getContentOutOfFile() throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
            if (line != null) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * This method writes content to the file.
     * @param content, the content, which should be written.
     * @throws IOException, is thrown when the file con not be written.
     */
    public void writeContentToFile(String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(content);
        bw.close();
    }
}
