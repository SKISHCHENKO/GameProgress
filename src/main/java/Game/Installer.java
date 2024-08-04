package Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Installer {

    static String installPath = "D:\\Games";
    static StringBuilder log = new StringBuilder();

    public static void main(String[] args) throws IOException {

        createDir(installPath);
        createDir(installPath + "\\src");
        createDir(installPath + "\\res");
        createDir(installPath + "\\savegames");
        createDir(installPath + "\\temp");

        createDir(installPath + "\\src\\main");
        createDir(installPath + "\\src\\test");

        createFile(installPath + "\\src\\main\\Main.java");
        createFile(installPath + "\\src\\main\\Utils.java");

        createDir(installPath + "\\res\\drawables");
        createDir(installPath + "\\res\\vectors");
        createDir(installPath + "\\res\\icons");

        createFile(installPath + "\\temp\\tmp.txt");

        logToFile(log,installPath + "\\temp\\tmp.txt");
    }

    public static void createDir(String dirPath) {
        File dir = new File(dirPath);
        if (dir.exists()) {
            log("Папка: " + dirPath + " уже существует!");
        } else if (dir.mkdir()) {
            log("Создана папка: " + dirPath);
        } else {
            log("Не удалось создать папку: " + dirPath);
        }
    }

    public static void createFile(String filePath) {
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                log("Создан файл: " + filePath);
            } else {
                log("Не удалось создать файл: " + filePath);
            }
        } catch (IOException e) {
            log("Ошибка при создании файла: " + filePath);
            e.printStackTrace();
        }
    }

    public static void logToFile(StringBuilder msg, String fileName) {
        try (FileWriter logWriter = new FileWriter(fileName, true)) {
            logWriter.write(msg.toString()+"\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи лога: " + e.getMessage());
        }
    }

    public static void log(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("[" + dtf.format(now) + "] <" + msg + ">");
        log.append("[").append(dtf.format(now)).append("] <").append(msg).append(">").append("\n");
    }
}
