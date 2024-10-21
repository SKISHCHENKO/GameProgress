package Game;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Installer {

    static final String installPath = "D:\\Games";
    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) {
        try {
            installStructure();
        } catch (IOException e) {
            System.err.println("Ошибка установки: " + e.getMessage());
        }
    }

    public static void installStructure() throws IOException {
        createDir(installPath);
        createDirectories();
        createFiles();
    }

    private static void createDirectories() {
        createDir(installPath + "\\src");
        createDir(installPath + "\\res");
        createDir(installPath + "\\savegames");
        createDir(installPath + "\\temp");

        createDir(installPath + "\\src\\main");
        createDir(installPath + "\\src\\test");

        createDir(installPath + "\\res\\drawables");
        createDir(installPath + "\\res\\vectors");
        createDir(installPath + "\\res\\icons");
    }

    private static void createFiles() {
        createFile(installPath + "\\src\\main\\Main.java");
        createFile(installPath + "\\src\\main\\Utils.java");
        createFile(installPath + "\\temp\\tmp.txt");
    }

    public static void createDir(String dirPath) {
        Path path = Paths.get(dirPath);
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path);
                log("Создана папка: " + dirPath);
            } else {
                log("Папка: " + dirPath + " уже существует!");
            }
        } catch (IOException e) {
            log("Не удалось создать папку: " + dirPath + " - " + e.getMessage());
        }
    }

    public static void createFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
                log("Создан файл: " + filePath);
            } else {
                log("Файл: " + filePath + " уже существует!");
            }
        } catch (IOException e) {
            log("Ошибка при создании файла: " + filePath + " - " + e.getMessage());
        }
    }

    public static void log(String msg) {
        try (FileWriter logWriter = new FileWriter(installPath + "\\temp\\installer.log", true)) {
            String logMsg = "[" + DATE_FORMAT.format(LocalDateTime.now()) + "] <" + msg + ">\n";
            logWriter.write(logMsg);
            System.out.println(logMsg);
        } catch (IOException e) {
            System.err.println("Ошибка при записи лога: " + e.getMessage());
        }
    }
}