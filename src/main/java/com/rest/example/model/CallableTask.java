package com.rest.example.model;

import javax.script.*;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;

import java.io.PrintStream;
import java.util.concurrent.Callable;

public class CallableTask implements Callable<String> {

    private final String script;

    public CallableTask(String script) {
        this.script = script;
    }

    @Override
    public String call() throws Exception {

        ByteArrayOutputStream interceptConsoleOut = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(interceptConsoleOut);
        PrintStream old = System.out;
        System.setOut(ps);
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        String result = "";
        try {
            while (!Thread.currentThread().isInterrupted()) {
                engine.eval(script);
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            result = "sorry... script error";
            return result;
        }
        System.out.flush();
        System.setOut(old);

        return interceptConsoleOut.toString();
    }

}
