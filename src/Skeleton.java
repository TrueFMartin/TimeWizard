import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Skeleton extends Sprite {
    static BufferedImage[] imageCollectionMove;
    static BufferedImage[] imageCollectionDead;

    int direction, prevX, prevY; //direction is either -1/0/1
    double vertVelocity;
    int imageNumber, imageNumberDead;
    boolean alive;

    Skeleton(int x, int y) {
        super(x, y, 90, 95);
        alive = true;
        direction = 1;

        if (imageCollectionMove == null) {
            imageCollectionMove = new BufferedImage[12];
            imageCollectionDead = new BufferedImage[13];
            fileLoad(imageCollectionMove, "skeleton/skeleton alive");
            fileLoad(imageCollectionDead, "skeleton/skeleton dead");
            image = imageCollectionMove[0];
        }
    }
    Skeleton(Json ob){
        this((int)ob.getLong("x"), (int)ob.getLong("y") );
    }


    @Override
    public void isCollision(Sprite sprite) {
        if(sprite instanceof Pipe && sprite.getBounds().intersects(getBounds()))
            collisionHandler(sprite);
        if(sprite instanceof Fireball && sprite.getBounds().intersects(getBounds()))
            alive = false;
    }

    @Override
    public void draw(Graphics g, int scrollPos) {
        if(direction != -1)
            g.drawImage(image, x - scrollPos, y, w, h, null);
        else
            g.drawImage(image, x - scrollPos + w, y, -w, h, null);
    }

    @Override
    public boolean update() {
        prevX = x;
        if (alive) {
            prevY = y;
            vertVelocity += 1.2;//Gravity always pulls down at 1.2 per tick
            y += vertVelocity;

            if (y >= 495) {//while sprite is on ground
                if (direction == 0) //sprite was falling, now on ground. Give direction of heading right
                    direction = 1;

                vertVelocity = 0.0;
                y = 495; //snap back to ground
            }
            x += direction * BASE_SPEED * .8;
            image = imageCollectionMove[imageNumber++ % 12];
            return true;
        }
        else{//skeleton has been hit with a fireball, countdown till dead and change image to dying image
            image = imageCollectionDead[deadImageHandler()];
            return(imageNumberDead < 30); //if we reach 13, return false and model's update should remove skeleton
        }
    }

    private int deadImageHandler(){
        if (imageNumberDead < 12)
            return imageNumberDead++;
        else {
            imageNumberDead++;
            return 12;
        }
    }
    @Override
    public void collisionHandler(Sprite sprite) {
        if(prevY < y && (x < sprite.x + sprite.w || x + w > sprite.w) && prevY + h <= sprite.y){
            vertVelocity = 0;
            y = sprite.y - h;
            if (direction == 0)
                direction = 1;
            return;
        }
        if(prevX < x){
            x=sprite.x - w;
            direction *= -1;
        }
        else if(prevX > x){
            x = sprite.x + sprite.w;
            direction *= -1;
        }

    }

    @Override
    public String toString() {
        return "Goomba (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }
}