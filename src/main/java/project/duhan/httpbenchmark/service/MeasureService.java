package project.duhan.httpbenchmark.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MeasureService {

    public void measure() throws InterruptedException {
        Hashtable<String, Integer> map = new Hashtable<>();
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(new Task("url1", "A", map));
        Thread t2 = new Thread(new Task("url2", "B", map));
        Thread t3 = new Thread(new Task("url3", "C", map));
        t1.start();
        t2.start();
        t3.start();
        // t1,t2,t3 스레드가 모두 종료되면 메인스레드를 실행, 확장성이 없음
        t1.join();
        t2.join();
        t3.join();
    }

    public void measureWithRunnableAndLatch() throws InterruptedException {
        Hashtable<String, Integer> map = new Hashtable<>();
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(3);
        Thread t1 = new Thread(new RunnableTask("url1", "A", map, latch));
        Thread t2 = new Thread(new RunnableTask("url2", "B", map, latch));
        Thread t3 = new Thread(new RunnableTask("url3", "C", map, latch));
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(t1);
        executorService.submit(t2);
        executorService.submit(t3);

        latch.await(2, TimeUnit.SECONDS); // latch가 0이 되면 메인스레드 실행 또는 timeout이 지나면 main스레드 실행
        executorService.shutdown(); // 스레드들을 순차적으로 종료

        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("total time: " + time);

        map.forEach((key, value) -> System.out.println(key + " " + value));
    }

    public void measureWithCallable() throws InterruptedException {
        long start = System.currentTimeMillis();
        CallableTask task1 = new CallableTask("url1", "A");
        CallableTask task2 = new CallableTask("url2", "B");
        CallableTask task3 = new CallableTask("url3", "C");
        ArrayList<CallableTask> tasks = new ArrayList<>();

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Integer>> results = executorService.invokeAll(tasks, 2, TimeUnit.SECONDS);
        results.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("total time: " + time);
    }
}
