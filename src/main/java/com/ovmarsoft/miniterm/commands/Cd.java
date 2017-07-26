package com.ovmarsoft.miniterm.commands;

import com.ovmarsoft.miniterm.tools.DataLoader;
import javafx.scene.chart.XYChart;

/**
 * Created by ovidiu on 7/26/17.
 */
public class Cd implements Executor{
    private String path;
    private static Cd instance;

    private Cd(){
        path = DataLoader.getCrtDirectory();
    }

    public static Cd getInstance(){
        return getInstance(null);
    }

    public static Cd getInstance(String path){
        if (instance == null) {
            instance = new Cd();
        }
        return instance.setPath(path);
    }

    public Cd setPath(String newPath) {
        path = newPath;
        return instance;
    }

    @Override
    public String execute() {
        if (path == null) {
            DataLoader.setCrtDirectory(System.getProperty("user.home"));
        } else {
            DataLoader.setCrtDirectory(path);
        }
        return "";
    }
}
