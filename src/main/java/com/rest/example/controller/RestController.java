package com.rest.example.controller;

import com.rest.example.service.ScriptTaskService;
import com.rest.example.util.ScriptListMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@RequestMapping(value = "/script")
public class RestController {

    @Autowired
    ScriptTaskService taskService;

    @RequestMapping(value = "/listScripts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ScriptListMessage> getAllScripts() throws ScriptException {
        ScriptListMessage message = new ScriptListMessage();
        message.setSriptsId(taskService.getAllScriptTasks());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addScript(@RequestParam("script") String script) throws ScriptException {

        Integer id = taskService.addScriptTask(script);

        return new ResponseEntity<>("{Location:/restful/script/getStatus/" + id + "}", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteScript(@PathVariable Integer id) throws ScriptException, InterruptedException {
        taskService.deleteScriptTask(id);

    }

    @RequestMapping(value = "/getStatus/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getScriptStatus(@PathVariable Integer id) throws ScriptException {
        String response = "";
        Future<String> result = taskService.getStatus(id);
        if (result != null) {
            if (!result.isDone()) {
                response = "{status:in progress}";
            } else {
                try {
                    response = "{status:done;result:" + result.get() + "}";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }else {
            response = "{error:resource not available}";
        }
        return response;
    }


}
