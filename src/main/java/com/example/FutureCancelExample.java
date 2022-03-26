package com.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCancelExample {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    Future<String> future =
        executorService.submit(
            () -> {
              Thread.sleep(2000);
              return "Hello from Callable";
            });
    int i = 0;
    while (!future.isDone()) {
      System.out.println("Task is still not done...");
      Thread.sleep(200);
      if (i > 0) {
        System.out.println("Trying to cancel the future....");
        future.cancel(true);
      }
      i = i + 1;
    }
    if (future.isCancelled()) {
      System.out.println("Future is cancelled");
    } else {
      System.out.println("Future is not cancelled");
      String result = future.get();
      System.out.println(result);
    }
    executorService.shutdown();
  }
}
