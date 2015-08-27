package org.parabot.random.rdmfighter.strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.Npc;
import org.parabot.random.rdmfighter.data.Methods;
import org.parabot.random.rdmfighter.data.Variables;

public class Attack implements Strategy {

	Npc[] enemyNpcs;
	@Override
	public boolean activate() {
		Item[] foodId = Inventory.getItems(Variables.getFood().getItemID());
		if(foodId.length > 0) {
			if(Methods.getAttackingNPC() == null) {
				enemyNpcs = Npcs.getNearest(Variables.getNpc().getNpcID());
				return (enemyNpcs.length > 0 && enemyNpcs != null);
			}
		} else {
			System.out.println("Not going to attack anything; you have no valid food left!");
			Time.sleep(5000);
		}
		return false;
	}

	@Override
	public void execute() {
		if(Methods.getHittingNPC() != null) {
			Npc enemyNpc = Methods.getHittingNPC();
			if(enemyNpc.getHealth() != 0 && enemyNpc.getMaxHealth() >= 0 
					|| enemyNpc.getHealth() == 0 && enemyNpc.getMaxHealth() == 0) {
				enemyNpc.interact(1);
				Time.sleep(enemyNpc.distanceTo() * 400 + 400);
				return;
			}
		}
		for(Npc enemyNpc : enemyNpcs) {
			if(!enemyNpc.isInCombat()) {
				enemyNpc.interact(1);
				Time.sleep(enemyNpc.distanceTo() * 400 + 400);
				return;
			}
		}
	}
}