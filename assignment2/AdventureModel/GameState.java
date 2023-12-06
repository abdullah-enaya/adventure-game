package AdventureModel;

/**
 * Interface GameState: for the state of the game, defines interpretAction
 */
public interface GameState {
    /**
     * interpretAction
     * interpret the user's action.
     *
     * @param command String representation of the command.
     */
    public String interpretAction(String command);
}
