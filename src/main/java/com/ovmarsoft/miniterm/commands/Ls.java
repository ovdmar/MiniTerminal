package com.ovmarsoft.miniterm.commands;

import com.ovmarsoft.miniterm.tools.DataLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovidiu on 7/26/17.
 *
 * Class containing methods used to execute the "cat" unix command.
 */
public class Ls implements Executor {
    private String path;
    private List<String> multiplePaths;
    private static Ls instance;

    private Ls() {
        path = DataLoader.getCrtDirectory();
    }

    public static Ls getInstance(){
        return getInstance(DataLoader.getCrtDirectory());
    }

    public static Ls getInstance(String path){
        if (instance == null) {
            instance = new Ls();
        }
        return instance.setPath(DataLoader.getAbsolutePath(path));
    }


    public Ls setPath(String newPath) {
        path = newPath;
        return instance;
    }

    public String execute() {
        if (multiplePaths == null) {
            return execute(path);
        }
        return execute(multiplePaths);
    }

    public String execute(List<String> files) {
        String commandOutput = "";
        for(String str : files) {
            commandOutput += execute(DataLoader.getAbsolutePath(str));
        }

        multiplePaths = null;
        return commandOutput;
    }

    private String execute(String path) {
        String commandOutput = "";
        List<String> toDisplay = new ArrayList<>();

        File dir = new File(path);
        if(!dir.exists()) {
            commandOutput += path + ": No such file or directory";
            return commandOutput;
        }
        System.out.println(path + ":");
        if (dir.isDirectory() && dir.listFiles() != null) {
            for (File file : dir.listFiles()){
                if (file.getName().startsWith(".")){
                    continue;
                }
                if (file.isDirectory()){
                    toDisplay.add(file.getName());
                } else {
                    toDisplay.add(file.getName());
                }
            }
        } else {
            toDisplay.add(path);
        }

        toDisplay.sort(String::compareTo);
        commandOutput = toDisplay.toString(); //TODO align ls output
        path = null;
        return commandOutput;
    }

    public Ls setMultiplePaths(List<String> multiplePaths) {
        this.multiplePaths = multiplePaths;
        return instance;
    }
}
