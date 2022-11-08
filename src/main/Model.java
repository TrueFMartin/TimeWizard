/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */

package main;

import java.io.*;
import java.util.ArrayList;
public class Model {

    int clickX;
    int clickY;
    ArrayList<Sprite> sprites;
    Wizard wizard;

    Model() {
        sprites = new ArrayList<>();
        wizard = new Wizard(100, 495); //set wizard// start pos.
        sprites.add(wizard);
    }

    public void update() { //calls methods that are not trigger by key press, ran continually
        sprites.removeIf(sprite -> !sprite.update()); //called a "collection update" using predicate
        // Remove sprite if update fails
        sprites.removeIf(fireball -> fireball instanceof Fireball && (Math.abs(fireball.x - wizard.x) > Game.SCREEN_W)); //fireball disappears if it gets further than 700 from wizard
        CollisionDetector();
    }

    public void CollisionDetector() { //constantly checks if wizard// is in contact with pipe
        for (Sprite outerSprite : sprites) {
            for (Sprite innerSprite : sprites) { //as long as there is another pipe in array, send pipe to wizard// to check for collision
                outerSprite.isCollision(innerSprite);
            }
        }
    }

    public void noAnimation() {
        wizard.noAnimation(); //no movement of wizard// , set wizard
        //to stand still image
    }

    public void updateWizardForward() {// move wizard// to right and update image
        wizard.imageStateForward();
    }

    public void updateWizardBackward() {// move wizard// to left and update image
        wizard.imageStateBackward();
    }

    public void addPipe(int pipeW, int pipeH) //user uses mouse click. Will either add/remove pipe updating model
    {
        for (int i = 1; i < sprites.size(); i++) { //run through all sprites
            if (sprites.get(i) instanceof Pipe && isSpritePresent(sprites.get(i))) { //if that sprite is a pipe and it intersects with x,y click
                sprites.remove(i); //remove that pipe
                return;
            }
        }
        //pipe is not present

        sprites.add(new Pipe(clickX, clickY, pipeW, pipeH));
    }

    //Makes sure click cords. are inside of pipe
    boolean isSpritePresent(Sprite sprite) {
        return ((sprite.x <= this.clickX && sprite.x + sprite.w >= clickX) &&
                (sprite.y <= clickY && sprite.y + sprite.h >= clickY));
    }

    public void wizardJump() {
        wizard.jump(); //space bar is pressed, have wizard// jump
    }

    public void setXY(int x, int y) //set x y cords. X is affected by view's scrollPos
    {
        clickX = x;
        clickY = y;
    }

    public void shootFireball() {
        if (wizard.canCastSpell())
            sprites.add(new Fireball(wizard.x + wizard.w, wizard.y + wizard.h / 2));
    }

    public void addSkeleton() {
        sprites.add(new Skeleton(clickX, clickY));
    }

    public void restartWizard() {
        wizard = new Wizard(200, 495);
        sprites.set(0, wizard);
    }

    public void saveSerial() {
        System.out.println("Saving..");
        try {
            FileOutputStream file = new FileOutputStream("resources/map/map.ser");
            ObjectOutputStream outObj = new ObjectOutputStream(file);
            outObj.writeObject(sprites);
            file.close();
            outObj.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
@SuppressWarnings("unchecked")
    public void loadSerial() {
        System.out.println("Loading...");
//-----------intialize temp objects to load static images that serializer cant---------
        Skeleton tempSkel = new Skeleton(0,0);
        Pipe tempPipe = new Pipe(0, 0,60,180);
        Wizard tempWiz = new Wizard(0,600);
        Fireball tempFire = new Fireball(0,0);
        try {
//            FileInputStream file = new FileInputStream("resources/map/map.ser");
            InputStream file = Model.class.getClassLoader().getResourceAsStream("resources/map/map.ser");
            ObjectInputStream inObj = new ObjectInputStream(file);
            Object readObj = inObj.readObject();
            sprites = (ArrayList<Sprite>) readObj;
            inObj.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
            wizard = (Wizard) sprites.get(0);
    }
}
