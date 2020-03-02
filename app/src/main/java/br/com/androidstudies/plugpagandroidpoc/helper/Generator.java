package br.com.androidstudies.plugpagandroidpoc.helper;

import java.util.Random;

public class Generator {
    /**
     * Generates a random payment value.
     *
     * @return Random payment value.
     */
    public static final int generateValue() {
        return new Random(System.currentTimeMillis()).nextInt(400) + 100;
    }

    /**
     * Generates a random amount of installments.
     *
     * @return Random amount of installments.
     */
    public static final int generateInstallments() {
        return new Random(System.currentTimeMillis()).nextInt(10) + 2;
    }
}
