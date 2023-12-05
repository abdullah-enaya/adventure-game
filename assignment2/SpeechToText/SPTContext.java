package SpeechToText;

/**
 * Class Speech-To-Text context
 */
public class SPTContext {
    private SPTStrategy strategy;

    /**
     * Empty constructor.
     */
    public SPTContext() {}

    /**
     * Initializes attributes.
     * @param strategy the strategy
     */
    public SPTContext(SPTStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Getter for strategy.
     * @return the current strategy
     */
    public SPTStrategy getStrategy() {
        return strategy;
    }

    /**
     * Setter for strategy.
     * @param strategy the strategy to be set
     */
    public void setStrategy(SPTStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Execute the strategy.
     */
    public void executeStrategy() {
        this.strategy.setUpSpeechToText();
    }
}
