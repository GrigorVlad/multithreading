package memory.model.task;

import java.util.concurrent.Callable;

public class TaskImpl<T> implements Task<T> {

    private final Callable<T> callable;
    private volatile T result; //volatile necessary
    private boolean calculated = false;
    private volatile Exception exception;

    public TaskImpl(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public T get() throws Exception {

        T temp = result;

        if (!calculated) {
            synchronized (this) {
                if (!calculated) {
                    try {
                        result = callable.call();
                        System.out.println("Call!!!");
                    } catch (Exception e) {
                        exception = e;
                    }
                    calculated = true;
                }
                temp = result;

            }
        }

        if (exception != null)
        {
            throw exception;
        }

        return result;

    }
}
