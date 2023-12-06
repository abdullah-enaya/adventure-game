package AdventureModel.boss;

/**
 * Command Interface. Defines the execute method. Commands are to be used in a BossFight, so that they are queued
 * and executed.
 */
public interface Command {
    /**
     * Execute the action. Return a message for BossFightView to update.
     * @return the message to update BossFightView
     */
    String execute();
}
