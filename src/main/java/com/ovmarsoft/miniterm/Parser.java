package com.ovmarsoft.miniterm;

import com.ovmarsoft.miniterm.commands.*;
import com.ovmarsoft.miniterm.tools.Constants;
import com.ovmarsoft.miniterm.tools.DataLoader;
import com.ovmarsoft.miniterm.tools.Displayer;
import com.ovmarsoft.miniterm.tools.ExecutionChainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ovidiu on 7/26/17.
 */
public class Parser {
    private ExecutionChainer execChainer;

    public Parser() {
        execChainer = new ExecutionChainer();
    }

    public void parse(String input) {
        List<String> rawCommand = Arrays.asList(input.split("[>]"));

        String[] splittedCommand = rawCommand.get(0).split(" ");
        String filePathToRedirectTo = null;
        if (rawCommand.size() > 1) {
            filePathToRedirectTo = DataLoader.getAbsolutePath(rawCommand.get(1));
        }
        List<String> args = null;
        if (splittedCommand.length > 1) {
            args = Arrays.asList(splittedCommand).subList(1, splittedCommand.length);
        }

        switch (splittedCommand[0]) { /* java 8 feature */
            case "exit":
                Displayer.niceExit(Constants.ACTION_CLOSE, null);
                break;
            case "dir":
            case "ls":
                if (splittedCommand.length > 1) {
                    execChainer.execute(Ls.getInstance().setMultiplePaths(args));
                } else {
                    execChainer.execute(Ls.getInstance());
                }
                break;
            case "type":
            case "cat":
                if (splittedCommand.length > 1) {
                    execChainer.execute(Cat.getInstance().setMultiplePaths(args));
                } else {
                    execChainer.execute(Ls.getInstance());
                }
                break;
            case "pwd":
                System.out.println(DataLoader.getCrtDirectory());
                break;
            case "cd":
                if (splittedCommand.length > 1) {
                    execChainer.execute(Cd.getInstance().setPath(args.get(0)));
                } else {
                    execChainer.execute(Cd.getInstance());
                }
                break;
            case "clear":
            case "cls":
                try {
                    if (DataLoader.getOsType() == Constants.OS_WINDOWS) {
                        Runtime.getRuntime().exec("cls");
                    } else {
                        System.out.print(String.format("\033[2J"));
                        System.out.print(String.format("\033[%dA", 25));
                    }
                } catch (IOException e) {
                    System.out.println("Clear has failed");
                }
                break;
            case "cp":
            case "copy":
                if (splittedCommand.length != 3) {
                    System.out.println("Not enough arguments!");
                } else {
                    execChainer.execute(Copy.getInstance(splittedCommand[1], splittedCommand[2]));
                }
                break;
            case "mkdir":
                if (splittedCommand.length > 1) {
                    execChainer.execute(Mkdir.getInstance(args));
                } else {
                    System.out.println("Missing operand");
                }
                break;
            case "rm":
                if (splittedCommand.length > 1) {
                    execChainer.execute(Rm.getInstance(args));
                } else {
                    System.out.println("Missing operand");
                }
                break;
            case "rmdir":
                if (splittedCommand.length > 1) {
                    execChainer.execute(Rm.getInstance(args).setRemoveDirectory(true));
                } else {
                    System.out.println("Missing operand");
                }
                break;

            case "touch":
                if (splittedCommand.length > 1) {
                    for (String newFile : args) {
                        Path newPath = Paths.get(DataLoader.getAbsolutePath(newFile));
                        if (!Files.exists(newPath)) {
                            try {
                                Files.createFile(newPath);
                            } catch (IOException e) {
                                System.out.println("Error while creating " + newFile);
                            }
                        }
                    }
                } else {
                    System.out.println("Missing operand");
                }
                break;

            case "ps":

            default:
                System.out.println("unrecognized command");
                break;
        }

    }
}
