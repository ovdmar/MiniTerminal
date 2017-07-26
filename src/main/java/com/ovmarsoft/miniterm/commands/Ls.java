package com.ovmarsoft.miniterm.commands;

import com.ovmarsoft.miniterm.tools.DataLoader;
import com.ovmarsoft.miniterm.tools.Displayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovidiu on 7/26/17.
 */
public class Ls {
    public static void execute() {
        execute("");
    }

    public static void execute(List<String> files) {
        for(String str : files) {
            System.out.println(str + ":");
            execute(str);
        }
    }

    public static void execute(String filename) {
        List<String> toDisplay = new ArrayList<>();
        String path = DataLoader.getCrtDirectory() + DataLoader.getPathLineSeparator() + filename;
        File dir = new File(path);
        if (dir.isDirectory()) {
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
        }

        toDisplay.sort(String::compareTo);
        Displayer.alignedPrint(toDisplay);
    }

}
