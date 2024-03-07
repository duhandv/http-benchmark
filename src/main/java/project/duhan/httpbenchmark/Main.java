package project.duhan.httpbenchmark;

import project.duhan.httpbenchmark.service.MeasureService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MeasureService measureService = new MeasureService();
        measureService.measureWithCallable();
    }
}
