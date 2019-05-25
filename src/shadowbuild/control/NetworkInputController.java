package shadowbuild.control;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NetworkInputController {

    private static final int TIME_COUNT = 1000000;

    private boolean preInit = true;
    private Input input;
    private int timeStamp;
    private Queue<Input> inputQueue;
    private Queue<Input> networkInputQueue;

    public NetworkInputController() {
        input = Input.NO_INPUT;
        inputQueue = new LinkedList<>();
        networkInputQueue = new LinkedList<>();
    }

    public void update(int delta) {
        Input input = inputQueue.peek();
        if(input == null || (input.getTimeStamp() < timeStamp && input.getTimeStamp() > timeStamp - 1000)) {
            this.input = Input.NO_INPUT;
        } else {
            this.input = inputQueue.poll();
        }
        if (!preInit) {
            timeStamp += delta;
            timeStamp %= TIME_COUNT;
        }
    }


    public Input getInput() {
        return  this.input;
    }

    public List<Input> getInputQueue() {
        List<Input> res = (LinkedList)networkInputQueue;
        networkInputQueue = new LinkedList<>();
        return res;
    }

    public void addInputQueue(List<Input> inputList) {
        if (preInit) {
            timeStamp = input.getTimeStamp();
            preInit = false;
        }
        inputQueue.addAll(inputList);
        networkInputQueue.addAll(inputList);
    }

}
