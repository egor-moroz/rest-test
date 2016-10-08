package com.rest.example.service.impl;

import com.rest.example.model.CallableTask;
import com.rest.example.service.ScriptTaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ScriptTaskServiceImpl implements ScriptTaskService {

    AtomicInteger count = new AtomicInteger(1);

    public ScriptTaskServiceImpl() {
    }

    private ConcurrentHashMap<Integer, ExecutorService> mapTask = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Future<String>> statusMap = new ConcurrentHashMap<>();

    public Future<String> getStatus(Integer id) {
        Future<String> result = statusMap.get(id);
        return result;
    }

    public List<Integer> getAllScriptTasks() {
        List<Integer> tasksId = new ArrayList<>(mapTask.keySet());
        return tasksId;
    }

    public Integer addScriptTask(String script) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CallableTask callableTask = new CallableTask(script);
        Future<String> status = executorService.submit(callableTask);
        executorService.shutdown();
        Integer key = count.getAndIncrement();
        mapTask.put(key, executorService);
        statusMap.put(key, status);
        return key;
    }

    public void deleteScriptTask(Integer id) {
        ExecutorService task = mapTask.get(id);
        task.shutdownNow();
        mapTask.remove(id);
        statusMap.remove(id);
    }

}
