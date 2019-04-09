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

public class MyMainTest {

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
    private static final String FILE4 = "Log: 123 abc\nError: 567 abc\nLog: 567 abc";
    private static final String FILE5 = "Log: 123 abc\nError: 123 xyz\nLog: 567 abc";
    private static final String FILE6 = "Log: 123 abc\nError: 123 xyz\nError: 567 abc";
    private static final String FILE7 = "Error: 123 xyz\nLog: 567 abc";
    private static final String FILE8 = "Error: 123 xyz\nError: 567 abc";
    private static final String FILE9 = "Error: 567 abc\nLog: 567 abc";
    private static final String FILE10 = "Log: 123 abc\nLog: 567 abc";
    private static final String FILE11 = "Log: 123 abc\nError: 567 abc";
    private static final String FILE12 = "Log: 123 abc\nError: 123 xyz";
    private static final String FILE13 = "Log: 143gv4g23 abc\nError: 123 xyz\nError: 567 abc\nLog: 567 abc";
    private static final String FILE14 = "Log: 123 abc\nError: 123 fr32g4g4xyz\nError: 567 abc\nLog: 567 abc";
    private static final String FILE15 = "Log: 123 abc\nError: 123 xyz\nError: 554ml5y967 abc\nLog: 567 abc";


    // test cases

    /*
     *   TEST CASES
     */

    // Purpose: Multiple arguments for the option -s, we should expect the error since only one string should be provided for -s
    // Frame #4
    @Test
    public void filesummaryTest1() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {"-s", "dog", "cat", inputFile1.getPath()};
        Main.main(args);

