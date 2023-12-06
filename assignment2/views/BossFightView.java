package views;

import AdventureModel.AdventureGame;
import AdventureModel.Room;
import AdventureModel.boss.Boss;
import AdventureModel.boss.BossAttack;
import AdventureModel.boss.BossFight;
import AdventureModel.boss.BossHeal;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.Serializable;
import java.sql.Time;
import java.util.NoSuchElementException;

/**
 * Class BossFightView
 *
 * Holds the viewing elements of the BossFight.
 */
public class BossFightView {

    private AnchorPane bossFightView;
    public Label bossFightLabel;
    public VBox bossPane;
    private ImageView characterSprite;
    private ImageView bossSprite;
    private AdventureGame model;
    private AdventureGameView view;
    public BossFight bossFight;
    private Label hpLabel;
    private ProgressBar healthBar;
    private Label bossHPLabel;
    private ProgressBar bossHealthBar;

    /**
     * Initialize a new boss fight view, with a new boss fight.
     * @param view the current AdventureGameView
     * @param model the AdventureGame model of the current game
     */
    public BossFightView(AdventureGameView view, AdventureGame model) {
        this.model = model;
        
        Image characeterImage = new Image("Assets/characterImages/" + this.model.player.character.title + ".png");
        characterSprite = new ImageView(characeterImage);
        characterSprite.setFitWidth(150);
        characterSprite.setPreserveRatio(true);

        Image bossImage = new Image("Assets/Boss.png");
        bossSprite = new ImageView(bossImage);
        bossSprite.setFitWidth(150);
        bossSprite.setPreserveRatio(true);

        bossFightView = new AnchorPane();

        AnchorPane.setBottomAnchor(characterSprite, 10.0);
        AnchorPane.setLeftAnchor(characterSprite, 10.0);
        AnchorPane.setTopAnchor(bossSprite, 50.0);
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


        //Health bar
        healthBar = new ProgressBar();
        healthBar.setMaxWidth(Double.MAX_VALUE);

        hpLabel = new Label();
        hpLabel.setStyle("-fx-text-fill: white;");
        hpLabel.setFont(new Font("Arial", 16));

        HBox healthView = new HBox();
        healthView.setStyle("-fx-background-color: #000000;");
        healthView.setPadding(new Insets(20, 20, 20, 20));
        healthView.getChildren().addAll(healthBar, hpLabel);
        HBox.setHgrow(healthBar, Priority.ALWAYS);
        healthView.setSpacing(10);

        AnchorPane.setBottomAnchor(healthView, 0.0);
        AnchorPane.setLeftAnchor(healthView, 0.0);
        bossFightView.getChildren().add(healthView);


        //Boss health bar
        bossHealthBar = new ProgressBar();
        bossHealthBar.setMaxWidth(Double.MAX_VALUE);

        bossHPLabel = new Label();
        bossHPLabel.setStyle("-fx-text-fill: white;");
        bossHPLabel.setFont(new Font("Arial", 16));

        HBox bossHealthView = new HBox();
        bossHealthView.setStyle("-fx-background-color: #000000;");
        bossHealthView.setPadding(new Insets(20, 20, 20, 20));
        bossHealthView.getChildren().addAll(bossHealthBar, bossHPLabel);
        HBox.setHgrow(bossHealthBar, Priority.ALWAYS);
        bossHealthView.setSpacing(10);

        AnchorPane.setTopAnchor(bossHealthView, 0.0);
        AnchorPane.setRightAnchor(bossHealthView, 0.0);
        bossFightView.getChildren().add(bossHealthView);

        bossFight = new BossFight(new Boss(), this.model.player.character, this);
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
        pauseTransition.setOnFinished(actionEvent -> {
            int result = this.bossFight.checkIfDone();
            if (result == 1) {
                this.model.gameState = null;
                this.model.after.setBlocked(false);

                int destinationRoom = this.model.after.getDestinationRoom();
                Room roomToVisit = this.model.getRooms().get(destinationRoom);
                this.model.player.setCurrentRoom(roomToVisit);

                view.bossView = null;
                view.updateScene("FIGHT OVER. YOU WON!");
                view.updateItems();
                view.pause.play();
                return;
            } else if (result == 2) {
                this.model.gameState = null;
                view.bossView = null;
                view.saveButton.setDisable(false);
                view.updateScene("FIGHT OVER. YOU LOST.");
                view.updateItems();
                view.pause.play();
                if (this.model.player.character.health.getLives() == 0) {
                    PauseTransition quit = new PauseTransition(Duration.seconds(2));
                    quit.setOnFinished(actionEvent1 -> view.submitEvent("QUIT"));
                    quit.play();
                    return;
                }
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
                this.bossFight.commandQueue.add(new BossHeal(this.bossFight));
                this.bossFight.isPlayerTurn = true;
            }

            updateHealth();

            pauseTransition.play();
        });
        pauseTransition.play();
        updateHealth();
    }

