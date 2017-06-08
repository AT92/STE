package app;

import app.controller.EditorController;
import app.data.FileManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ContentManangerTest {
    @Mock
    EditorController ctrl = new EditorController();

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetContentOutOfFile() throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        File testFile = new File(cl.getResource("TestFile").getFile());
        FileManager cm = new FileManager(testFile);
        assertEquals("Simple Text with\nlinebreaks", cm.getContentOutOfFile());
    }

    @Test
    public void testWriteContentToFile() throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        File testFile = new File(cl.getResource("TestFile").getFile());
        FileManager cm = new FileManager(testFile);
        String content = cm.getContentOutOfFile();
        cm.writeContentToFile(content);
        assertEquals(content, cm.getContentOutOfFile());
    }


}