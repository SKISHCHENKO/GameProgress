package Game;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class InstallerTests {

    private static final String TEST_INSTALL_PATH = "D:\\GamesTest";
    private static final String TEST_FILE_PATH = TEST_INSTALL_PATH + "\\temp\\tmp.txt";

    @Before
    public void setUp() throws IOException {
        // Удалить все созданные папки и файлы перед каждым тестом
        deleteDirectory(Paths.get(TEST_INSTALL_PATH));
    }

    @After
    public void tearDown() throws IOException {
        // Удалить все созданные папки и файлы после каждого теста
        deleteDirectory(Paths.get(TEST_INSTALL_PATH));
    }

    @Test
    public void testCreateDir()  {
        Installer.createDir(TEST_INSTALL_PATH);
        assertThat(Files.exists(Paths.get(TEST_INSTALL_PATH)), is(true));
    }

    @Test
    public void testCreateFile()  {
        Installer.createDir(TEST_INSTALL_PATH);
        Installer.createDir(TEST_INSTALL_PATH + "\\temp");
        Installer.createFile(TEST_FILE_PATH);
        assertThat(Files.exists(Paths.get(TEST_FILE_PATH)), is(true));
    }

    @Test
    public void testCreateFileWithHamcrest()  {
        Installer.createDir(TEST_INSTALL_PATH);
        Installer.createDir(TEST_INSTALL_PATH + "\\temp");
        Installer.createFile(TEST_FILE_PATH);
        assertThat(Files.exists(Paths.get(TEST_FILE_PATH)), is(true));
    }

    @Test
    public void testLogToFile() throws IOException {
        Installer.createDir(TEST_INSTALL_PATH);
        Installer.createDir(TEST_INSTALL_PATH + "\\temp");
        Installer.createFile(TEST_FILE_PATH);
        String testMessage = "Тестовое сообщение";

        // Логирование непосредственно в файл
        Installer.log(testMessage);

        Path logFilePath = Paths.get(TEST_FILE_PATH);
        assertThat(Files.exists(logFilePath), is(true));

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(testMessage)) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void testLogToFileWithHamcrest() throws IOException {
        Installer.createDir(TEST_INSTALL_PATH);
        Installer.createDir(TEST_INSTALL_PATH + "\\temp");
        Installer.createFile(TEST_FILE_PATH);
        String testMessage = "Тестовое сообщение";

        // Логирование непосредственно в файл
        Installer.log(testMessage);

        Path logFilePath = Paths.get(TEST_FILE_PATH);
        assertThat(Files.exists(logFilePath), is(true));
        assertThat(Files.size(logFilePath), greaterThan(0L));

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(testMessage)) {
                    found = true;
                    break;
                }
            }
            assertThat(found, is(true));
        }
    }

    // Вспомогательный метод для удаления директорий
    private void deleteDirectory(Path dir) throws IOException {
        if (Files.exists(dir)) {
            try (var paths = Files.walk(dir)) {
                paths
                        .sorted(Comparator.reverseOrder()) // Сортировка для удаления файлов перед директориями
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        }
    }
}