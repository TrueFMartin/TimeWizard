/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */
package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Wizard extends Sprite{
    static BufferedImage[] imageCollectionForward; //array of images for wizard moving towards right
    static BufferedImage[] imageCollectionBackward;// array of images for wizard moving towards left
    static BufferedImage[] imageCollectionJump;// array of images for wizard jumping
    int health, mana;
    public final static int MANA_MAX = 200;
    public final static int HEALTH_MAX = 5;
    public final static int SPELL_COST = 60;
    //int x,y,w,h; //position and dimensions of wizard
    double vertVelocity; //amount of pull against gravity

    int imageNumberForward, imageNumberBackward, imageNumberJump, imageNumberInvincible; //keeps track of which image is in use in forward/backward/jump array

    int prevX, prevY; //wizard's previous position, used in determine collision direction
    int airTimeFrames, invincibilityFrames;//frame counters for stats

    boolean isInvincible;

    Wizard(int x, int y){
        super(x, y, 60, 95);
        health = 5;
        mana = 5;
        if(imageCollectionBackward == null){

            imageCollectionForward = new BufferedImage[8];
            imageCollectionBackward = new BufferedImage[16];
            imageCollectionJump = new BufferedImage[17];
            fileLoad(imageCollectionForward, "resources/wizard/wizard");
            fileLoad(imageCollectionBackward, "resources/wizard/backward");
            fileLoad(imageCollectionJump, "resources/wizard/jump");
            image = imageCollectionForward[0]; //image will start with default moving forward image
        }
    }
    Wizard(Json ob){
        this((int)ob.getLong("x"), (int)ob.getLong("y"));
    }
    @Override
    public void draw(Graphics g, int scrollPos) {
        g.drawImage(image, x - scrollPos, y, w, h, null);
    }

    @Override
    public boolean update() {
        updateInvincabilty();
        updateGravity();
        updateMana();

        return (health > 0);
    }
    void updateInvincabilty(){
        if(isInvincible){
            invincibilityFrames++;
            if(++imageNumberInvincible % 4 == 0)
                image = imageCollectionJump[11];
            else
                image = imageCollectionJump[10];
        }
        if(invincibilityFrames > 35) {
            isInvincible = false;
            invincibilityFrames = 0;
        }
    }
    void updateGravity() {
        prevY = y;
        vertVelocity += 1.2;//Gravity always pulls down at 1.2 per tick
        y += vertVelocity;

        if (y >= 495) {//while wizard is on ground
            imageNumberJump = 0;
            vertVelocity = 0.0;
            y = 495; //snap back to ground
            airTimeFrames = 0;
        } else {//since not on ground, increase counter of air time
            airTimeFrames++;
        }
    }
    void updateMana(){
        if(mana < MANA_MAX)
            mana++;
    }

    @Override
    public void isCollision(Sprite sprite){//returns true if wizard intersects with a pipe
        if(sprite instanceof Pipe && sprite.getBounds().intersects(getBounds()))  //creates a rectangle at pipe and wizard location, if there is an intersection return true
            collisionHandler(sprite);
        if(sprite instanceof Skeleton && sprite.getBounds().intersects(getBounds())) {
            if (!isInvincible){ //reduce health if contact is made and not invincible
                isInvincible = true;
                health--;
            }
        }

    }
    @Override
    public void collisionHandler(Sprite sprite) {
        if(prevY < y && (x < sprite.x + sprite.w || x + w > sprite.w) && prevY + h <= sprite.y){
            vertVelocity = 0;
            y = sprite.y - h;
            airTimeFrames = 0;
            return;
        }
        else if(prevY > y && (x < sprite.x + sprite.w || x + w > sprite.w) && prevY*1.02 >= sprite.y + sprite.h){
            vertVelocity = 0;
            y = sprite.y + sprite.h;
            return;
        }
        if(prevX < x){
            x=sprite.x - w;
        }
        else if(prevX > x && !(x < sprite.x)){
            x = sprite.x + sprite.w;
        }
    }

    @Override
    protected void preLoadStaticImages() {
        if(imageCollectionBackward == null){

            imageCollectionForward = new BufferedImage[8];
            imageCollectionBackward = new BufferedImage[16];
            imageCollectionJump = new BufferedImage[17];
            fileLoad(imageCollectionForward, "resources/wizard/wizard");
            fileLoad(imageCollectionBackward, "resources/wizard/backward");
            fileLoad(imageCollectionJump, "resources/wizard/jump");
            image = imageCollectionForward[0]; //image will start with default moving forward image
        }
    }

    void imageStateForward(){ //User is pressing right arrow,
        BASE_SPEED = FOWARD_SPEED;
        imageNumberBackward = 0;//resets the counter for the BACKWARDS image array
        image = imageCollectionForward[(imageNumberForward++ % 14) / 2]; //cycle through the FORWARD image array, assigning currentImage
        prevX = x;//stores previous X value before updating
        x += BASE_SPEED*1.3;
    }

    void imageStateBackward(){//User is pressing left arrow key
        BASE_SPEED = REVERSE_SPEED;
        imageNumberForward = 0;//resets the counter on the FORWARD image array
        image = imageCollectionBackward[(imageNumberBackward++ % 32) / 2]; //cycles through the BACKWARD image array, assigning currentImage
        prevX = x;
        x += BASE_SPEED;
    }
    void noAnimation(){//called when no movement input is given.
        BASE_SPEED = FOWARD_SPEED;
        image = imageCollectionForward[0];   //This is because backwards movement is an animated ghost,
                                                    // and it looks wierd if the user stops moving while heading backwards
                                                    //This way wizard goes back to corporeal from if standing still.
    }
    void jump(){
        image = imageCollectionJump[(imageNumberJump++ % 34) / 2]; //transition image through the jump image array

        if(airTimeFrames < 3){ //For intial part of jump, high speed
            vertVelocity -= 5;
            y += vertVelocity;
        }
        else if(airTimeFrames < 6){ //slow last part of jump to seem more natural
            vertVelocity -= 3;
            y += vertVelocity;
        }
    }
//-------------Compares spell cost/remaining mana-------------
// ------------If castable, reduce mana, return true----------
    boolean canCastSpell(){
        if(mana > SPELL_COST) {
            mana -= SPELL_COST;
            return true;
        }
        return false;
    }
    @Override
    public String toString()
    {
        return "wizard (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }
}

