package SpeechToText;

import views.AdventureGameView;

import java.util.ArrayList;

public abstract class SPTStrategy {

    protected AdventureGameView view;

    public SPTStrategy(AdventureGameView view) {
        this.view = view;
    }

    public abstract void setUpSpeechToText();

    public abstract ArrayList<String> getPhrases();
}
