package org.parabot.random.rdmfighter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.parabot.core.Context;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.wrappers.Npc;
import org.parabot.random.rdmfighter.data.Methods;
import org.parabot.random.rdmfighter.data.Variables;
import org.parabot.random.rdmfighter.strategies.Attack;
import org.parabot.random.rdmfighter.strategies.Eat;
import org.parabot.random.rdmfighter.strategies.Loot;

@ScriptManifest(
		author = "Random (Kendal)", 
		category = Category.COMBAT, 
		description = "The script can be used to fight several monsters. Also supports food eating at a specified percentage. (Made for my SW application)", 
		name = "RDM Fighter", 
		servers = { "PkHonor" }, 
		version = 0.1
		)

public class Main extends Script implements Paintable {

	private final ArrayList<Strategy> Strategies = new ArrayList<Strategy>();
	public Timer scriptTimer = new Timer();
	public ScriptManifest Manifest = (ScriptManifest) Main.class.getAnnotation(ScriptManifest.class);
	
	public boolean startScript = false;

	private String enemyInformation;
	private String foodInformation;
	private int biggestStringWidth = -1;
	
	
	@Override
	public boolean onExecute() {
		GUI g = new GUI(this);
        while(g.isVisible()) {
        	Time.sleep(100);
        }
		
		if(!startScript) {
			return false;
		}
		
		Context.getInstance().getRandomHandler().clearActiveRandoms();
		
		Strategies.add(new Eat());
		Strategies.add(new Loot());
		Strategies.add(new Attack());

        System.out.println();
        System.out.println("=== Starting Script! ===");
        System.out.println("Attacking: " + Variables.getNpc().getName());
        System.out.println("Eating: " + Variables.getFood().getName() + " at " + Variables.getEatPercentage() + "% health");
        
        if(Variables.getLootableItems() != null) {
	        String Lootables = "";
	        for(int Lootable : Variables.getLootableItems()) {
	        	Lootables += Lootable + ", ";
	        }
	        System.out.println("Looting: " + Lootables);
		} else {
	        System.out.println("Not looting anything.");
		}
        System.out.println("========================");
        System.out.println();
        
        enemyInformation = Variables.getNpc().getName() + " (#" + Variables.getNpc().getNpcID()[0] + ")";
		foodInformation = Variables.getFood().getName() + " at " + Variables.getEatPercentage() + "%";
        
		provide(Strategies);
		return true;
	}
	
	@Override
	public void onFinish() {
        System.out.println("Ran " + Manifest.name() + " v" + Manifest.version() + " for: " + scriptTimer.toString());
        System.out.println("Thank you for using my script!");
	}
	
	@Override
	public void paint(Graphics g) {
		if(startScript) {
			Graphics2D graphs = (Graphics2D) g;
			Color colOrange = new Color(255, 152, 31);
				
			if(biggestStringWidth == -1) {
				int enemyStringWidth = (int) graphs.getFontMetrics().getStringBounds(enemyInformation, graphs).getWidth();
				int foodStringWidth = (int) graphs.getFontMetrics().getStringBounds(foodInformation, graphs).getWidth();
				biggestStringWidth = ((enemyStringWidth > foodStringWidth) ? enemyStringWidth : foodStringWidth);
			}
				
			graphs.setColor(new Color(0, 0, 0, 160));
			graphs.fillRect(0, 0, 112, 34);
			graphs.fillRect(0, 34, 47 + biggestStringWidth, 31);
				
			int Y1 = 16;
			int Y2 = 48;
				
			drawDoubleString(Manifest.name() + " v" + Manifest.version(), 6, Y1, graphs, colOrange);
			drawDoubleString("Runtime: " + scriptTimer.toString(), 6, Y1 + 13, graphs, colOrange);
	
			drawDoubleString("Killing:", 6, Y2, graphs, colOrange);
			drawDoubleString("Food:", 6, Y2 + 13, graphs, colOrange);
			drawDoubleString(enemyInformation, 45, Y2, graphs, Color.white);
			drawDoubleString(foodInformation, 45, Y2 + 13, graphs, Color.white);
				
			Npc currentlyFightingNpc = Methods.getAttackingNPC();
			if(currentlyFightingNpc != null && currentlyFightingNpc.getMaxHealth() > 0) {
				double percentHealth = ((double) currentlyFightingNpc.getHealth()) / currentlyFightingNpc.getMaxHealth();
				graphs.setColor(new Color(0, 0, 0, 160));
				graphs.fillRect(0, 70, 160, 25);
				graphs.setColor(Color.red);
				graphs.fillRect(6, 76, 148, 13);
				graphs.setColor(new Color(0, 153, 0));
				graphs.fillRect(6, 76, (int) (((double) 148) * percentHealth), 13);
				if(currentlyFightingNpc.getHealth() != 0) {
					drawDoubleString(currentlyFightingNpc.getHealth() + " / " + currentlyFightingNpc.getMaxHealth() + " HP", 10, 87, graphs, Color.white);
				} else {
					drawDoubleString("(DEAD)", 10, 87, graphs, Color.white);
				}
			}
		}
	}
	
	public void drawDoubleString(String text, int x, int y, Graphics2D graphics, Color color) {
		graphics.setColor(Color.black);
		graphics.drawString(text, x + 1, y + 1);
		graphics.setColor(new Color(255, 152, 31));
		graphics.setColor(color);
		graphics.drawString(text, x, y);
	}
}