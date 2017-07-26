package com.ovmarsoft.miniterm.commands;

/**
 * Functional interface implemented by all command executors.
 *
 * The execute method will do its thing and then return the command output, so the Execution chainer can
 * redirect input/output through >, < or |.
 *
 * Created by ovidiu on 7/26/17.
 */
public interface Executor {
    String execute();
}
