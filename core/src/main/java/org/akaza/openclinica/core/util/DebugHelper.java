package org.akaza.openclinica.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

public class DebugHelper implements Serializable {
    public DebugHelper() {
        super();
    }

    public String getCurrentGitBranch() {
        // Obtained from https://stackoverflow.com/questions/49106104/get-current-git-branch-inside-a-java-test
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown(error)";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Unknown(interrupted)";
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown(error reading command output)";
        }
    }
}