package com.ovmarsoft.miniterm.commands;

import com.ovmarsoft.miniterm.tools.DataLoader;
import com.ovmarsoft.miniterm.tools.Displayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by ovidiu on 7/26/17.
 */
public class Cat {
    public static void execute(List<String> filenames){
        BufferedReader reader;
        for(String file : filenames) {
            try {
                File path = new File(DataLoader.getCrtDirectory() + "/" + file);
                if (path.isDirectory()) {
                    throw new IllegalArgumentException();
                }
                reader = new BufferedReader(new FileReader(path));
            }  catch (IllegalArgumentException e) {
                Displayer.print(file + ": Is a directory");
                continue;
            } catch (IOException e) {
                Displayer.print(file + ": No such file or directory");
                continue;
            }

            try {
                String line = reader.readLine();
                while(line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                Displayer.print(file + ": Error while reading the file");
            }

        }
    }
}
