/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */
package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;

public class Pipe extends Sprite{
    @Serial
    private static final long serialVersionUID = 1234567L;

    static BufferedImage image;
     Pipe(int x, int y, int w, int h){
         super(x, y, w, h);
         if(image == null){
             try {
                 image = ImageIO.read(returnImage("resources/background/deadpipe.png"));
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
     }
     //Unmarshalling construct
//    Pipe(Json ob){
//         this((int)ob.getLong("x"), (int)ob.getLong("y"));
//     }

    @Override
    public void isCollision(Sprite sprite) {
    }
    @Override
    public void collisionHandler(Sprite sprite) {}

    @Override
    protected void preLoadStaticImages() {
        if(image == null){
            try {
                image = ImageIO.read(returnImage("resources/background/deadpipe.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
     public boolean update(){
         return true;
     }
    @Override
    public void draw(Graphics g, int scrollPos, int vertScrollPos){
         g.drawImage(image, x - scrollPos, y - vertScrollPos, w, h, null);
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
