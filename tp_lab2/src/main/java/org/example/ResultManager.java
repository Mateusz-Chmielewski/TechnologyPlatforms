package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class ResultManager {

    private Queue<String> results = new LinkedList<>();

    public synchronized String getResult() throws InterruptedException {
        if (results.isEmpty()) {
            wait();
        }

        return results.poll();
    }

    public synchronized void addResult(String result) {
        results.add(result);
        notify();
    }
}
