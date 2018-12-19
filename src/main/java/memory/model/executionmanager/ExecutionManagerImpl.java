package memory.model.executionmanager;

public class ExecutionManagerImpl implements ExecutionManager {
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        return new ContextImpl(callback, tasks);
    }
}
