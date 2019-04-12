package shadowbuild.control.coroutine;

import shadowbuild.sprite.Sprite;


public class Task {
    private boolean active = true;
    TaskExecutor executor;

    public Task(TaskExecutor executor) {
        this.executor = executor;
    }

    public void stop() {
        active = false;
    }

    public boolean expired() {
        return !active;
    }

    public void run(Sprite sprite, int delta) {
        if (executor != null)
            executor.run(sprite, this, delta);
        else
            stop();
    }



}
