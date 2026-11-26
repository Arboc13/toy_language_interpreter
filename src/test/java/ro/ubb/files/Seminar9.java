package ro.ubb.files;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Seminar9 {

    List<String> texts = List.of(
            "the stream is lazy and the stream is consumed once",
            "a thread pool reuses its threads",
            "the executor runs the tasks of the pool",
            "virtual threads are cheap");

    @Test
    void testproblem2(){
        Map<String, Integer> counts = new HashMap<>(); // shared by all tasks
        // if we use COncurrentHashMap, it still doesn't save us, cause we could be doing get at the same time
        // and fuck up our count
        int[] words = {0};
        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            for (int round = 0; round < 1000; round++)
                for (String text : texts)
                    executor.submit(() -> {
                        for (String w : text.split("\\s+")) {
                            Integer old = counts.get(w);
                            counts.put(w, old == null ? 1 : old + 1);
                            words[0]++;
                        }
                    });
        }
        // fine cause try closes the threads as well, so we know they finished
        System.out.println(counts.get("the") + " " + words[0]);
    }


    @Test
    void testProblem3()  {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        final int POISON = -1;
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.add(i);
                }
                queue.add(POISON);
                } catch (Exception e){throw new RuntimeException(e);}
        });
        Thread consumer = new Thread(() -> {
            int sum = 0;
            while(!queue.isEmpty()){
                try {
                    int elem = queue.take();
                    if (elem != POISON)
                        sum += elem;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            IO.println(sum);
            // TODO: take until POISON, add up the numbers, print the sum
        });
        producer.start(); consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
