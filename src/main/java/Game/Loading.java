package Game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
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
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = pathFiles + "\\" + entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathFile) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(pathFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }


}
