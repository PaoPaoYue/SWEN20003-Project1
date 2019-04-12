package shadowbuild.control.coroutine;

import shadowbuild.sprite.Sprite;

@FunctionalInterface
public interface TaskExecutor {

    void run(Sprite sprite, Task task, int delta);
}
