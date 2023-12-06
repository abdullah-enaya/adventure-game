package AdventureModel.boss;

import java.io.Serializable;

/**
 * PlayerAttack Command. Defines the action of a player attack in a BossFight.
 */
public class PlayerAttack implements Command, Serializable {
    BossFight bossFight;
    String attack;

    /**
     * Initialize the PlayerAttack.
     * @param attack Name of the attack if it is special.
     */
    public PlayerAttack(BossFight bossFight, String attack) {
        this.bossFight = bossFight;
        this.attack = attack;
    }

    /**
     * Execute the BossAttack action, returning the message for the view.
     * @return the message BOSS ATTACK [ATTACK] for the view to update.
     */
    @Override
    public String execute() {
        if (bossFight.boss.getHit(bossFight.player.attack())) {
            this.bossFight.winner = 1;
        }
        return "PLAYER ATTACK " + attack;
    }
}
