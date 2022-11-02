/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class Sprite {
    int x,y,w,h;
    static int BASE_SPEED = 5;
    final static int FOWARD_SPEED = 5;
    final static int REVERSE_SPEED = -3;
    BufferedImage image;
    Sprite(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    //--------------Load X Y cords from Json file----------
    Sprite(Json ob){

        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
    }
    //--------------Save X Y cords to Json file----------
    public Json marshal(){
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }
    //--------------Loop to add image number and prefix, turn into stream, and add to array---------
    void fileLoad(BufferedImage[] imageArray, String filePrefix){
        try {
            for (int i = 0; i < imageArray.length; i++) {
                int fileNumber = 1 + i;
                imageArray[i] = ImageIO.read(returnImage(filePrefix + " (" + fileNumber+ ").png"));
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //---------------Used to translate image file to stream----------------
    public InputStream returnImage(String imageLocation){
        return Sprite.class.getClassLoader().getResourceAsStream(imageLocation);
    }
//--------------Use if we don't want to convert images to stream------------------
//    BufferedImage loadImageToArray(String fileName, int imageArrayPos) throws IOException {
//        return (ImageIO.read(returnImage(fileName + imageArrayPos + ".png")));
//    }
//---------------------------------------------------------------------------------
    protected Rectangle getBounds() {
        return new Rectangle(x, y, w, h); //Creates a rectangle object of sprite with its position and dimensions
    }
    protected abstract void isCollision(Sprite sprite);
    protected abstract void draw(Graphics g, int scrollPos);
    protected abstract boolean update();
    protected abstract void collisionHandler(Sprite sprite);

}


