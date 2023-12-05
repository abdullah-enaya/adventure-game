package views;

import AdventureModel.AdventureGame;
import AdventureModel.Boss;
import AdventureModel.BossFight;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class BossFightView {
    AnchorPane bossView;
    Label bossLabel;
    VBox bossPane;
    ImageView characterSprite;
    ImageView bossSprite;
    AdventureGame model;
    BossFight bossFight;

    public BossFightView(AdventureGame model) {
        this.bossView = new AnchorPane();
        this.model = model;

        Image characeterImage = new Image("Assets/characterImages/" + this.model.player.character.title + ".png");
        this.characterSprite = new ImageView(characeterImage);
        characterSprite.setFitWidth(150);
        characterSprite.setPreserveRatio(true);

        Image bossImage = new Image("Assets/Boss.png");
        this.bossSprite = new ImageView(bossImage);
        bossSprite.setFitWidth(150);
        bossSprite.setPreserveRatio(true);

        AnchorPane.setBottomAnchor(characterSprite, 10.0);
        AnchorPane.setLeftAnchor(characterSprite, 10.0);
        AnchorPane.setTopAnchor(bossSprite, 10.0);
        AnchorPane.setRightAnchor(bossSprite, 10.0);

        this.bossView.getChildren().addAll(characterSprite, bossSprite);
        this.bossFight = new BossFight(new Boss(), this.model.player.character);
        bossView.setPrefWidth(400);
        bossView.setPrefHeight(400);

        bossLabel = new Label();
        formatText("Boss fight started!!!!!!!!!huisdfsdfjiosdijofiojsdfjosdiojfiojsdfijosiojdfiojsdiofiojsdfoijsijodfiojsiojdfoijsdiojfoijsdojifiojsdiojfijosdijofijosdjiofjiosdjiofjiosdiojfiojsdijofijosdjifosijodfijosdoijfoisjdfjoisdf");

        bossLabel.setPrefWidth(500);
        bossLabel.setPrefHeight(100);
        bossLabel.setTextOverrun(OverrunStyle.CLIP);
        bossLabel.setWrapText(true);
        bossPane = new VBox(bossView, bossLabel);
        bossPane.setPadding(new Insets(10));
        bossPane.setAlignment(Pos.TOP_CENTER);
        bossPane.setStyle("-fx-background-color: #000000;");

    }
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) bossLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else bossLabel.setText(roomDesc);
        } else bossLabel.setText(textToDisplay);
        bossLabel.setStyle("-fx-text-fill: white;");
        bossLabel.setFont(new Font("Arial", 16));
        bossLabel.setAlignment(Pos.CENTER);
    }

    public void throwFireball() {
        Image fireballImage = new Image("Assets/Fireball.png");

        ImageView fireballSprite = new ImageView(fireballImage);
        fireballSprite.setFitWidth(150);
        fireballSprite.setPreserveRatio(true);

        this.bossView.getChildren().add(fireballSprite);
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
        pathTransition.setOnFinished(actionEvent -> this.bossView.getChildren().remove(fireballSprite));

        pathTransition.play();
    }




}
