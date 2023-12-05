package AdventureModel;

import AdventureModel.boss.BossFight;
import AdventureModel.boss.PlayerAttack;
import AdventureModel.boss.PlayerHeal;
import AdventureModel.characters.abilities.Ability;

import java.util.Arrays;

public class BossState implements GameState {
    public AdventureGame model;
    public BossFight bossFight;

    public BossState(AdventureGame model, BossFight bossFight) {
        this.model = model;
        this.bossFight = bossFight;
    }
    @Override
    public String interpretAction(String command) {
        String[] inputArray = model.tokenize(command); //look up synonyms

        if(inputArray[0].equals("QUIT")) { return "GAME OVER"; } //time to stop!
        else if(inputArray[0].equals("ABILITIES")) return bossFight.player.unlockedAbilities.toString();
        else if(inputArray[0].equals("ABILITY") && inputArray.length < 2) return "THE ABILITY COMMAND REQUIRES AN ABILITY";
        else if(inputArray[0].equals("LEVEL") && inputArray.length < 2) {
            Level level = model.player.getLevel();
            String xp;
            if (level.isMaxLevel()) {
                xp = Integer.toString(level.getXP());
            } else {
                xp = level.getXP() + "/" + level.getXPToNextLevel();
            }
            return "YOU ARE AT LEVEL " + model.player.getLevel().getLevel() + ", AND AT XP " + xp;
        }
        else if(inputArray[0].equals("ABILITY") && inputArray.length == 2 && bossFight.isPlayerTurn) {
            Ability ability = model.player.character.getAbility(inputArray[1]);
            if(ability != null) {
                if (bossFight.equipPlayerAbility(ability)) {
                    bossFight.isPlayerTurn = false;
                    return null;
                } else {
                    return "YOU CAN'T ACTIVATE " + inputArray[1] + " NOW.";
                }
            } else {
                return "THAT IS NOT AN AVAILABLE ABILITY: " + inputArray[1];
            }
        }
        else if(inputArray[0].equals("ATTACK") && bossFight.isPlayerTurn) {
            bossFight.isPlayerTurn = false;
            this.bossFight.commandQueue.add(new PlayerAttack(this.bossFight, ""));
            return null;
        }
        else if(inputArray[0].equals("HEAL") && bossFight.isPlayerTurn) {
            bossFight.isPlayerTurn = false;
            this.bossFight.commandQueue.add(new PlayerHeal(this.bossFight));
            return null;
        }
        return "INVALID COMMAND.";
    }
}
