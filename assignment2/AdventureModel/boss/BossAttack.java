package AdventureModel.boss;

import java.io.Serializable;

/**
 * BossAttack Command. Defines the action of a Boss attack in a BossFight.
 */
public class BossAttack implements Command, Serializable {
    BossFight bossFight;
    String attack;

    /**
     * Initialize the BossAttack.
     * @param attack Name of the attack if it is special.
     */
    public BossAttack(BossFight bossFight, String attack) {
        this.bossFight = bossFight;
        this.attack = attack;
    }

    /**
     * Execute the BossAttack action, returning the message for the view.
     * @return the message BOSS ATTACK [ATTACK] for the view to update.
     */
    @Override
    public String execute() {
        if (bossFight.player.getHit(bossFight.boss.attack())) {
            this.bossFight.winner = 2;
        }
        return "BOSS ATTACK " + attack;
    }
}
