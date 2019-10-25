package test;

import aknakereso.Difficulty;
import aknakereso.FileOperations;
import org.junit.*;

public class FileOperationsTest {

    FileOperations fo;

    @Before
    public void setUp() throws Exception {
        fo = new FileOperations(Difficulty.HARD);
    }

    @Test
    public void testReadFile() {
        fo.readFile();
        Assert.assertEquals(5, fo.getList().size());
    }

    @Test
    public void testGetList() {
        Assert.assertEquals(0,fo.getList().size());
        fo.readFile();
        Assert.assertEquals(5,fo.getList().size());
    }
}