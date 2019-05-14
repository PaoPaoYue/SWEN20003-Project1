package shadowbuild.control.coroutine;

/**
 * Define the callback function of the task
 * Can use a lambda function to represent it
 */
@FunctionalInterface
public interface TaskExecutor {

    void run(Task task, int delta);
}
