package Game;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class InstallerTests {

    private static final String TEST_INSTALL_PATH = "D:\\GamesTest";
    private static final String TEST_FILE_PATH = TEST_INSTALL_PATH + "\\temp\\tmp.txt";

    @Before
    public void setUp() {
        // Удалить все созданные папки и файлы перед каждым тестом
        deleteDirectory(new File(TEST_INSTALL_PATH));
    }

    @After
    public void tearDown() {
        // Удалить все созданные папки и файлы после каждого теста
        deleteDirectory(new File(TEST_INSTALL_PATH));
    }

    @Test
    public void testCreateDir() {
        Installer.createDir(TEST_INSTALL_PATH);
        Assert.assertTrue(new File(TEST_INSTALL_PATH).exists());
    }

    @Test
    public void testCreateFile() {
        Installer.createDir(TEST_INSTALL_PATH);
        Installer.createDir(TEST_INSTALL_PATH + "\\temp");
        Installer.createFile(TEST_FILE_PATH);
        Assert.assertTrue(new File(TEST_FILE_PATH).exists());
    }
    @Test
    public void testCreateFileWithHamcrest() {
        Installer.createDir(TEST_INSTALL_PATH);
        Installer.createDir(TEST_INSTALL_PATH + "\\temp");
        Installer.createFile(TEST_FILE_PATH);
        assertThat(new File(TEST_FILE_PATH).exists(),  is(true));
    }

    @Test
    public void testLogToFile() throws IOException {
        Installer.createDir(TEST_INSTALL_PATH);
        Installer.createDir(TEST_INSTALL_PATH + "\\temp");
        Installer.createFile(TEST_FILE_PATH);
        String testMessage = "Тестовое сообщение";
        Installer.log(testMessage);
        Installer.logToFile(Installer.log, TEST_FILE_PATH);

        File file = new File(TEST_FILE_PATH);
        if (file.exists() && file.length() > 0) {
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
        }else {
            Assert.fail("Файл не существует или пустой");
        }
    }

    @Test
    public void testLogToFileWithHamcrest() throws IOException {
        Installer.createDir(TEST_INSTALL_PATH);
        Installer.createDir(TEST_INSTALL_PATH + "\\temp");
        Installer.createFile(TEST_FILE_PATH);
        String testMessage = "Тестовое сообщение";
        Installer.log(testMessage);
        Installer.logToFile(Installer.log, TEST_FILE_PATH);

        File file = new File(TEST_FILE_PATH);
        assertThat(file.exists(), is(true));
        assertThat(file.length(), greaterThan(0L));

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine())!= null) {
                if (line.contains(testMessage)) {
                    found = true;
                    break;
                }
            }
            assertThat(found, is(true));
        }
    }

    @Test
    public void testLog() {
        String testMessage = "Тестовое сообщение";
        Installer.log(testMessage);
        Assert.assertTrue(Installer.log.toString().contains(testMessage));
    }
    @Test
    public void testLogWithHamcrest() {
        String testMessage = "Тестовое сообщение";
        Installer.log(testMessage);
        assertThat(Installer.log.toString(), containsString(testMessage));
    }


    private void deleteDirectory(File dir) {
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
            dir.delete();
        }
    }
}
