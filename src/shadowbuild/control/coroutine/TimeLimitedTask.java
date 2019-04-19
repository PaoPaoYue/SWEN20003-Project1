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
        setClock(time);
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock >= 0 ? clock: 0;
    }

    public void consumeClock(int time) {
        clock -= time;
    }

    @Override
    public void run(Sprite sprite, int delta) {
        consumeClock(delta);
        if (executor != null)
            executor.run(sprite, this, delta);
        if(getClock() <= 0) stop();
    }
}
