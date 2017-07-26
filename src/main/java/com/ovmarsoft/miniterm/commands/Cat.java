package com.ovmarsoft.miniterm.commands;

import com.ovmarsoft.miniterm.tools.DataLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovidiu on 7/26/17.
 * <p>
 * Class containing a method used to execute the "cat" unix command.
 */
public class Cat implements Executor{
    private String path;
    private static Cat instance;
    private List<String> multiplePaths;


    private Cat() {
        path = "";
    }

    public static Cat getInstance(){
        return getInstance("");
    }

    public static Cat getInstance(String path){
        if (instance == null) {
            instance = new Cat();
        }
        return instance.setPath(DataLoader.getAbsolutePath(path));
    }


    public Cat setPath(String path) {
        instance.path = path;
        return instance;
    }

    @Override
    public String execute() {
        if (multiplePaths == null) {
            return execute(path);
        }
        return execute(multiplePaths);
    }

    public String execute(String path) {
        List<String> paths = new ArrayList<>();
        paths.add(path);
        return execute(paths);
    }

    public String execute(List<String> filenames) {
        String commandOutput = "";
        BufferedReader reader;
        for (String filename : filenames) {
            try {
                File path = new File(DataLoader.getAbsolutePath(filename));
                if (path.isDirectory()) {
                    throw new IllegalArgumentException();
                }
                reader = new BufferedReader(new FileReader(path));
            } catch (IllegalArgumentException e) {
                commandOutput += filename + ": Is a directory\n";
                continue;
            } catch (IOException e) {
                commandOutput += filename + ": No such file or directory";
                continue;
            }

            try {
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                commandOutput += path + ": Error while reading the file";
            }
        }
        path = null;
        multiplePaths = null;
        return commandOutput;
    }

    public Cat setMultiplePaths(List<String> multiplePaths) {
        this.multiplePaths = multiplePaths;
        return instance;
    }
}
