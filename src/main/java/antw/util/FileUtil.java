package antw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<File> findFiles(File folder, final String fileName) {
        List<File> list = new ArrayList<File>();
        findFiles(folder, fileName, list);
        return list;
    }

    private static void findFiles(File folder, final String fileName, List<File> list) {
        File[] allFiles = folder.listFiles();
        for (File file : allFiles) {
            if (file.isDirectory()) {
                findFiles(file, fileName, list);
            }
            if (file.getName().equals(fileName)) {
                list.add(file);
            }
        }
    }

    public static void merge(List<File> junitFiles, File out) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(out);

            for (File junitFile : junitFiles) {
                FileInputStream inputStream = new FileInputStream(junitFile);
                byte[] buffer = new byte[1024];
                int read = -1;
                while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    outputStream.write(buffer, 0, read);
                    outputStream.flush();
                }
                inputStream.close();
            }

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
