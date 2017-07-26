package com.ovmarsoft.miniterm.commands;

import com.ovmarsoft.miniterm.tools.DataLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by ovidiu on 7/26/17.
 */
public class Mkdir implements Executor {
    private List<String> dirsToBeCreated;
    private static Mkdir instance;

    private Mkdir(List<String> dirsToBeCreated) {
        this.dirsToBeCreated = dirsToBeCreated;
    }

    public static Mkdir getInstance(List<String> dirsToBeCreated) {
        if (instance == null) {
            instance = new Mkdir(dirsToBeCreated);
        } else {
            instance.dirsToBeCreated = dirsToBeCreated;
        }
        return instance;
    }

    @Override
    public String execute() {
        String output = "";
        for(String newDir : dirsToBeCreated) {
            String newDirAbsPath = DataLoader.getAbsolutePath(newDir);
            if (!(new File(newDirAbsPath)).exists()) {
                try {
                    Files.createDirectories(Paths.get(newDirAbsPath));
                } catch (IOException e) {
                    output += "Could not create directory " + newDirAbsPath;
                }
            }
        }

        return output;

    }
}
