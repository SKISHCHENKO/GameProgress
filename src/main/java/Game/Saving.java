package Game;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Saving {

    static String savingPath = "D:\\Games\\savegames";

    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(100, 10, 10, 20.0);
        GameProgress game2 = new GameProgress(50, 5, 15, 10.0);
        GameProgress game3 = new GameProgress(70, 15, 20, 15.0);

        String filePath1 = savingPath + "\\save1.dat";
        String filePath2 = savingPath + "\\save2.dat";
        String filePath3 = savingPath + "\\save3.dat";

        saveGame(filePath1, game1);
        saveGame(filePath2, game2);
        saveGame(filePath3, game3);

        List<String> pathFiles = Arrays.asList(filePath1, filePath2, filePath3);

        zipFiles(savingPath + "\\save.zip", pathFiles);

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathZip, List<String> pathFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip))) {
            for (String pathFile : pathFiles) {
                try (FileInputStream fis = new FileInputStream(pathFile)) {
                    String fileName = new File(pathFile).getName();
                    ZipEntry entry = new ZipEntry(fileName);
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
            for (String pathFile : pathFiles) {
                new File(pathFile).delete();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}