package com.ovmarsoft.miniterm.tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ovidiu on 7/25/17.
 * <p>
 * Singleton exposing an API for data loading;
 */
public class DataLoader {
    private static DataLoader instance = null;
    private static Map<String, String> available_commands;
    private static String crtUser;
    private static String hostName;
    private static String crtDirectory;
    private static String pathLineSeparator;

    private DataLoader() {
        /* singleton private constructor */
        available_commands = new HashMap<>();
        if (crtUser == null || hostName == null) {
            crtUser = retrieveLoggedUser();
            hostName = retrieveHostname();
            pathLineSeparator = retrieveLineSeparator();
        }
    }

    public static String getPathLineSeparator() {
        if (pathLineSeparator == null) {
            pathLineSeparator = retrieveLineSeparator();
        }
        return pathLineSeparator;
    }

    private static String retrieveLineSeparator() {
        if (getOsType() == Constants.OS_WINDOWS) {
            return Constants.WINDOWS_PATH_SEP;
        } else {
            return Constants.UNIX_PATH_SEP;
        }
    }

    public DataLoader getInstance() {
        if (instance == null) {
            instance = new DataLoader();
        }
        return instance;
    }

    /**
     * Calls getOSCommands() in order to get the available commands;
     * Saves the returned map and returns it directly on future calls;
     *
     * @return a map containing the available commands
     */

    public static Map<String, String> getAvailableCommands() {
        if (available_commands == null) {
            try {
                available_commands = getOSCommands();
            } catch (JSONException e) {
                Displayer.niceExit(Constants.OSCommandsError, e);
            } catch (Exception e) {
                Displayer.niceExit(Constants.OSNotRecognized, e);
            }
        }
        return available_commands;
    }

    /**
     * Extracts the supported commands from the corresponding json resource file
     * after finding the current os type.
     *
     * @return a HashMap containing the extracted commands
     * @throws JSONException if there is a problem with the resource file
     * @throws Exception     if the determined OS is not supported by the app
     */
    private static Map<String, String> getOSCommands() throws Exception {
        JSONObject json;

        switch (getOsType()) {
            case Constants.OS_WINDOWS:
                json = new JSONObject(new File("..\\resources\\windows-commands.json"));
                break;
            case Constants.OS_UNIX_LIKE:
                json = new JSONObject(new File("../resources/unix-commands.json"));
                break;
            default:
                /* os not recognized, generating failure code 1 */
                throw new Exception("OS not recognized!");
        }

        Map<String, String> commands = new HashMap<String, String>();
        Iterator iterator = json.keys(); /* if json is null the program wouldn't even be here */
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            commands.put(key, (String) json.get(key)); /* any exception thrown will generate failure code 0 */
        }

        return commands;

    }

    public static int getOsType() {
        /* will return 1 for Unix-like systems :) */
        return System.getProperty("os.name").contains("Windows") ? Constants.OS_WINDOWS : Constants.OS_UNIX_LIKE;
    }

    private static String retrieveLoggedUser() {
        return System.getProperty("user.name");
    }

    protected static String retrieveHostname() {
        String host;
        if (DataLoader.getOsType() == Constants.OS_WINDOWS) {
            host = System.getenv("COMPUTERNAME");
        } else {
            try {
                host = java.net.InetAddress.getLocalHost().getCanonicalHostName();
            } catch (IOException e) {
                host = Constants.UNKNOWN_HOST;
            }
        }
        return host;
    }

    public static void setCrtDirectory(String crtDirectory) {
        String newPath = DataLoader.getAbsolutePath(crtDirectory);
        File newFile = new File(newPath);
        if (newFile.exists()) {
            if (newFile.isDirectory()) {
                DataLoader.crtDirectory = newPath;
                System.setProperty("user.dir", DataLoader.crtDirectory);
            } else {
                System.out.println("Not a directory");
            }
        } else {
            System.out.println("No such file or directory");
        }

    }

    public static String getCrtDirectory() {
        if (crtDirectory == null) {
            crtDirectory = System.getProperty("user.home");
            System.setProperty("user.dir", crtDirectory);
        }

        return DataLoader.getAbsolutePath(crtDirectory);
    }

    public static String getCrtUser() {
        if (crtUser == null) {
            crtUser = retrieveLoggedUser();
        }
        return crtUser;
    }

    public static String getHostName() {
        if (hostName == null) {
            hostName = retrieveHostname();
        }
        return hostName;
    }

    public static String getAbsolutePath(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        try {
            return Paths.get((new File(path)).getCanonicalPath()).toString();
        } catch (IOException e) {
            /* will return absolute path instead of canonical */
        }
        return Paths.get((new File(path)).getAbsolutePath()).toString();
    }
}
