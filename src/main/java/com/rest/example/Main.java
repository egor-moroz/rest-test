package com.rest.example;


import com.rest.example.service.ScriptTaskService;
import com.rest.example.service.impl.ScriptTaskServiceImpl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ScriptException, InterruptedException, ExecutionException {
      /*  ByteArrayOutputStream interceptConsoleOut = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(interceptConsoleOut);
        PrintStream old = System.out;
        System.setOut(ps);
        System.out.println("Hellow world");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("print('Hello World!');");
        System.out.flush();
        System.setOut(old);*/

        ScriptTaskService scriptTaskService = new ScriptTaskServiceImpl();
        Integer i = scriptTaskService.addScriptTask("print('Hello World!');");
        Integer i1 = scriptTaskService.addScriptTask("while(true){}");
        System.out.println(i);
        Future<String> result1 = scriptTaskService.getStatus(i1);
       /* Thread.sleep(10000);*/
        System.out.println("is done = "+result1.isDone());
        System.out.println(result1.isDone());
        scriptTaskService.deleteScriptTask(i1);


       /* System.out.println(result);*/
       /*System.out.println(interceptConsoleOut.toString());*/
       /* System.out.println(Thread.activeCount());
        ScriptTaskService scriptTaskService = new ScriptTaskServiceImpl();
        Integer c = scriptTaskService.addScriptTask("while (true) {\\n\" +\n" +
                "                \"}");
        System.out.println(Thread.activeCount());
        scriptTaskService.deleteScriptTask(c);
        Thread.sleep(1000);
        System.out.println(Thread.activeCount());*/

        System.out.println("is done = "+result1.isDone());

    }
}
