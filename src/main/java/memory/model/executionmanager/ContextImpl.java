package memory.model.executionmanager;

import java.util.ArrayList;
import java.util.List;

public class ContextImpl implements Context {

    private Runnable[] tasks;
    private Runnable callback;
    private List<Thread> threadList = new ArrayList<>();

    private int completed;
    private int failed;
    private int interrupted;

    public ContextImpl(Runnable callback, Runnable...tasks) {
        this.callback = callback;
        this.tasks = tasks;
        startThreads();
    }

    private void startThreads() {

        for (Runnable task : tasks) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if ( !Thread.currentThread().isInterrupted() ) {
                        try {
                            task.run();
                            synchronized (this) {
                                completed++;
                            }
                        } catch (Exception e) {
                            synchronized (this) {
                                failed++;
                            }
                        }
                    } else {
                        synchronized (this) {
                            interrupted++;
                        }
                    }

                    if (isFinished()) {
                        callback.run();
                    }
                }

            });
            threadList.add(thread);
            thread.start();
        }

    }

    @Override
    public int getCompletedTaskCount() {
        return completed;
    }

    @Override
    public int getFailedTaskCount() {
        return failed;
    }

    @Override
    public int getInterruptedTaskCount() {
        return interrupted;
    }

    @Override
    public void interrupt() {
        for (Thread thread : threadList) {
            thread.interrupt();
        }
    }

    @Override
    public synchronized boolean isFinished() {
        int done = getCompletedTaskCount() + getFailedTaskCount() + getInterruptedTaskCount();
        return (done == tasks.length);
    }
}
