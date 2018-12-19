package memory.model.executionmanager;

public class Main {

    public static void main(String[] args) {

        Runnable callback = new Runnable() {
            @Override
            public void run() {
                System.out.println("Call!");
            }
        };

        Runnable runnable1 = () -> System.out.println("Run1");

        Runnable runnable2 = () -> System.out.println("Run2");

        Runnable runnable3 = () -> System.out.println("Run3");

        Runnable runnable4 = () -> System.out.println("Run4");

        Runnable runnable5 = () -> System.out.println("Run5");


        ExecutionManager executionManager = new ExecutionManagerImpl();

        executionManager.execute(callback, runnable1, runnable2, runnable3, runnable4, runnable5);

    }

}