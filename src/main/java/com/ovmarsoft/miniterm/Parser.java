package com.ovmarsoft.miniterm;

import com.ovmarsoft.miniterm.commands.Cat;
import com.ovmarsoft.miniterm.commands.Ls;
import com.ovmarsoft.miniterm.tools.Constants;
import com.ovmarsoft.miniterm.tools.Displayer;

import java.util.Arrays;

/**
 * Created by ovidiu on 7/26/17.
 */
public class Parser {

    public void parse(String input) {
        String[] command = input.split(" ");

        switch (command[0]) {
            case "exit":
                Displayer.niceExit(Constants.ACTION_CLOSE, null);
                break;
            case "cat":
                Cat.execute(Arrays.asList(command).subList(1, command.length));
                break;
            case "ls":
                if (command.length == 1){
                    Ls.execute();
                } else {
                    Ls.execute(Arrays.asList(command).subList(1, command.length));
                }

                break;
            default:
                System.out.println("unrecognized command");
        }
    }
}
