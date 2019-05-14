package shadowbuild.sprite.constructable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ConstructMenu implements Iterable<ConstructMenuItem>{
    private List<ConstructMenuItem> menuItemList = new ArrayList<>();
    private ConstructMenuItem constructTarget;
    private double progress;

    public ConstructMenu(ConstructMenuItem... items) {
        menuItemList.addAll(Arrays.asList(items));
    }

    public ConstructMenuItem getConstructMenuItem(int index) {
        try {
            return menuItemList.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void addMenuItem(ConstructMenuItem item) {
        menuItemList.add(item);
    }

    public void removeMenuItem(int index) {
        menuItemList.remove(index);
    }

    public ConstructMenuItem getConstructTarget() {
        return constructTarget;
    }

    public void setConstructTarget(ConstructMenuItem constructTarget) {
        this.constructTarget = constructTarget;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void increaseProgress(double delta) {
        progress += delta;
        if(progress > 1) progress = 1;
        else if(progress < 0) progress = 0;
    }

    public int size() {
        return menuItemList.size();
    }

    @Override
    public Iterator<ConstructMenuItem> iterator() {
        return menuItemList.iterator();
    }
}
