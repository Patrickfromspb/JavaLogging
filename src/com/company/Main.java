package com.company;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {


        logger.warning("Can cause ArrayIndexOutOfBoundsException");

        int[] a = {1, 2, 3};

        int index = 4;

        logger.config("index is set to " + index);


        try {

            System.out.println(a[index]);

        } catch (ArrayIndexOutOfBoundsException ex) {

            logger.log(Level.SEVERE, "Exception occur", ex);

        }

    }
}
