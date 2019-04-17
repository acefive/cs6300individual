package edu.gatech.seclass.filesummary;

import org.apache.commons.cli.*;
import org.junit.rules.TemporaryFolder;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {

        if(args.length == 0) {
            usage();
            return;
        }

        Charset charset = StandardCharsets.UTF_8;

        boolean filesummaryNoOptions = true;
        String filePathString = args[args.length-1];
        Path filePath = Paths.get(filePathString);

        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePathString)));
            //content = Files.readString(Paths.get(filePathString), charset);
        } catch (IOException e) {
            System.err.println("File Not Found");
            return;
        }

        assert content != null;
        if(content.substring(Math.max(content.length() - 1, 0)).equals("\r")) {
            content = "\r" + content.substring(0, Math.max(0, content.length() - 1));
        } else if(content.substring(Math.max(content.length() - 2, 0)).equals("\r\n")) {
            content = "\r\n" + content.substring(0, Math.max(0, content.length() - 2));
        } else if(content.substring(Math.max(content.length() - 1, 0)).equals("\n")) {
            content = "\n" + content.substring(0, Math.max(0, content.length() - 1));
        }
        content = content.replaceAll("\\r", "\r@@@");
        content = content.replaceAll("\\n", "\n@@@");
        content = content.replaceAll("\\r@@@\\n@@@", "\r\n@@@");
        String[] contentSplited = content.split("@@@");

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

        if(cmd != null && cmd.hasOption("r") && cmd.hasOption("k")) {
            usage();
            return;
        }

        if(cmd != null && cmd.hasOption("s")) {
            String sArgument = cmd.getOptionValue("s");
            if(sArgument == null || sArgument.equals("")) {
                System.out.println("");
            } else {
                Matcher matcher = Pattern.compile("["+sArgument+"]+").matcher(content);
                List<String> allMatches = new ArrayList<>();
                while(matcher.find()) {
                    allMatches.add(matcher.group());
                }
                allMatches.sort((string1, string2) -> string2.length() - string1.length());
                if(allMatches.size() != 0) {
                    System.out.println(allMatches.get(0));
                }
            }
            filesummaryNoOptions = false;
        }

        if(cmd != null && cmd.hasOption("n")) {
            int lineValue = 1;
            for(int i = 0; i < contentSplited.length; i++) {
                contentSplited[i] = i+1 + contentSplited[i];
            }
            filesummaryNoOptions = false;
        }

        Iterator<Option> iterator = cmd.iterator();
        while(iterator.hasNext()) {
            Option temp = iterator.next();
            if(temp.equals(aOption)) {
                String aArgument = cmd.getOptionValue("a");
                int aNumber = 0;
                if(aArgument != null && !aArgument.equals("")){
                    if(aArgument.length() > 6) { aNumber = 999999; }
                    else { aNumber = Integer.parseInt(aArgument); }
                    if(aNumber <= 0) {
                        usage();
                        return;
                    }
                }
                int finalANumber = aNumber;
                Arrays.sort(contentSplited, (string1, string2) -> {
                    String sub1, sub2;
                    if (finalANumber < string1.length()) { sub1 = string1.substring(finalANumber).replaceAll("[^a-zA-Z]+", "").trim(); }
                    else { sub1 = ""; }
                    if (finalANumber < string2.length()) { sub2 = string2.substring(finalANumber).replaceAll("[^a-zA-Z]+", "").trim(); }
                    else { sub2 = ""; }
                    if(sub1.length() > 0 && sub2.length() > 0) {
                        while(sub1.charAt(0) == sub2.charAt(0)) {
                            if(sub1.length() <= 1 || sub2.length() <= 1) {
                                return sub1.compareTo(sub2);
                            } else {
                                sub1 = sub1.substring(1);
                                sub2 = sub2.substring(1);
                            }
                        }
                        if(Character.toUpperCase(sub1.charAt(0)) == Character.toUpperCase(sub2.charAt(0))) {
                            if(Character.isUpperCase(sub1.charAt(0))) {
                                return 1;
                            } else {
                                return -1;
                            }
                        } else {
                            return sub1.compareToIgnoreCase(sub2);
                        }
                    } else {
                        return sub1.compareToIgnoreCase(sub2);
                    }
                });
                filesummaryNoOptions = false;
            }
            if(temp.equals(rOption)) {
                String[] rArgument = cmd.getOptionValues("r");
                if(rArgument == null) {
                    usage();
                    return;
                }
                if(rArgument.length == 2) {
                    int rNumber = Integer.parseInt(rArgument[1]);
                    for(int i = contentSplited.length - 1; i >= 0 && rNumber > 0; i--) {
                        if(contentSplited[i].contains(rArgument[0])) {
                            contentSplited[i] = "";
                            rNumber--;
                        }
                    }
                } else if(!rArgument[0].equals("")) {
                    for(int i = 0; i < contentSplited.length; i++) {
                        if(contentSplited[i].contains(rArgument[0])) {
                            contentSplited[i] = "";
                        }
                    }
                }
                filesummaryNoOptions = false;
            }
            if(temp.equals(kOption)) {
                String[] kArgument = cmd.getOptionValues("k");
                if(kArgument == null) {
                    usage();
                    return;
                }
                if(!kArgument[0].equals("")) {
                    for(int i = 0; i < contentSplited.length; i++) {
                        if(!contentSplited[i].contains(kArgument[0])) {
                            contentSplited[i] = "";
                        }
                    }
                }
                if(kArgument.length == 2) {
                    int kNumber = Integer.parseInt(kArgument[1]);
                    for(int i = contentSplited.length - 1; i >= 0; i--) {
                        if(contentSplited[i] == "") {
                            continue;
                        } else if(kNumber > 0) {
                            kNumber--;
                        } else {
                            contentSplited[i] = "";
                        }
                    }
                }
                filesummaryNoOptions = false;
            }
        }

        if(filesummaryNoOptions) {
            int totalWord = 0;
            for(String line : contentSplited) {
                line = line.replaceAll("[^a-zA-Z0-9]+", " ");
                String[] countNum = line.trim().split("\\s+");
                if(!countNum[0].equals("")) {
                    totalWord += countNum.length;
                }
            }
            System.out.println(totalWord);
        }

        if(cmd.hasOption("a")) {
            for(int i = 0; i < contentSplited.length - 1; i++) {
                if(!contentSplited[i].substring(Math.max(contentSplited[i].length() - 1, 0)).equals("\r") &&
                        !contentSplited[i].substring(Math.max(contentSplited[i].length() - 2, 0)).equals("\r\n") &&
                        !contentSplited[i].substring(Math.max(contentSplited[i].length() - 1, 0)).equals("\n") &&
                        !contentSplited[i].equals("")) {
                    contentSplited[i] += System.lineSeparator();
                }
            }
        }

        content = String.join("", contentSplited);

        if(!content.equals("\r") && !content.equals("\r\n") && !content.equals("\n")) {
            if(content.substring(Math.max(content.length() - 1, 0)).equals("\r")) {
                content = content.substring(0,content.length()-1);
            } else if(content.substring(Math.max(content.length() - 2, 0)).equals("\r\n")) {
                content = content.substring(0,content.length()-2);
            } else if(content.substring(Math.max(content.length() - 1, 0)).equals("\n")) {
                content = content.substring(0,content.length()-1);
            }
        }

        try {
            OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(filePath.toFile()), StandardCharsets.UTF_8);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void usage() {
        System.err.println("Usage: filesummary [-a [int]] [-r string [int] | -k string [int]] [-s string] [-n] <filename>");
    }

}