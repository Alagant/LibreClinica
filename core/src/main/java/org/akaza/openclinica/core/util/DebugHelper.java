package org.akaza.openclinica.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

public class DebugHelper implements Serializable {
    public DebugHelper() {
        super();
    }
    public String getCurrentGitBranch() throws IOException, InterruptedException {
        // Obtained from https://stackoverflow.com/questions/49106104/get-current-git-branch-inside-a-java-test
        Process process = Runtime.getRuntime().exec( "git rev-parse --abbrev-ref HEAD" );
        process.waitFor();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader( process.getInputStream() ) );

        return reader.readLine();
    }}
