package org.parabot.random.rdmfighter.strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.Item;
import org.parabot.random.rdmfighter.Methods;
import org.parabot.random.rdmfighter.data.Variables;

public class Eat implements Strategy {

	@Override
	public boolean activate() {
		return (((double) Skill.HITPOINTS.getLevel()) / Methods.getHPLevel() * 100 <= Variables.getEatPercentage());
	}

	@Override
	public void execute() {
		Item[] foodId = Inventory.getItems(Variables.getFood().getItemID());
		if(foodId.length > 0 && foodId != null) {
			Menu.sendAction(74, foodId[0].getId() - 1, foodId[0].getSlot(), 3214);
			Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                      return (((double) Skill.HITPOINTS.getLevel()) / Methods.getHPLevel() * 100 > Variables.getEatPercentage());
                }
			}, 1000);
		} else {
	        System.out.println("No more food left!");
	        Time.sleep(1000);
		}
	}
}