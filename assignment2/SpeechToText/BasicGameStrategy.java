package SpeechToText;

import AdventureModel.AdventureObject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import views.AdventureGameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BasicGameStrategy extends SPTStrategy{
    public BasicGameStrategy(AdventureGameView view) {
        super(view);
    }

    @Override
    public void setUpSpeechToText() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(6), (ActionEvent event) -> {
            AtomicReference<String> command = new AtomicReference<>("");
            Thread thread = new Thread(() -> {
                command.set(SpeechToText.streamingMicRecognize(getPhrases()));
            });
            thread.start();

            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
            pauseTransition.setOnFinished(actionEvent -> {
                System.out.println("Command: "+ command);
                if (command.get() != null && !command.get().isEmpty())  {
                    this.view.submitEvent(command.get());
                }
            });
            pauseTransition.play();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        Timeline prevTimeline = this.view.getTimeline();
        if (prevTimeline.getStatus().equals(Animation.Status.RUNNING)) {
            prevTimeline.stop();
            this.view.setTimeline(timeline);
            this.view.getTimeline().play();
        } else {
            this.view.setTimeline(timeline);
        }


        this.view.getStage().getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F5) {
                if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
                    timeline.stop();
                } else {
                    timeline.play();
                }
            }
        });
    }

    @Override
    public ArrayList<String> getPhrases() {
        ArrayList<String> allPhrases = new ArrayList<>();
        allPhrases.addAll(this.view.getModel().player.getCurrentRoom().getCommandsList());
        allPhrases.addAll(List.of(this.view.getModel().actionVerbs));
        allPhrases.addAll(List.of(new String[] {"LOOK", "HELP", "COMMANDS"}));
        for (AdventureObject object: this.view.getModel().player.getCurrentRoom().objectsInRoom) {
            allPhrases.add(object.getName());
        }
        allPhrases.addAll(this.view.getModel().player.getInventory());
        return allPhrases;
    }
}
