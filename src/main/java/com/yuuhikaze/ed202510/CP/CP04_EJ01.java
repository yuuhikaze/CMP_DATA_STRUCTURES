package com.yuuhikaze.ed202510.CP;

public class CP04_EJ01 {

    static double compute_nthHarmonicNumber(double k) {
        if (k == 1)
            return 1;
        else if (k < 1)
            throw new RuntimeException("k is less than 1, this should not happen!");
        return (1 / k) + compute_nthHarmonicNumber(k - 1);
    }

    public static void main(String[] args) {
        System.out.println(compute_nthHarmonicNumber(4));
    }
}
