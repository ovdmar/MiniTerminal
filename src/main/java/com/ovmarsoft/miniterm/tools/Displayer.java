package com.ovmarsoft.miniterm.tools;

/**
 * Created by ovidiu on 7/25/17.
 *
 * Class used to display certain messages to stdout.
 */
public class Displayer {

    /**
     * Nicely exits the MiniTerminal according to the received failure code.
     *
     * @param failureCode the associated failure code in order to follow a certain flow
     * @param exception the exception thrown, if any
     */
    public static void niceExit(int failureCode, Exception exception) {
        switch (failureCode) {
            case Constants.OSCommandsError:
                System.err.println("Encountered a problem while reading OS commands from the json file!");
                break;

            case Constants.OSNotRecognized:
                System.err.println("The operating system is not recognized!");
                System.err.println("Try running the app on Windows or on a Unix-like OS!");
                break;

            default:
                System.err.println("Unknown error!");
                if (exception != null) {
                    exception.printStackTrace();
                }
        }

        System.err.println("Exiting");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception f) {
                    /* do nothing - if the exception occurred print all the dots at once */
            }
            System.err.print(".");
        }
        System.err.println();
        /* TODO: clear the window */

        System.exit(-1);
    }
}
