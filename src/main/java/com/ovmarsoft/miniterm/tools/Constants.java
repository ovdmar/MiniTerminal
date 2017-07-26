package com.ovmarsoft.miniterm.tools;

/**
 * Created by ovidiu on 7/25/17.
 */
public interface Constants {
    int OS_WINDOWS = 0;
    int OS_UNIX_LIKE = 1;

    int ACTION_CLOSE = 2;

    /* Errors - will follow 3XX pattern */
    int OSCommandsError = 301;
    int OSNotRecognized = 302;


    String UNKNOWN_HOST = "unknown_host";
    String WINDOWS_PATH_SEP = "\\";
    String UNIX_PATH_SEP = "/";
}
