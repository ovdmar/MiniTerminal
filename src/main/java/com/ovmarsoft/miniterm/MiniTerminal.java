package com.ovmarsoft.miniterm;

import java.util.Scanner;

public class MiniTerminal {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        String userInput;


        boolean isRunning = true;
        while (isRunning) {
            /* TODO: do window cleanup */

            userInput = stdin.nextLine();
            
            isRunning = false;
        }
    }
}
