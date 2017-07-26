package com.ovmarsoft.miniterm.tools;

import com.ovmarsoft.miniterm.commands.Executor;

/**
 * Class used to chain commands as redirection or pipe
 *
 * Created by ovidiu on 7/26/17.
 */
public class ExecutionChainer {
    public void execute(Executor executor){
        String cmdOutput = executor.execute();
        if (!cmdOutput.equals("")){
            System.out.println(cmdOutput);
        }
    }
}
