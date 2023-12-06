package AdventureModel.boss;

import java.io.Serializable;

/**
 * PlayerHeal Command. Defines the action of a player healing in a BossFight.
 */
public class PlayerHeal implements Command, Serializable {
    BossFight bossFight;

    /**
     * Initialize the PlayerHeal.
     */
    public PlayerHeal(BossFight bossFight) {
        this.bossFight = bossFight;
    }

    /**
     * Execute the PlayerHeal action, returning the message for the view.
     * @return the message PLAYER HEAL for the view to update.
     */
    @Override
    public String execute() {
        bossFight.player.heal();
        return "PLAYER HEAL";
    }
}
