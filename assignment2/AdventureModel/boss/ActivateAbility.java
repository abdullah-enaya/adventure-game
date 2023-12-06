package AdventureModel.boss;

import AdventureModel.characters.abilities.Ability;

/**
 * ActivateAbility Command. Used to activate the Ability given, in the BossFight.
 */
public class ActivateAbility implements Command {
    BossFight bossFight;
    Ability ability;

    /**
     * Initialize the command.
     */
    public ActivateAbility(BossFight bossFight, Ability ability) {
        this.bossFight = bossFight;
        this.ability = ability;
    }

    /**
     * Activate the ability, and return the message of the ability for the view.
     * @return the message PLAYER [ABILITY] for the view to update
     */
    @Override
    public String execute() {
        bossFight.equipPlayerAbility(ability);
        return "PLAYER " + ability.name;
    }
}
