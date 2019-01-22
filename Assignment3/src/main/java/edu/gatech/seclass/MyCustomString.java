package edu.gatech.seclass;

import java.io.*;

import java.util.Arrays;

import static java.lang.Character.isDigit;

public class MyCustomString implements MyCustomStringInterface {
    private String string;

    public String getString() {
        return string;
    }

    public void setString(String inputString) {
        string = inputString;
    }

    public int countNumbers() {
        if (string == null || string.equals("")) {
            return 0;
        } else {
            string = string.replaceAll("[^0-9]+", " ");
            return string.trim().split("\\s+").length;
        }
    }

    public String addDigits(int n, boolean reverse) {
        char[] convertedChar = string.toCharArray();
        if(n > 9 || n <= 0) {
            throw new IllegalArgumentException();
        }
        if(string == null) {
            throw new NullPointerException();
        }
        for(int i = 0; i < convertedChar.length; i++) {
            if(isDigit(convertedChar[i])) {
                convertedChar[i] = (char)((Character.getNumericValue(convertedChar[i]) + n) % 10 +'0');
            }
        }
        if(reverse) {
            int j = 0, k = 0;
            for(int i = 0; i < convertedChar.length; i++) {
                if((i == 0 && isDigit(convertedChar[i])) ||
                        (i != 0 && isDigit(convertedChar[i]) && !isDigit(convertedChar[i-1]))) {
                    j = i;
                }
                if((i == convertedChar.length - 1 && isDigit(convertedChar[i])) ||
                        (i != convertedChar.length - 1 && isDigit(convertedChar[i]) && !isDigit(convertedChar[i+1]))) {
                    k = i;
                    char[] temp = new char[k-j+1];
                    for(int ptr = j; ptr <= k; ptr++) {
                        temp[ptr-j] = convertedChar[k+j-ptr];
                    }
                    for(int ptr = j; ptr <= k; ptr++) {
                        convertedChar[ptr] = temp[ptr-j];
                    }
                }
            }
        }
        return new String(convertedChar);
    }

    public void convertDigitsToNamesInSubstring(int startPosition, int endPosition) {
        if(string == null) {
            throw new NullPointerException();
        }
        if(startPosition < 1 || startPosition > endPosition) {
            throw new IllegalArgumentException();
        }
        if(endPosition > string.length()) {
            throw new MyIndexOutOfBoundsException();
        }
        String CustomSubstring = string.substring(startPosition-1, endPosition);
        String ConvertedCustomSubstring = CustomSubstring.replaceAll("([0-9]+)","*$1*");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("0","zero");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("1","one");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("2","two");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("3","three");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("4","four");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("5","five");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("6","six");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("7","seven");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("8","eight");
        ConvertedCustomSubstring = ConvertedCustomSubstring.replaceAll("9","nine");
        string = string.substring(0, startPosition-1) + ConvertedCustomSubstring + string.substring(endPosition);
    }

    public void main(String[] args) {

    }
}