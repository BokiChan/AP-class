package se233.chapter4.medel;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.view.GameStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameCharacter extends Pane {
    private static final Logger logger = LogManager.getLogger(GameCharacter.class);
    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;
    private Image gameCharacterImg;

    public AnimatedSprite getImageView() {
        return ImageView;
    }

    public void setImageView(AnimatedSprite imageView) { ImageView = imageView; }

    private AnimatedSprite ImageView;
    private AnimatedSprite ImageView2;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int xVelocity = 0;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    int yVelocity = 0;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity;
    int yMaxVelocity;

    public KeyCode getLeftKey() {
        return leftKey;
    }
    public KeyCode getRightKey() {
        return rightKey;
    }
    public KeyCode getUpKey() {
        return upKey;
    }
    public GameCharacter(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey , String ImagePath, int xMaxVelocity, int yMaxVelocity, int count, int columns, int rows, int width, int height) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.xMaxVelocity = xMaxVelocity;
        this.yMaxVelocity = yMaxVelocity;

        this.gameCharacterImg = new Image(Launcher.class.getResourceAsStream(ImagePath));
        this.ImageView = new AnimatedSprite(gameCharacterImg, count, columns, rows, offsetX, offsetY, width, height);
        this.ImageView.setFitWidth(CHARACTER_WIDTH);
        this.ImageView.setFitHeight(CHARACTER_HEIGHT);

        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;

        this.getChildren().addAll(this.ImageView);
    }
    public void moveX(){
        setTranslateX(x);
        if(isMoveLeft){
            xVelocity = xVelocity>=xMaxVelocity? xMaxVelocity:xVelocity+xAcceleration;
            x=x - xVelocity;}
        if(isMoveRight){
            xVelocity = xVelocity>=xMaxVelocity? xMaxVelocity:xVelocity+xAcceleration;
            x=x + xVelocity;}
    }
    public void moveY() {
        setTranslateY(y);
        if(isFalling){
            yVelocity = yVelocity>=yMaxVelocity? yMaxVelocity:yVelocity+yAcceleration;
            y = y + yVelocity;
        } else if(isJumping){
            yVelocity = yVelocity <= 0? 0:yVelocity-yAcceleration;
            y = y - yVelocity;
        }
    }
    public void checkReachGameWall() {
        if (x <= 0){
            x = 0;
            logger.debug("Character {} hit left wall.");
        }else if( x+getWidth() >= GameStage.WIDTH){
            x = GameStage.WIDTH-(int)getWidth();
            logger.debug("Character {} hit left wall.");
        }
    }
    public void jump() {
        if(canJump){
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }
    public void checkReachHighest(){
        if(isJumping && yVelocity <= 0){
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }
    public void checkReachFloor(){
        if(isFalling && y >= GameStage.GROUND - CHARACTER_HEIGHT){
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }
    public void repaint() {
        moveX();
        moveY();
    }
    public void moveLeft() {
        setScaleX(-1);
        isMoveLeft = true;
        isMoveRight = false;
    }
    public void moveRight() {
        setScaleX(1);
        isMoveLeft = false;
        isMoveRight = true;
    }
    public void stop(){
        isMoveLeft = false;
        isMoveRight = false;
    }
    public void trace(){
        logger.info("x:%d y:%d vx:%d vy:%d",x,y,xVelocity,yVelocity);
    }

    public boolean collided(GameCharacter c) {
        if (this.getBoundsInParent().intersects(c.getBoundsInParent())) {
            logger.debug("Collision detected between characters!");
            return true;
        }
        return false;
    }
}
