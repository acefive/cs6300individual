package edu.gatech.seclass.filesummary;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/*
DO NOT ALTER THIS CLASS.  Use it as an example for MyMainTest.java
 */

public class MainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
     *  TEST UTILITIES
     */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file =  createTmpFile();

        OutputStreamWriter fileWriter =
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);

        fileWriter.close();
        return file;
    }


    //Read File Utility
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /*
     * TEST FILE CONTENT
     */
    private static final String FILE1 = "1 dog" + System.lineSeparator() + "2 cat";
    private static final String FILE2 = "Log: 123 abc\nError: 123 xyz\nError: 567 abc\nLog: 567 abc";
    private static final String FILE3 = "Up with the white and gold\rDown with the red and black";


    // test cases

    /*
     *   TEST CASES
     */

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 1 from assignment directions
    @Test
    public void mainTest1() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {inputFile1.getPath()};
        Main.main(args);

        String expected1 = FILE1;

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("4", outStream.toString().trim());
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 2 from assignment directions
    @Test
    public void mainTest2() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {"-a", "2", "-s", "d1atc", inputFile1.getPath()};
        Main.main(args);

        String expected2 = "2 cat" + System.lineSeparator() + "1 dog";

        String actual2 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected2, actual2);
        assertEquals("cat", outStream.toString().trim());
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 3 from assignment directions
    @Test
    public void mainTest3() throws Exception {
        File inputFile2 = createInputFile(FILE1);

        String args[] = {"-r", "1", inputFile2.getPath()};
        Main.main(args);

        String expected3 = "2 cat";

        String actual3 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected3, actual3);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 4 from assignment directions
    @Test
    public void mainTest4() throws Exception {
        File inputFile3 = createInputFile(FILE1);

        String args[] = {"-k", "2", "-s", "dog", "-a", inputFile3.getPath()};
        Main.main(args);

        String expected4 = "2 cat";

        String actual4 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected4, actual4);
        assertEquals("dog", outStream.toString().trim());
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Extra example
    @Test
    public void mainTest5() throws Exception {
        File inputFile5 = createInputFile(FILE2);

        String args[] = {"-a", "-s", "orEO", inputFile5.getPath()};
        Main.main(args);

        String expected5 = "Error: 123 xyz\nError: 567 abc\nLog: 123 abc\nLog: 567 abc";

        String actual5 = getFileContent(inputFile5.getPath());

        assertEquals("The files differ!", expected5, actual5);
        assertEquals("Error", outStream.toString().trim());
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Extra example
    @Test
    public void mainTest6() throws Exception {
        File inputFile6 = createInputFile(FILE2);

        String args[] = {"-k", "abc", inputFile6.getPath()};
        Main.main(args);

        String expected6 = "Log: 123 abc\nError: 567 abc\nLog: 567 abc";

        String actual6 = getFileContent(inputFile6.getPath());

        assertEquals("The files differ!", expected6, actual6);
    }


    // Purpose: To provide an example of a test case format (no arguments passed)
    // Frame #: Instructor error example
    @Test
    public void mainTest7() {
        //no arguments on the command line will pass an array of length 0 to the application, not null.
        String args[]  = new String[0];
        Main.main(args);
        assertEquals("Usage: filesummary [-a [int]] [-r string | -k string] [-s string] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Extra example
    @Test
    public void mainTest8() throws Exception {
        File inputFile8 = createInputFile(FILE3);

        String args[] = {"-r", "red", inputFile8.getPath()};
        Main.main(args);

        String expected8 = "Up with the white and gold";

        String actual8 = getFileContent(inputFile8.getPath());

        assertEquals("The files differ!", expected8, actual8);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Extra example
    @Test
    public void mainTest9() throws Exception {
        File inputFile9 = createInputFile(FILE3);

        String args[] = {"-k", "with", "-s", "with e", inputFile9.getPath()};
        Main.main(args);

        String expected9 = FILE3;

        String actual9 = getFileContent(inputFile9.getPath());

        assertEquals("The files differ!", expected9, actual9);
        assertEquals(" with the white ".trim(), outStream.toString().trim());
    }

}