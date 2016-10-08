package com.rest.example.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;


public interface ScriptTaskService {

    Future<String> getStatus(Integer id);

    List<Integer> getAllScriptTasks();

    Integer addScriptTask(String script);

    void deleteScriptTask(Integer id);
}
