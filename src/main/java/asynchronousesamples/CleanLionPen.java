package main.java.asynchronousesamples;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

public class CleanLionPen {

    public void cleanPen(CyclicBarrier c1, CyclicBarrier c2) {
        try {
            System.out.println("Remove lions!");
            c1.await();
            System.out.println("Clean pens!");
            c2.await();
            System.out.println("Roaming lions");
        } catch (BrokenBarrierException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var service = Executors.newFixedThreadPool(4);
        var manage = new CleanLionPen();
        try {
            var c1 = new CyclicBarrier(4);
            var c2 = new CyclicBarrier(4, () -> System.out.println("Cleaning finished!"));
            for (var i = 0; i < 4; i++) {
                service.submit(() -> manage.cleanPen(c1, c2));
            }
        } finally {
            service.shutdown();
        }
    }
}
