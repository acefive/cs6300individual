package edu.gatech.seclass;

public class CoverageClass {

    private int value = 0;

    public void coverageMethod1(int i) {
        if(i != 0) {
            i = -i;
        }
        System.out.println(1 / i);
    }

    public void coverageMethod2(int j) {
        if(j == 0) {
            value++;
        }
        System.out.println(1 / value);
    }

    public void coverageMethod3(int j) {
        // Suppose there is a test suite with less than 100% statement coverage, revealing the fault.
        // Then we add more tests inside that test suite to form a new test suite with 100% statement coverage.
        // Then the new test suite reveals the fault, which contradicts the second condition.
    }

    public void coverageMethod4() {
        // Suppose there is a test suite with 100% branch coverage, not revealing the fault.
        // Then it achieves 100% statement coverage and doesn't reveal the fault.
        // That contradicts the second condition.
    }

    public boolean coverageMethod5 (boolean a, boolean b) {
        int x = 1;
        int y = 1;
        if(a)
            y -= x ;
        else
            x += y;
        if(b)
            y = x/y;
        else
            y = x*y;
        return (y == 0);
    }

    // ================
    //
    // Fill in column “output” with T, F, or E:
    //
    // | a | b |output|
    // ================
    // | T | T |  E   |
    // | T | F |  T   |
    // | F | T |  F   |
    // | F | F |  F   |
    // ================
    //
    // Fill in the blanks in the following sentences with
    // “NEVER”, “SOMETIMES” or “ALWAYS”:
    //
    // Test suites with 100% statement coverage SOMETIMES reveal the fault in this method.
    // Test suites with 100% branch coverage SOMETIMES reveal the fault in this method.
    // Test suites with 100% path coverage ALWAYS reveal the fault in this method.
    // ================

}
