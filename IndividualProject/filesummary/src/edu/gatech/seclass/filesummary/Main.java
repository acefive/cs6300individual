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
            int i;
            Eol eol = null;
            while(eol == null && (i = r.read()) != -1) {
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
            r.close();
        } catch (FileNotFoundException e) {
            System.err.print("File Not Found");
            return;
        }
        catch (IOException e) { e.printStackTrace(); }

        args = Arrays.copyOf(args, args.length-1);

        Options options = new Options();
        options.addOption("s", true, "if specified, the filesummary utility will output the longest sequence in the file made up of only characters in <string>. This option is always applied first.");

        Option aOption = Option.builder("a")
                .desc("if specified, the filesummary utility will reorder the lines in the file, alphabetizing the lines using only their alphanumeric characters, with numbers alphabetized as coming before letters (0-9 then A-Z).  If the optional parameter [integer] is included, it must be a positive integer, and the utility will skip the number of characters specified on each line, alphabetizing the lines by using the alphanumeric characters after [integer] characters.")
                .hasArg()
                .build();
        aOption.setOptionalArg(true);
        options.addOption(aOption);

        Option rOption = Option.builder("r")
                .desc("if specified, the filesummary utility will remove only lines in the file which contain the provided <string>.   -r and -k are mutually exclusive.")
                .hasArgs()
                .build();
        rOption.setOptionalArg(true);
        options.addOption(rOption);

        Option kOption = Option.builder("k")
                .desc("if specified, the filesummary utility will keep only lines in the file which contain the provided <string>.   -r and -k are mutually exclusive.")
                .hasArgs()
                .build();
        kOption.setOptionalArg(true);
        options.addOption(kOption);

        Option nOption = Option.builder("n")
                .desc("if specified, the filesummary utility will prepend the line number from the original file to each line.  This should be applied first, or second when after -s.")
                .build();
        options.addOption(nOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try { cmd = parser.parse(options, args); }
        catch (ParseException e) { e.printStackTrace(); }

        try {
            List<String> lines = Files.readAllLines(filePath, charset);

            if(cmd.hasOption("s")) {
                String sArgument = cmd.getOptionValue("s");
                StringBuilder allLines = new StringBuilder();
                for(String line : lines) { allLines.append(line).append(lineSep); }
                if(sArgument == null || sArgument.equals("")) { System.out.println(); }
                else {
                    Matcher matcher = Pattern.compile("["+sArgument+"]+").matcher(allLines.toString());
                    List<String> allMatches = new ArrayList<>();
                    while(matcher.find()){ allMatches.add(matcher.group()); }
                    allMatches.sort(new Comparator<>() {
                        public int compare(String string1, String string2) {
                            return string2.length() - string1.length();
                        }
                    });
                    System.out.println(allMatches.get(0));
                }
                filesummaryNoOptions = false;
            }
            if(cmd.hasOption("n")) {
                int lineValue = 1;
                for(ListIterator i = lines.listIterator(); i.hasNext(); lineValue++) {
                    i.set(String.valueOf(lineValue) + i.next());
                }
                filesummaryNoOptions = false;
            }
            if(cmd.hasOption("r") && !cmd.hasOption("k")) {
                String[] rArgument = cmd.getOptionValues("r");

                if(rArgument[0] != null && !rArgument[0].equals("")) {
                    Iterator iterator = lines.iterator();
                    while(iterator.hasNext()) {
                        String string = (String)iterator.next();
                        if(string.contains(rArgument[0])) {
                            iterator.remove();
                        }
                    }
                }
                if(rArgument.length == 2) {
                    int rNumber = Integer.parseInt(rArgument[1]);
                    for(int i = 1; i <= rNumber; i++) {
                        lines.remove(lines.size() - i);
                    }
                    filesummaryNoOptions = false;
                }

            }
            if(cmd.hasOption("k") && !cmd.hasOption("r")) {
                String[] kArgument = cmd.getOptionValues("k");

                if(kArgument[0].equals("")) {
                    lines = new ArrayList<>();
                } else {
                    Iterator iterator = lines.iterator();
                    while(iterator.hasNext()) {
                        String string = (String)iterator.next();
                        if(!string.contains(kArgument[0])) {
                            iterator.remove();
                        }
                    }
                }
                if(kArgument.length == 2) {
                    int kNumber = Integer.parseInt(kArgument[1]);
                    for(int i = 0; i < lines.size() - kNumber; i++) {
                        lines.remove(i);
                    }
                }

                filesummaryNoOptions = false;
            }
            if(cmd.hasOption("r") && cmd.hasOption("k")) {
                usage();
                return;
            }
            if(cmd.hasOption("a")) {
                String aArgument = cmd.getOptionValue("a");

                if(aArgument == null || aArgument.equals("")) {
                    lines.sort(new Comparator<String>() {
                        public int compare(String string1, String string2) {
                            return string1.replaceAll("[^a-zA-Z]+", "").trim().compareTo(string2.replaceAll("[^a-zA-Z]+", "").trim());
                        }
                    });
                }
                else {
                    int aNumber;
                    if(aArgument.length() > 6) { aNumber = 999999; }
                    else { aNumber = Integer.parseInt(aArgument); }
                    if(aNumber <= 0) {
                        usage();
                        return;
                    } else {
                        lines.sort(new Comparator<String>() {
                            public int compare(String string1, String string2) {
                                String sub1, sub2;
                                if (aNumber < string1.length()) { sub1 = string1.substring(aNumber).replaceAll("[^a-zA-Z]+", "").trim(); }
                                else { sub1 = ""; }
                                if (aNumber < string2.length()) { sub2 = string2.substring(aNumber).replaceAll("[^a-zA-Z]+", "").trim(); }
                                else { sub2 = ""; }
                                return sub1.compareTo(sub2);
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
            if(lines.size() > 0) {
                writer.write(lines.get(lines.size()-1));
            }
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static void usage() {
        System.err.println("Usage: filesummary [-a [int]] [-r string [int] | -k string [int]] [-s string] [-n] <filename>");
    }

}