        String expected1 = FILE1;

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Purpose: did not provide a file path or file name, so we should expect error from the program, and the file should be unchanged, there should not be any outputs
    // Frame #8
    @Test
    public void filesummaryTest2() throws Exception {
        File inputFile2 = createInputFile(FILE1);

        String args[] = {"-k", "2", "-s", "dog", "-a"};
        Main.main(args);

        String expected2 = FILE1;

        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected2, actual2);
    }

    // Purpose: a is specified but without arguments, s is specified with empty argument, so we directly sort the file, and output empty string
    // Frame #12
    @Test
    public void filesummaryTest3() throws Exception {
        File inputFile3 = createInputFile(FILE1);

        String args[] = {"-a", "-s", "", inputFile3.getPath()};
        Main.main(args);

        String expected3 = "2 cat" + System.lineSeparator() + "1 dog";

        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected3, actual3);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: -a with no argument, -s with one argument, -r with empty string, we should have a valid output, then the file should be sorted based on the dictionary sort, and there is no removal
    // Frame #16
    @Test
    public void filesummaryTest4() throws Exception {
        File inputFile4 = createInputFile(FILE1);

        String args[] = {"-r", "", "-s", "103atocg", "-a", inputFile4.getPath()};
        Main.main(args);

        String expected4 = "2 cat" + System.lineSeparator() + "1 dog";

        String actual4 = getFileContent(inputFile4.getPath());

        assertEquals("The files differ!", expected4, actual4);
        assertEquals("cat", outStream.toString().trim());
    }

    // Purpose: -a with no arguments, -k with some string arguments, so we only need to keep those lines, and reorder the lines in the file
    // Frame #20
    @Test
    public void filesummaryTest5() throws Exception {
        File inputFile5 = createInputFile(FILE1);

        String args[] = {"-a", "-k", "okm", inputFile5.getPath()};
        Main.main(args);

        String expected5 = "";

        String actual5 = getFileContent(inputFile5.getPath());

        assertEquals("The files differ!", expected5, actual5);
    }

    // Purpose: -a with empty argument, -s with empty argument, output nothing, and then order the lines by dictionary
    // Frame #24
    @Test
    public void filesummaryTest6() throws Exception {
        File inputFile6 = createInputFile(FILE1);

        String args[] = {"-a", "", "-s", "", inputFile6.getPath()};
        Main.main(args);

        String expected6 = "2 cat" + System.lineSeparator() + "1 dog";

        String actual6 = getFileContent(inputFile6.getPath());

        assertEquals("The files differ!", expected6, actual6);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: -a with empty argument, -s with one argument, -r with empty string, we should have a new file that is reordered, with no removal, and that we should also have some valid output
    // Frame #28
    @Test
    public void filesummaryTest7() throws Exception {
        File inputFile7 = createInputFile(FILE1);

        String args[] = {"-a", "", "-s", "dog", "-r", "", inputFile7.getPath()};
        Main.main(args);

        String expected7 = "2 cat" + System.lineSeparator() + "1 dog";

        String actual7 = getFileContent(inputFile7.getPath());

        assertEquals("The files differ!", expected7, actual7);
        assertEquals("dog", outStream.toString().trim());
    }

    // Purpose: -a with empty string, -r with some string, the lines of the file should be reordered and that some lines are removed
    // Frame #32
    @Test
    public void filesummaryTest8() throws Exception {
        File inputFile8 = createInputFile(FILE1);

        String args[] = {"-r", "dog123cat", "-a", "", inputFile8.getPath()};
        Main.main(args);

        String expected8 = "2 cat" + System.lineSeparator() + "1 dog";

        String actual8 = getFileContent(inputFile8.getPath());

        assertEquals("The files differ!", expected8, actual8);
    }

    // Purpose: -a with integer 1, -s with empty string, ignore the first character and then we sort the lines of the file, we should have no output for this test
    // Frame #36
    @Test
    public void filesummaryTest9() throws Exception {
        File inputFile9 = createInputFile(FILE1);

        String args[] = {"-s", "", "-a", "1", inputFile9.getPath()};
        Main.main(args);

        String expected9 = "2 cat" + System.lineSeparator() + "1 dog";

        String actual9 = getFileContent(inputFile9.getPath());

        assertEquals("The files differ!", expected9, actual9);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: -a with argument 1, -s with some string, -r with empty string, we should reorder the lines of the file with ignoring the first character, then output some valid string that corresponds the function of the utility, with no removal in the file
    // Frame #40
    @Test
    public void filesummaryTest10() throws Exception {
        File inputFile10 = createInputFile(FILE1);

        String args[] = {"-r", "2", "-s", "a1432g", "-a", "1", inputFile10.getPath()};
        Main.main(args);

        String expected10 = "1 dog";

        String actual10 = getFileContent(inputFile10.getPath());

        assertEquals("The files differ!", expected10, actual10);
        assertEquals("1", outStream.toString().trim());
    }

    // Purpose: -a with 1, -r with some string, we should have the file reordered based on the lines characters, but with some removal that corresponds with the string of -r
    // Frame #44
    @Test
    public void filesummaryTest11() throws Exception {
        File inputFile11 = createInputFile(FILE1);

        String args[] = {"-r", "2", "-a", "1", inputFile11.getPath()};
        Main.main(args);

        String expected11 = "1 dog";

        String actual11 = getFileContent(inputFile11.getPath());

        assertEquals("The files differ!", expected11, actual11);
    }

    // Purpose: -a with max int, -s with empty string, we should keep the file unordered since -a ignores all characters, then output nothing
    // Frame #48
    @Test
    public void filesummaryTest12() throws Exception {
        File inputFile12 = createInputFile(FILE1);

        String args[] = {"-s", "", "-a", "9999999999999999999", inputFile12.getPath()};
        Main.main(args);

        String expected12 = "1 dog" + System.lineSeparator() + "2 cat";

        String actual12 = getFileContent(inputFile12.getPath());

        assertEquals("The files differ!", expected12, actual12);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: -a with max int, -s with some string, -r with empty string, so that we won't reorder the lines of the file, we should have some output that corresponds to the string in -s, and we won't remove any liens in the file
    // Frame #52
    @Test(expected = NullPointerException.class)
    public void filesummaryTest13() throws Exception {
        File inputFile13 = createInputFile(FILE1);

        String args[] = {"-r", "-s", "og", "-a", "999999999999", inputFile13.getPath()};
        Main.main(args);

        String expected13 = "1 dog" + System.lineSeparator() + "2 cat";

        String actual13 = getFileContent(inputFile13.getPath());

        assertEquals("The files differ!", expected13, actual13);
        assertEquals("og", outStream.toString().trim());
    }

    // Purpose: -a with max int, -r with some string, keep the file unordered, then remove several lines that corresponds with the argument comes from -r
    // Frame #56
    @Test
    public void filesummaryTest14() throws Exception {
        File inputFile14 = createInputFile(FILE1);

        String args[] = {"-r", "2", "-a", "9999999999999", inputFile14.getPath()};
        Main.main(args);

        String expected14 = "1 dog";

        String actual14 = getFileContent(inputFile14.getPath());

        assertEquals("The files differ!", expected14, actual14);
    }

    // Purpose: -s with empty string, we only output empty string, and won't make any change to the original input file.
    // Frame #60
    @Test
    public void filesummaryTest15() throws Exception {
        File inputFile15 = createInputFile(FILE1);

        String args[] = {"-s", "", inputFile15.getPath()};
        Main.main(args);

        String expected15 = FILE1;

        String actual15 = getFileContent(inputFile15.getPath());

        assertEquals("The files differ!", expected15, actual15);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: test the chance that if the value provided for a is negative
    // Frame #2
    @Test
    public void filesummaryTest16() throws Exception {
        File inputFile16 = createInputFile(FILE1);

        String args[] = {"-a", "-1", inputFile16.getPath()};
        Main.main(args);

        assertEquals("Usage: filesummary [-a [int]] [-r string [int] | -k string [int]] [-s string] [-n] <filename>", errStream.toString().trim());
    }

    // Purpose: what if there is no argument for "r" option
    // Frame #6
    @Test(expected = NullPointerException.class)
    public void filesummaryTest17() throws Exception {
        File inputFile17 = createInputFile(FILE2);

        String args[] = {"-r", inputFile17.getPath()};
        Main.main(args);
    }

    // Purpose: test that there is no argument for the options a s and r
    // Frame #10
    @Test
    public void filesummaryTest18() throws Exception {
        File inputFile18 = createInputFile(FILE3);

        String args[] = {"-a", "-s", "", "-r", "", inputFile18.getPath()};
        Main.main(args);

        String expected18 = "Down with the red and black\rUp with the white and gold";

        String actual18 = getFileContent(inputFile18.getPath());

        assertEquals("The files differ!", expected18, actual18);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: given some string for the option s and the option r
    // Frame #14
    @Test
    public void filesummaryTest19() throws Exception {
        File inputFile19 = createInputFile(FILE4);

        String args[] = {"-a", "-s", "2ncy943nuc3i", "-r", "567", inputFile19.getPath()};
        Main.main(args);

        String expected19 = "Log: 123 abc";

        String actual19 = getFileContent(inputFile19.getPath());

        assertEquals("The files differ!", expected19, actual19);
        assertEquals("23", outStream.toString().trim());
    }

    // Purpose: given some string for the option s and no r or k option
    // Frame #18
    @Test
    public void filesummaryTest20() throws Exception {
        File inputFile20 = createInputFile(FILE5);

        String args[] = {"-a", "-s", "n23k4jlvhuir", inputFile20.getPath()};
        Main.main(args);

        String expected20 = "Error: 123 xyz\nLog: 123 abc\nLog: 567 abc";

        String actual20 = getFileContent(inputFile20.getPath());

        assertEquals("The files differ!", expected20, actual20);
        assertEquals("23", outStream.toString().trim());
    }

    // Purpose: what if all the argument given a s and r are empty
    // Frame #22
    @Test
    public void filesummaryTest21() throws Exception {
        File inputFile21 = createInputFile(FILE6);

        String args[] = {"-a", "", "-s", "", "-r", "", inputFile21.getPath()};
        Main.main(args);

        String expected21 = "Error: 567 abc\nError: 123 xyz\nLog: 123 abc";

        String actual21 = getFileContent(inputFile21.getPath());

        assertEquals("The files differ!", expected21, actual21);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: given empty string for the option a and some random string for s and r
    // Frame #26
    @Test
    public void filesummaryTest22() throws Exception {
        File inputFile22 = createInputFile(FILE7);

        String args[] = {"-a", "", "-s", "3ithn4ui", "-r", "3", inputFile22.getPath()};
        Main.main(args);

        String expected22 = "Log: 567 abc";

        String actual22 = getFileContent(inputFile22.getPath());

        assertEquals("The files differ!", expected22, actual22);
        assertEquals("3", outStream.toString().trim());
    }

    // Purpose: what if there is empty argument for a and some random string for s
    // Frame #30
    @Test
    public void filesummaryTest23() throws Exception {
        File inputFile23 = createInputFile(FILE8);

        String args[] = {"-a", "", "-s", "32ktmjo1p", inputFile23.getPath()};
        Main.main(args);

        String expected23 = "Error: 567 abc\nError: 123 xyz";

        String actual23 = getFileContent(inputFile23.getPath());

        assertEquals("The files differ!", expected23, actual23);
        assertEquals("123", outStream.toString().trim());
    }

    // Purpose: Test that if the argument provided for a is 1 and empty string for s and r
    // Frame #34
    @Test
    public void filesummaryTest24() throws Exception {
        File inputFile24 = createInputFile(FILE9);

        String args[] = {"-a", "1", "-s", "", "-r", "", inputFile24.getPath()};
        Main.main(args);

        String expected24 = "Log: 567 abc\nError: 567 abc";

        String actual24 = getFileContent(inputFile24.getPath());

        assertEquals("The files differ!", expected24, actual24);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: test that all normal strings provided for the three options
    // Frame #38
    @Test
    public void filesummaryTest25() throws Exception {
        File inputFile25 = createInputFile(FILE10);

        String args[] = {"-a", "1", "-s", "1ot4b7kc4", "-r", "56", inputFile25.getPath()};
        Main.main(args);

        String expected25 = "Log: 123 abc";

        String actual25 = getFileContent(inputFile25.getPath());

        assertEquals("The files differ!", expected25, actual25);
        assertEquals("bc", outStream.toString().trim());
    }

    // Purpose: test that there is only one for the option a and one string for the option s
    // Frame #42
    @Test
    public void filesummaryTest26() throws Exception {
        File inputFile26 = createInputFile(FILE11);

        String args[] = {"-a", "1", "-s", "hi2o1y9p9tr", inputFile26.getPath()};
        Main.main(args);

        String expected26 = "Log: 123 abc\nError: 567 abc";

        String actual26 = getFileContent(inputFile26.getPath());

        assertEquals("The files differ!", expected26, actual26);
        assertEquals("rror", outStream.toString().trim());
    }

    // Purpose: test that there is a big enough number for the option a and empty strings for s and r
    // Frame #46
    @Test
    public void filesummaryTest27() throws Exception {
        File inputFile27 = createInputFile(FILE12);

        String args[] = {"-a", "9999", "-s", "", "-r", "", inputFile27.getPath()};
        Main.main(args);

        String expected27 = "Log: 123 abc\nError: 123 xyz";

        String actual27 = getFileContent(inputFile27.getPath());

        assertEquals("The files differ!", expected27, actual27);
        assertEquals("", outStream.toString().trim());
    }

    // Purpose: test that there is a big enough number for the option a and some random string for s and r
    // Frame #50
    @Test
    public void filesummaryTest28() throws Exception {
        File inputFile28 = createInputFile(FILE13);

        String args[] = {"-a", "9999", "-s", "blabla123", "-r", "23", inputFile28.getPath()};
        Main.main(args);

        String expected28 = "Error: 567 abc\nLog: 567 abc";

        String actual28 = getFileContent(inputFile28.getPath());

        assertEquals("The files differ!", expected28, actual28);
        assertEquals("123", outStream.toString().trim());
    }

    // Purpose: test that there is a big enough number for the option a and some random string for s 
    // Frame #54
    @Test
    public void filesummaryTest29() throws Exception {
        File inputFile29 = createInputFile(FILE14);

        String args[] = {"-a", "9999", "-s", "asbfj iosj23", inputFile29.getPath()};
        Main.main(args);

        String expected29 = "Log: 123 abc\nError: 123 fr32g4g4xyz\nError: 567 abc\nLog: 567 abc";

        String actual29 = getFileContent(inputFile29.getPath());

        assertEquals("The files differ!", expected29, actual29);
        assertEquals("23 ab", outStream.toString().trim());
    }

    // Purpose: test that there is no option a and two empty strings for the option s and r
    // Frame #58
    @Test
    public void filesummaryTest30() throws Exception {
        File inputFile30 = createInputFile(FILE15);

        String args[] = {"-s", "", "-r", "", inputFile30.getPath()};
        Main.main(args);

        String expected30 = "Log: 123 abc\nError: 123 xyz\nError: 554ml5y967 abc\nLog: 567 abc";

        String actual30 = getFileContent(inputFile30.getPath());

        assertEquals("The files differ!", expected30, actual30);
        assertEquals("", outStream.toString().trim());
    }

}
