package com.ovmarsoft.miniterm.tools;


import java.util.List;

/**
 * Created by ovidiu on 7/25/17.
 * <p>
 * Class used to display certain messages to stdout.
 */
public class Displayer {
    /**
     * Displays the default bash prompt.
     */
    public static void showPrompt() {
        String prompt = DataLoader.getCrtUser() + "@" +
                        DataLoader.getHostName() + ":" +
                        DataLoader.getCrtDirectory() + "$ ";
        System.out.print(prompt);
    }

    /**
     * Nicely exits the MiniTerminal according to the received failure code.
     *
     * @param actionCode the associated code in order to follow a certain flow
     * @param exception  the exception thrown, if any
     */
    public static void niceExit(int actionCode, Exception exception) {
        switch (actionCode) {
            case Constants.OSCommandsError:
                System.err.println("Encountered a problem while reading OS commands from the json file!");
                break;

            case Constants.OSNotRecognized:
                System.err.println("The operating system is not recognized!");
                System.err.println("Try running the app on Windows or on a Unix-like OS!");
                break;

            case Constants.ACTION_CLOSE:
                System.out.println("Bye!");
                break;

            default:
                System.err.println("Unknown error!");
                if (exception != null) {
                    exception.printStackTrace();
                }
        }

        Thread thread = new Thread(() -> {
            System.err.print("Exiting");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(250);
                } catch (Exception f) {
                /* do nothing - if the exception occurred print all the dots at once */
                }
                System.err.print(".");
            }
            System.err.println();
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            /* do nothing */
        }

        System.exit(-1);
    }

    public static void print(String commandOutput) {
        System.out.println(commandOutput);
    }

    //TODO
    public static void alignedPrint(List<String> files){
        System.out.println(files);
    }
}
