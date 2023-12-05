package views;

import AdventureModel.AdventureGame;
import AdventureModel.boss.Boss;
import AdventureModel.boss.BossAttack;
import AdventureModel.boss.BossFight;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.sql.Time;
import java.util.NoSuchElementException;

public class BossFightView {
    AnchorPane bossFightView;
    Label bossFightLabel;
    VBox bossPane;
    ImageView characterSprite;
    ImageView bossSprite;
    AdventureGame model;
    BossFight bossFight;

    public BossFightView(AdventureGame model) {
        this.model = model;
        
        Image characeterImage = new Image("file:Assets/characterImages/" + this.model.player.character.title + ".png");
        characterSprite = new ImageView(characeterImage);
        characterSprite.setFitWidth(150);
        characterSprite.setPreserveRatio(true);

        Image bossImage = new Image("file:Assets/Boss.png");
        bossSprite = new ImageView(bossImage);
        bossSprite.setFitWidth(150);
        bossSprite.setPreserveRatio(true);

        bossFightView = new AnchorPane();

        AnchorPane.setBottomAnchor(characterSprite, 10.0);
        AnchorPane.setLeftAnchor(characterSprite, 10.0);
        AnchorPane.setTopAnchor(bossSprite, 10.0);
        AnchorPane.setRightAnchor(bossSprite, 10.0);

        bossFightView.getChildren().addAll(characterSprite, bossSprite);
        bossFightView.setPrefWidth(400);
        bossFightView.setPrefHeight(400);

        bossFightLabel = new Label();
        formatText("Boss fight started!!!!!");

        bossFightLabel.setPrefWidth(500);
        bossFightLabel.setPrefHeight(100);
        bossFightLabel.setTextOverrun(OverrunStyle.CLIP);
        bossFightLabel.setWrapText(true);
        bossPane = new VBox(bossFightView, bossFightLabel);
        bossPane.setAlignment(Pos.TOP_CENTER);
        bossPane.setStyle("-fx-background-color: #000000;");

        bossFight = new BossFight(new Boss(), this.model.player.character, this);
        bossFight.commandQueue.add(new BossAttack(bossFight, ""));
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(actionEvent -> {
            int result = this.bossFight.checkIfDone();
            if (result == 1) {
                this.model.gameState = null;
                this.model.after.isBlocked = false;
                this.model.movePlayer(this.model.after.getDirection());
                return;
            } else if (result == 2) {
                this.model.gameState = null;
                return;
            }
            try {
                this.updateView(this.bossFight.commandQueue.remove().execute());
                if (this.bossFight.commandQueue.isEmpty()) {
                }
            } catch (NoSuchElementException ignore) {
            }
            if (!this.bossFight.isPlayerTurn) {
                this.bossFight.commandQueue.add(new BossAttack(this.bossFight, ""));
                this.bossFight.isPlayerTurn = true;
            }
            pauseTransition.play();
        });
        pauseTransition.play();
    }

    private void updateView(String execute) {
        switch (execute) {
            case "PLAYER ATTACK FIREBALL" -> fireball();
            case "PLAYER ARMOUR UP" -> armourUp();
            case "PLAYER ATTACK " -> playerAttack();
            case "PLAYER HEAL" -> playerHeal();
            case "BOSS ATTACK " -> bossAttack();
            case "BOSS HEAL" -> bossHeal();
        }
    }

    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) bossFightLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else bossFightLabel.setText(roomDesc);
        } else bossFightLabel.setText(textToDisplay);
        bossFightLabel.setStyle("-fx-text-fill: white;");
        bossFightLabel.setFont(new Font("Arial", 16));
        bossFightLabel.setAlignment(Pos.CENTER);
    }

    public void fireball() {
        bossFightLabel.setText("You throw a fireball!");

        Image fireballImage = new Image("file:Assets/Fireball.png");

        ImageView fireballSprite = new ImageView(fireballImage);
        fireballSprite.setFitWidth(150);
        fireballSprite.setPreserveRatio(true);

        this.bossFightView.getChildren().add(fireballSprite);
        AnchorPane.setBottomAnchor(fireballSprite, 15.0);
        AnchorPane.setLeftAnchor(fireballSprite, 15.0);

        Path path = new Path();
        path.getElements().add(new MoveTo(100, 0));
        path.getElements().add(new LineTo(500, -200));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(fireballSprite);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setOnFinished(actionEvent -> this.bossFightView.getChildren().remove(fireballSprite));

        pathTransition.play();
    }

    public void armourUp() {
        bossFightLabel.setText("You get armoured up!");
    }

    public void playerAttack() {
        bossFightLabel.setText("You attack!");
    }

    public void playerHeal() {
        bossFightLabel.setText("You heal up!");
    }

    public void bossAttack() {
        bossFightLabel.setText("The boss attacks you!");
    }

    public void bossHeal() {
        bossFightLabel.setText("The boss heals up!");
    }
}
