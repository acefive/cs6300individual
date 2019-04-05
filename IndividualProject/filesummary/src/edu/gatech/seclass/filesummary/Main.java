package edu.gatech.seclass.filesummary;

import org.apache.commons.cli.*;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class Main {

    enum Eol {
        WINDOWS, MAC, LINUX
    }

    public static void main(String[] args) {

        if(args.length == 0) {
            usage();
            return;
        }

        Charset charset = StandardCharsets.UTF_8;

        boolean filesummaryNoOptions = true;
        String filePathString = args[args.length-1];
        Path filePath = Paths.get(filePathString);

        String lineSep = "";
        try {
            Reader r = new FileReader(filePathString);
            int i = -1;
            Eol eol = null;
            while(eol == null) {
                if((i = r.read()) != -1) {
                    if(i == '\r') {
                        i = r.read();
                        if(i == '\n') {
                            eol = Eol.WINDOWS;
                            lineSep = "\r\n";
                        } else {
                            eol = Eol.MAC;
                            lineSep = "\r";
                        }
                    } else if(i == '\n') {
                        eol = Eol.LINUX;
                        lineSep = "\n";
                    }
                }
            }
            r.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        args = Arrays.copyOf(args, args.length-1);

        Options options = new Options();
        options.addOption("s", true, "if specified, the filesummary utility will output the longest sequence in the file made up of only characters in <string>. This option is always applied first.");
        options.addOption("r", true, "if specified, the filesummary utility will remove only lines in the file which contain the provided <string>.   -r and -k are mutually exclusive. ");
        options.addOption("k", true, "if specified, the filesummary utility will keep only lines in the file which contain the provided <string>.   -r and -k are mutually exclusive. ");
        Option aOption = Option.builder("a")
                .desc("if specified, the filesummary utility will reorder the lines in the file, alphabetizing the lines using only their alphanumeric characters, with numbers alphabetized as coming before letters (0-9 then A-Z).  If the optional parameter [integer] is included, it must be a positive integer, and the utility will skip the number of characters specified on each line, alphabetizing the lines by using the alphanumeric characters after [integer] characters.")
                .hasArg()
                .build();
        aOption.setOptionalArg(true);
        options.addOption(aOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            List<String> lines = Files.readAllLines(filePath, charset);

            if(cmd.hasOption("s")) {
                String sArgument = cmd.getOptionValue("s");
                String allLines = "";
                for(String line : lines) {
                    allLines += line + lineSep;
                }
                if(sArgument == null) {
                    //TODO
                } else {
                    Matcher matcher = Pattern.compile("["+sArgument+"]+").matcher(allLines);
                    List<String> allMatches = new ArrayList<String>();
                    while(matcher.find()){
                        allMatches.add(matcher.group());
                    }
                    Collections.sort (allMatches, new Comparator<String>() {
                        public int compare(String string1, String string2) {
                            return string2.length() - string1.length();
                        }
                    });
                    System.out.println(allMatches.get(0));
                }
                filesummaryNoOptions = false;
            }
            if(cmd.hasOption("r") && !cmd.hasOption("k")) {
                String rArgument = cmd.getOptionValue("r");
                if(rArgument == null) {
                    // TODO
                } else {
                    Iterator iterator = lines.iterator();
                    while(iterator.hasNext()) {
                        String string = (String)iterator.next();
                        if(string.contains(rArgument)) {
                            iterator.remove();
                        }
                    }
                }
                filesummaryNoOptions = false;
            }
            if(cmd.hasOption("k") && !cmd.hasOption("r")) {
                String kArgument = cmd.getOptionValue("k");
                if(kArgument == null) {
                    // TODO
                } else {
                    Iterator iterator = lines.iterator();
                    while(iterator.hasNext()) {
                        String string = (String)iterator.next();
                        if(!string.contains(kArgument)) {
                            iterator.remove();
                        }
                    }
                }
                filesummaryNoOptions = false;
            }
            if(cmd.hasOption("a")) {
                String aArgument = cmd.getOptionValue("a");

                if(aArgument == null) {
                    Collections.sort (lines);
                } else {
                    int aNumber = Integer.parseInt(aArgument);
                    if(aNumber <= 0) {
                        // TODO
                    } else {
                        Collections.sort (lines, new Comparator<String>() {
                            public int compare(String string1, String string2) {
                                String sub1 = string1.substring (aNumber);
                                String sub2 = string2.substring (aNumber);
                                return sub1.compareTo (sub2);
                            }
                        });
                    }
                }
                filesummaryNoOptions = false;
            }
            if(filesummaryNoOptions) {
                int totalWord = 0;
                for(String line : lines) {
                    line = line.replaceAll("[^a-zA-Z0-9]+", " ");
                    String[] countNum = line.trim().split("\\s+");
                    if(!countNum[0].equals("")) {
                        totalWord += countNum.length;
                    }
                }
                System.out.println(totalWord);
            }
            FileWriter writer = new FileWriter(filePathString);

            for(int i = 0; i < lines.size() - 1; i++) {
                writer.write(lines.get(i) + lineSep);
            }
            writer.write(lines.get(lines.size()-1));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void usage() {
        System.err.println("Usage: filesummary [-a [int]] [-r string | -k string] [-s string] <filename>");
    }

}