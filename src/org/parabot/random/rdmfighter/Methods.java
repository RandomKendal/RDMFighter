package org.parabot.random.rdmfighter;

import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.Npc;

public class Methods {
	
	public static Npc getHittingNPC() {
		for(Npc checker : Npcs.getNearest()) {
			try {
				if(checker.getInteractingCharacter().getIndex() == Players.getMyPlayer().getIndex() 
						&& Players.getMyPlayer().isInCombat()) {
					return checker;
				}
			} catch (Exception _e) {}
		}
		return null;
	}
	
	public static Npc getAttackingNPC() {
		for(Npc checker : Npcs.getNearest()) {
			try {
				if(checker.getInteractingCharacter().getIndex() == Players.getMyPlayer().getIndex() 
						&& Players.getMyPlayer().getInteractingCharacter().getIndex() == checker.getIndex()) {
					return checker;
				}
			} catch (Exception _e) {}
		}
		return null;
	}
	
	public static Integer getHPLevel() {
		return ((Skill.HITPOINTS.getRealLevel() > 99) ? 99 : Skill.HITPOINTS.getRealLevel());
	}
}
