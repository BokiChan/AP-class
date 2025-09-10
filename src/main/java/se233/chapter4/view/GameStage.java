package se233.chapter4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.medel.GameCharacter;
import se233.chapter4.medel.Keys;

public class GameStage extends Pane {
    public static  final int WIDTH = 800;
    public static  final int HEIGHT = 400;
    public static  final int GROUND = 300;
    private Image gameStageImg;
    private GameCharacter gameCharacterMario;
    private GameCharacter gameCharacterrockman;
    private Keys keys;
    public GameStage(){
        keys = new Keys();
        gameStageImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitWidth(WIDTH);
        backgroundImg.setFitHeight(HEIGHT);
        gameCharacterMario = new GameCharacter(30 ,30,0,0, KeyCode.A, KeyCode.D, KeyCode.W , "assets/MarioSheet.png", 7, 17, 4, 4, 1, 16, 32);
        gameCharacterrockman = new GameCharacter(100 ,30,0,0, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP , "assets/rockmannew.png", 10, 20 , 5, 5, 2, 25, 50);
        getChildren().addAll(backgroundImg, gameCharacterMario,gameCharacterrockman);
    }
    public GameCharacter getGameCharacterMario() {
        return gameCharacterMario;
    }
    public GameCharacter getGameCharacterrockman() {return gameCharacterrockman; }
    public Keys getKeys() {
        return keys;
    }
}
