package org.parabot.random.rdmfighter.strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.GroundItems;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.wrappers.GroundItem;
import org.parabot.random.rdmfighter.data.Variables;

public class Loot implements Strategy {
	GroundItem[] foundItems;
	
	@Override
	public boolean activate() {
		if(Variables.getLootableItems() != null) {
			foundItems = GroundItems.getNearest(Variables.getLootableItems());
			return (foundItems.length > 0 && foundItems != null);
		}
		return false;
	}

	@Override
	public void execute() {
		for(GroundItem foundItem : foundItems) {
			final int count = Inventory.getCount(true);
			foundItem.take();
			Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                      return (Inventory.getCount(true) != count);
                }
			}, (foundItem.distanceTo() * 400 + 400));
		}
	}
}