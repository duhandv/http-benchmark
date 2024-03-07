package project.duhan.httpbenchmark.service;

import java.util.Hashtable;
import java.util.Random;
import lombok.SneakyThrows;

public class Task implements Runnable {

    final String url;
    final String name;
    final Hashtable<String, Integer> map;

    public Task(String url, String name, Hashtable<String, Integer> map) {
        this.url = url;
        this.name = name;
        this.map = map;
    }

    @SneakyThrows
    @Override
    public void run() {
        // http call to get the response
        Thread.sleep(1000);
        map.put(name, new Random().nextInt());
    }
}
