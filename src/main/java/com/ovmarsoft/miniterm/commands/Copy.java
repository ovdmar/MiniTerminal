package com.ovmarsoft.miniterm.commands;

import com.ovmarsoft.miniterm.tools.DataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by ovidiu on 7/26/17.
 */
public class Copy implements Executor {
    private String source;
    private String destination;
    private static Copy instance;

    private Copy(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public static Copy getInstance(String source, String dest) {
        if (instance == null) {
            instance = new Copy(source, dest);
        } else {
            instance.source = source;
            instance.destination = dest;
        }
        return instance;
    }

    @Override
    public String execute() {
        if (!(new File(DataLoader.getAbsolutePath(source)).exists())){
            return source + ": No such file or directory";
        }

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(DataLoader.getAbsolutePath(source)).getChannel();
            destChannel = new FileOutputStream(DataLoader.getAbsolutePath(destination)).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
            sourceChannel.close();
            destChannel.close();
        } catch(Exception e) {
            System.out.println("Encountered an error while copying the file");
        }

        return "Success!";
    }
}