    /**
     * Updates the view with the action given
     * @param execute the action executed to update the view to
     */
    private void updateView(String execute) {
        switch (execute) {
            case "PLAYER ATTACK FIREBALL" -> fireball();
            case "PLAYER ARMOURUP" -> armourUp();
            case "PLAYER ATTACK " -> playerAttack();
            case "PLAYER HEAL" -> playerHeal();
            case "BOSS ATTACK " -> bossAttack();
            case "BOSS HEAL" -> bossHeal();
        }
    }

    /**
     * Update the health bars of the boss and player.
     */
    public void updateHealth(){
        double hpRatio = (double) this.model.getPlayer().character.health.hp / this.model.getPlayer().character.health.maxHP;
        healthBar.setProgress(hpRatio);

        hpLabel.setText("(" + this.model.player.character.health.hp + "/" + this.model.player.character.health.maxHP + ")");
        hpLabel.setStyle("-fx-text-fill: white;");
        hpLabel.setFont(new Font("Arial", 16));


        double bossHPRatio = (double) this.bossFight.boss.health.hp / this.bossFight.boss.health.maxHP;
        bossHealthBar.setProgress(bossHPRatio);

        bossHPLabel.setText("(" + this.bossFight.boss.health.hp + "/" + this.bossFight.boss.health.maxHP + ")");
        bossHPLabel.setStyle("-fx-text-fill: white;");
        bossHPLabel.setFont(new Font("Arial", 16));

    }

    /**
     * Format the text given for displaying
     */
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

    /**
     * Animate the player throwing a fireball
     */
    public void fireball() {
        bossFightLabel.setText("You throw a fireball!");

        Image fireballImage = new Image("Assets/Fireball.png");

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
        pathTransition.setDuration(Duration.millis(1500));
        pathTransition.setNode(fireballSprite);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setOnFinished(actionEvent -> this.bossFightView.getChildren().remove(fireballSprite));

        pathTransition.play();
    }

    /**
     * Update the view with the player armouring up
     */
    public void armourUp() {
        bossFightLabel.setText("You get armoured up!");
    }

    /**
     * Update the view with the player attacking the boss
     */
    public void playerAttack() {
        bossFightLabel.setText("You attack!");
        bossParticle("Hit.png");
    }

    /**
     * Update the view with the player healing
     */
    public void playerHeal() {
        bossFightLabel.setText("You heal up!");
        playerParticle("Hearts.png");
    }

    /**
     * Update the view with the boss attacking the player
     */
    public void bossAttack() {
        bossFightLabel.setText("The boss attacks you!");
        playerParticle("Hit.png");
    }

    /**
     * Update the view with the boss healing
     */
    public void bossHeal() {
        bossFightLabel.setText("The boss heals up!");
        bossParticle("Hearts.png");
    }

    /**
     * Display the given particle on the player
     */
    public void playerParticle(String filename) {
        Image image = new Image("Assets/" + filename);

        ImageView sprite = new ImageView(image);
        sprite.setFitHeight(50);
        sprite.setPreserveRatio(true);

        this.bossFightView.getChildren().add(sprite);
        AnchorPane.setBottomAnchor(sprite, 150.0);
        AnchorPane.setLeftAnchor(sprite, 10.0);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
        pauseTransition.setOnFinished(actionEvent -> bossFightView.getChildren().remove(sprite));
        pauseTransition.play();
    }

    /**
     * Display the given particle on the boss
     */
    public void bossParticle(String filename) {
        Image image = new Image("Assets/" + filename);

        ImageView sprite = new ImageView(image);
        sprite.setFitHeight(50);
        sprite.setPreserveRatio(true);

        this.bossFightView.getChildren().add(sprite);
        AnchorPane.setTopAnchor(sprite, 150.0);
        AnchorPane.setRightAnchor(sprite, 10.0);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
        pauseTransition.setOnFinished(actionEvent -> bossFightView.getChildren().remove(sprite));
        pauseTransition.play();
    }}
