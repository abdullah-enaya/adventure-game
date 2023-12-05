package AdventureModel.boss;

public class PlayerHeal implements Command {
    BossFight bossFight;

    public PlayerHeal(BossFight bossFight) {
        this.bossFight = bossFight;
    }

    @Override
    public String execute() {
        bossFight.player.heal();
        return "PLAYER HEAL";
    }
}
