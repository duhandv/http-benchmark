package project.duhan.httpbenchmark.service;

import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import lombok.SneakyThrows;

public class RunnableTask implements Runnable {

    final String url;
    final String name;
    final Hashtable<String, Integer> map;
    final CountDownLatch latch;

    public RunnableTask(String url, String name, Hashtable<String, Integer> map, CountDownLatch latch) {
        this.url = url;
        this.name = name;
        this.map = map;
        this.latch = latch;
    }

    @SneakyThrows
    @Override
    public void run() {
        // http call to get the response
        Thread.sleep(1000);
        System.out.println(name + " RunnableTask.run");
        map.put(name, new Random().nextInt());
        latch.countDown();
    }
}
