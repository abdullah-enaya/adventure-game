package SpeechToText;

public class SPTContext {
    private SPTStrategy strategy;

    public SPTContext() {
    }

    public SPTContext(SPTStrategy strategy) {
        this.strategy = strategy;
    }

    public SPTStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SPTStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        this.strategy.setUpSpeechToText();
    }
}
