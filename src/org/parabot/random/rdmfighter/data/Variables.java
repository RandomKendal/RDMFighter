package org.parabot.random.rdmfighter.data;

public class Variables {
	
	private static int eatAtPercent = 60;
	private static int[] lootableItems;
	private static Food selectedFood = Food.LOBSTER;
	private static EnemyNpc selectedNpc = EnemyNpc.GOBLIN;
	
	public static int getEatPercentage() {
		return eatAtPercent;
	}
	
	public static int[] getLootableItems() {
		return lootableItems;
	}
	
	public static Food getFood() {
		return selectedFood;
	}
	
	public static EnemyNpc getNpc() {
		return selectedNpc;
	}
	
	public static void configureSettings(int eatpercentage, int[] lootables, Food food, EnemyNpc npc) {
		eatAtPercent = eatpercentage;
		lootableItems = lootables;
		selectedFood = food;
		selectedNpc = npc;
	}
}
