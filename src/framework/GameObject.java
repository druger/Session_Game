package framework;

import java.awt.*;
import java.util.List;

public abstract class GameObject {
    
    protected float x;
    protected float y;
    protected ObjectId id;
    protected float velX = 0;
    protected float velY = 0;
    protected boolean falling = true;
    protected boolean jumping;
    protected int direction = 1; //1=right; -1=left
    
    public GameObject(float x, float y, ObjectId id){
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    public abstract void tick(List<GameObject> object);
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();
    
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    
    public float getVelX(){
        return velX;
    }
    public float getVelY(){
        return velY;
    }
    public void setVelX(float velX){
        this.velX = velX;
    }
    public void setVelY(float velY){
        this.velY = velY;
    }
    
    public ObjectId getId(){
        return id;
    }
     public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    
    public int getDirection(){
        return direction;
    }
}
