package project.duhan.httpbenchmark.service;

import java.util.Random;
import java.util.concurrent.Callable;

public class CallableTask implements Callable<Integer> {
    final String url;
    final String name;

    public CallableTask(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return new Random().nextInt();
    }
}
