package edu.gatech.seclass;

public class CoverageClass {

    int value = 2;
    int i = 1;

    public void coverageMethod1() {
        if(i == 1) {
            i++;
        }
        value = value - i + 1;
        System.out.println(1 / value);
    }

    public void coverageMethod2() {

    }
}
