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

public class ChooseCharacterStrategy extends SPTStrategy {

    public ChooseCharacterStrategy(AdventureGameView view) {
        super(view);
    }

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
                    try {
                        this.view.submitEvent2(command.get());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pauseTransition.play();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        this.view.setTimeline(timeline);

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
        return new ArrayList<>(List.of("MAGE", "DAMAGE", "DWARF"));
    }
}
