package Game;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Loading {
    static String unZipPath = "D:\\Games\\savegames";

    public static void main(String[] args) {
        String filePath1 = unZipPath + "\\save1.dat";
        String filePath2 = unZipPath + "\\save2.dat";
        String filePath3 = unZipPath + "\\save3.dat";

        openZip(unZipPath + "\\save.zip", unZipPath);

        GameProgress game1 = openProgress(filePath1);
        GameProgress game2 = openProgress(filePath2);
        GameProgress game3 = openProgress(filePath3);

        System.out.println(game1);
        System.out.println(game2);
        System.out.println(game3);
    }

    public static void openZip(String pathZip, String pathFiles) {
        try (ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(pathZip)))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                String fileName = pathFiles + "\\" + entry.getName();
                try (FileOutputStream fout = new FileOutputStream(fileName);
                     BufferedOutputStream bos = new BufferedOutputStream(fout)) {

                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zin.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                    }

                    zin.closeEntry();
                } catch (IOException ex) {
                    System.err.println("Ошибка при извлечении файла: " + fileName + " - " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.err.println("Ошибка при работе с архивом: " + ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathFile) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(pathFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Ошибка при загрузке прогресса игры: " + ex.getMessage());
        }
        return gameProgress;
    }
}