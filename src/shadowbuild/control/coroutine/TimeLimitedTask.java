package shadowbuild.control.coroutine;

import shadowbuild.sprite.Sprite;

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
