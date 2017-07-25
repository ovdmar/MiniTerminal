package com.ovmarsoft.miniterm;

import com.ovmarsoft.miniterm.tools.DataLoader;
import com.ovmarsoft.miniterm.tools.Displayer;
import java.util.Scanner;

public class MiniTerminal {


    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        String userInput;


        Parser parser = new Parser();

        while (true) {
            Displayer.showPrompt();

            userInput = stdin.nextLine();
            parser.parse(userInput);

        }
    }
}
