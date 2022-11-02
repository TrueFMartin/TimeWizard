/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pipe extends Sprite{
     static BufferedImage image;
     Pipe(int x, int y){
         super(x, y, 60, 300);
         if(image == null){
             try {
                 image = ImageIO.read(returnImage("background/deadpipe.png"));
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
     }
     //Unmarshalling construct
    Pipe(Json ob){
         this((int)ob.getLong("x"), (int)ob.getLong("y"));
     }

//     boolean isPipePresent(int x, int y)
//     {  //Makes sure click cords. are inside of pipe
//         return(    (this.x <= x && this.x + w >= x) &&
//                    (this.y <= y && this.y + h >= y));
//     }
    //marshals to json


    @Override
    public void isCollision(Sprite sprite) {
    }
    @Override
    public void collisionHandler(Sprite sprite) {}

    @Override
     public boolean update(){
         return true;
     }
    @Override
    public void draw(Graphics g, int scrollPos){
         g.drawImage(image, x - scrollPos, y , w, h, null);
    }
    @Override
    public String toString()
    {
        return "Pipe (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }
//    public Rectangle getBounds() {
//        return new Rectangle(x, y, w, h);
//    }
}
