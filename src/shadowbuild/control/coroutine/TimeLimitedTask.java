package shadowbuild.control.coroutine;

import shadowbuild.sprite.Sprite;

/**
 * This class defines a time limited coroutine task
 * The task will automatically stop if the time limit exceed
 * Not used in project1 but I think will be useful
 */
public class TimeLimitedTask extends Task{
    private int clock;

    public TimeLimitedTask(TaskExecutor executor, int time) {
        super(executor);
        this.clock = time;
    }

    private void consumeTime(int time) {
        clock -= time;
    }

    @Override
    public void run(Sprite sprite, int delta) {
        consumeTime(delta);
        if (executor != null)
            executor.run(this, delta);
        if(clock <= 0) stop();
    }
}
