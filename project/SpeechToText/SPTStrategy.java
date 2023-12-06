package SpeechToText;

import views.AdventureGameView;

import java.util.ArrayList;

/**
 * Class for Speech-To-Text Strategy.
 */
public abstract class SPTStrategy {

    protected AdventureGameView view;

    /**
     * Initializes the attributes.
     * @param view the adventure game view
     */
    public SPTStrategy(AdventureGameView view) {
        this.view = view;
    }

    /**
     * Set up the speech-to-text by creating a timeline
     * and activating Google API.
     */
    public abstract void setUpSpeechToText();

    /**
     * Return the legal phrases for a given strategy.
     * @return a list of all legal phrases for the strategy
     */
    public abstract ArrayList<String> getPhrases();
}
