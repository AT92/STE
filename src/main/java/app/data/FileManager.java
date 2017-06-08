package app.data;


import java.io.*;

public class FileManager {
    private final File file;

    public FileManager(File file) throws FileNotFoundException {
        this.file = file;
    }

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

    public void writeContentToFile(String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(content);
        bw.close();
    }
}
