/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */


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


    public void marshal(){

        System.out.println("Saving game...");
        Json ob = Json.newObject();
        Json wizardLocation = Json.newList();
        Json pipeList = Json.newList();
        Json SkeletonList = Json.newList();
        for (Sprite sprite : sprites) { //write all pipes onto json object's list
            if(sprite instanceof Wizard)
                wizardLocation.add(sprite.marshal());
            if(sprite instanceof Pipe)
                pipeList.add(sprite.marshal());
            if(sprite instanceof Skeleton)
                SkeletonList.add(sprite.marshal());
        }
        ob.add("Wizard", wizardLocation);
        ob.add("Pipes", pipeList);
        ob.add("Skeletons", SkeletonList);

        ob.save("map\\map.json"); //file name
    }

    public void unmarshal(){// load model from Json file
        sprites = new ArrayList<>(); //allocates 'pipes' to new memory
        Json ob = Json.load("map\\map.json"); //J object will be node from file
        Json pipeList = ob.get("Pipes"); //create list from Map in json
        Json skeletonList = ob.get("Skeletons");
        Json wizardLocation = ob.get("Wizard");
        sprites.add(new Wizard(wizardLocation.get(0)));
        wizard = (Wizard)sprites.get(0);
        for(int i = 0; i < pipeList.size(); i++)
            sprites.add(new Pipe(pipeList.get(i))); //with new pipes array, fill from json temp list

        for(int i = 0; i < skeletonList.size(); i++)
            sprites.add(new Skeleton(skeletonList.get(i)));

    }
    public void update(){ //calls methods that are not trigger by key press, ran continually
        sprites.removeIf(sprite -> !sprite.update()); //called a "collection update" using predicate
        // Remove sprite if update fails
        sprites.removeIf(fireball -> fireball instanceof Fireball && (Math.abs(fireball.x - wizard.x) > Game.SCREEN_W)); //fireball disappears if it gets further than 700 from wizard
        CollisionDetector();
    }

    public void CollisionDetector(){ //constantly checks if wizard// is in contact with pipe
        for(Sprite outerSprite : sprites) {
            for (Sprite innerSprite : sprites) { //as long as there is another pipe in array, send pipe to wizard// to check for collision
                outerSprite.isCollision(innerSprite);
            }
        }
    }

    public void noAnimation(){
        wizard.noAnimation(); //no movement of wizard// , set wizard
        //to stand still image
    }
    public void updateWizardForward() {// move wizard// to right and update image
        wizard.imageStateForward();
    }
    public void updateWizardBackward(){// move wizard// to left and update image
        wizard.imageStateBackward();
    }
    public void addPipe() //user uses mouse click. Will either add/remove pipe updating model
    {
        for(int i = 1; i < sprites.size(); i++){ //run through all sprites
            if (sprites.get(i) instanceof Pipe && isSpritePresent(sprites.get(i))) { //if that sprite is a pipe and it intersects with x,y click
                sprites.remove(i); //remove that pipe
                return;
            }
        }
        //pipe is not present

        sprites.add(new Pipe(clickX, clickY));
    }
    //Makes sure click cords. are inside of pipe
    boolean isSpritePresent(Sprite sprite)
    {
        return(    (sprite.x <= this.clickX && sprite.x + sprite.w >= clickX) &&
                (sprite.y <= clickY && sprite.y + sprite.h >= clickY));
    }
    public void wizardJump(){
        wizard.jump(); //space bar is pressed, have wizard// jump
    }
    public void setXY(int x, int y) //set x y cords. X is affected by view's scrollPos
    {
        clickX = x;
        clickY = y;
    }

    public void shootFireball() {
        if(wizard.prevX <= wizard.x)
            sprites.add(new Fireball(wizard.x + wizard.w, wizard.y + wizard.h/2, 1));
        else
            sprites.add(new Fireball(wizard.x - 50, wizard.y + wizard.h/2, -1));
    }

    public void addSkeleton() {
        sprites.add(new Skeleton(clickX, clickY));
    }

    public void restartWizard() {
        wizard.x = 200;
    }
}
