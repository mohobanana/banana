package org.banana.futurePromise;

import java.util.concurrent.*;

/**
 * Desc: TestJdkFuture
 * Created by mskj-mohao on 2021/9/24 3:12 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<Long> fu = service.submit(() -> System.currentTimeMillis());
        System.out.println(fu.get());
        service.shutdown();
    }
}
