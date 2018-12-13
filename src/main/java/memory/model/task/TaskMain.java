package memory.model.task;

import java.util.concurrent.Callable;

public class TaskMain {

    public static void main(String[] args) throws Exception {
        Callable<Integer> callable = () -> {
            Thread.sleep(3000);
            //return null;
            throw new Exception();
        };

        Task<Integer> task = new TaskImpl<>(callable);


        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Integer integer = task.get();
                    System.out.println(Thread.currentThread().getName() + "\t" + integer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                });

            thread.start();



        }

    }
}
