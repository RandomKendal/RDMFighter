package org.parabot.random.rdmfighter.data;

public enum Food {
    ROCKTAIL("Rocktail", 14617),
    MANTA_RAY("Manta Ray", 392),
    SHARK("Shark", 386),
    LOBSTER("Lobster", 380);
    
    private final String name;
    private final int itemId;
    
    private Food(String name, int itemId) {
        this.name = name;
        this.itemId = itemId;
    }
    
    public String getName() {
    	return name;
    }
    
    public int getItemID() {
    	return itemId;
    }
}