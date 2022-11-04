/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */
package main;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Fireball extends Sprite{
    static BufferedImage[] imageCollection;
    int imageNumber;
    double vertVelocity;
    int airTimeFrames;
    boolean alive;
    Fireball(int x, int y) {
        super(x, y, 50 , 50);
        alive = true;
        if (imageCollection == null) {
            imageCollection = new BufferedImage[60];
            fileLoad(imageCollection, "resources/fireball/fireball");
            image = imageCollection[0];

        }
    }


    @Override
    public void isCollision(Sprite sprite) {
        if(sprite instanceof Skeleton && sprite.getBounds().intersects(getBounds()))
            alive = false;
    }

    @Override
    public void draw(Graphics g, int scrollPos) {
        g.drawImage(image, x - scrollPos, y, w, h, null);
    }

    @Override
    public boolean update() {
        if(w < 100) {
            w += 2;
            h += 2;
        }
        x+= 10;
        vertVelocity += 1.2;//Gravity always pulls down at 1.2 per tick
        y += vertVelocity;
        image = imageCollection[imageNumber++ % 60];
        if(y + h >= 590){//while sprite is on ground
            airTimeFrames = 0;
            vertVelocity = 0;
            y = 590-h; //snap back to ground
        }
        else
            airTimeFrames++;
        if(airTimeFrames < 15){
            vertVelocity -= 1.8;
        }
        return(alive);
    }

    @Override
    public void collisionHandler(Sprite sprite) {
    }
    @Override
    public String toString()
    {
        return "Fireball (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }
}
