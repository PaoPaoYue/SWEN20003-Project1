package shadowbuild.control.coroutine;

import shadowbuild.sprite.Sprite;

/**
 * Define the callback function of the task
 * Can use a lambda function to represent it
 */
@FunctionalInterface
public interface TaskExecutor {

    void run(Sprite sprite, Task task, int delta);
}
