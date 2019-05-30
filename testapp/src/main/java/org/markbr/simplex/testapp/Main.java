package org.markbr.simplex.testapp;

import org.markbr.simplex.testapp.exceptions.OutOfRangeException;

public class Main {

    public static void main(String[] args) {
        throw new OutOfRangeException(50, 70, 100);
    }
}
