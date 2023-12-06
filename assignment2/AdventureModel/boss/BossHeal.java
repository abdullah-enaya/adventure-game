package AdventureModel.boss;

import java.io.Serializable;

/**
 * BossHeal Command. Defines the action of a Boss healing in a BossFight.
 */
public class BossHeal implements Command, Serializable {
    BossFight bossFight;

    /**
     * Initialize the BossHeal.
     */
    public BossHeal(BossFight bossFight) {
        this.bossFight = bossFight;
    }

    /**
     * Execute the BossHeal action, returning the message for the view.
     * @return the message BOSS HEAL for the view to update.
     */
    @Override
    public String execute() {
        bossFight.boss.heal();
        return "BOSS HEAL";
    }
}
