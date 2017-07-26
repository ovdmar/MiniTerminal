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
public class Rm implements Executor {
    private List<String> filesToBeDeleted;
    private static Rm instance;
    private boolean removeDirectory = false;

    public Rm setRemoveDirectory(boolean removeDirectory) {
        instance.removeDirectory = removeDirectory;
        return instance;
    }



    private Rm(List<String> filesToBeDeleted) {
        this.filesToBeDeleted = filesToBeDeleted;
    }

    public static Rm getInstance(List<String> filesToBeDeleted) {
        if (instance == null) {
            instance = new Rm(filesToBeDeleted);
        } else {
            instance.filesToBeDeleted = filesToBeDeleted;
        }
        return instance;
    }

    @Override
    public String execute() {
        String output = "";
        for (String toDelete : filesToBeDeleted) {
            String filePath = DataLoader.getAbsolutePath(toDelete);
            File file = new File(filePath);
            if(!file.exists()){
                output += filePath + ": No such file or directory\n";
                continue;
            }

            if (file.isDirectory() && !removeDirectory) {
                if (file.listFiles() != null && file.listFiles().length != 0) {
                    output += "Directory not empty! Use -r for a recursive deletion\n";
                } else {
                    output += "Empty directory! Use rmdir in order to delete it\n";
                }
                continue;
            }

            try {
                Files.deleteIfExists(file.toPath());
                output += "Deleted " + filePath + "\n";
            } catch (IOException e) {
                output += "Could not delete file directory " + filePath + "\n";
            }
        }
        removeDirectory = false;
        return output;

    }
}
