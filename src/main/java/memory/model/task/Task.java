package memory.model.task;

public interface Task<T> {
    T get() throws Exception;
}
