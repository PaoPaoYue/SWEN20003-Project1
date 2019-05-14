package shadowbuild.sprite.constructable;

import shadowbuild.control.GameController;
import shadowbuild.control.SpritesController;
import shadowbuild.player.PlayerInfo;
import shadowbuild.sprite.Sprite;
import shadowbuild.util.Vector2;

public interface Constructable {

    ConstructMenu getConstructMenu();

    default boolean isConstructing(){
        return getConstructMenu().getConstructTarget() != null;
    }


    default void construct(int optionIndex, Vector2 pos){
        ConstructMenuItem constructTarget = getConstructMenu().getConstructMenuItem(optionIndex);
        if (constructTarget != null) {
            PlayerInfo playerInfo = GameController.getMainPlayer().getInfo();
            if(playerInfo.changeMetalAmount(constructTarget.getMetalCost()) &&
                    playerInfo.changeUnobtainiumAmount(constructTarget.getUnobtainiumCost())){
                getConstructMenu().setConstructTarget(constructTarget);
                ((Sprite)this).setTask((task, delta) ->
                    getConstructMenu().increaseProgress((double) delta/constructTarget.getConstructTime()),
                    constructTarget.getConstructTime());
                ((Sprite)this).addTask((task, coroutineDelta) -> {
                    try {
                        Sprite newSprite = constructTarget.getInstance();
                        newSprite.setPos(pos);
                        SpritesController.addSprite(newSprite);
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                    getConstructMenu().setConstructTarget(null);
                    task.stop();
                });
            }
        } else {
            System.out.println("ConstructError: construct menu option index out of bound!!!");
        }
    }

}
