package org.parabot.random.rdmfighter.data;

public enum EnemyNpc {
    GOBLIN("Goblin",  new int[]{ 101 }),
    ROCK_CRAB("Rock Crab", new int[]{ 1265, 1267 }),
    MOUNTAIN_TROLL("Mountain Troll", new int[]{ 1106, 1110 }),
    TZHAAR_KET("TzHaar-Ket",  new int[]{ 2610 }),
    BLUE_DRAGON("Blue Dragon", new int[]{ 55 });
    
    private final String name;
    private final int[] npcId;
    
    private EnemyNpc(String name, int[] npcId) {
        this.name = name;
        this.npcId = npcId;
    }
    
    public String getName() {
    	return name;
    }
    
    public int[] getNpcID() {
    	return npcId;
    }
}