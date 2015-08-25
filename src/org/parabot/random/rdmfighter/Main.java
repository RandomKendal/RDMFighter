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
	private int biggestWidth = -1;
	
	
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
	public void paint(Graphics Graphs) {
		if(startScript) {
			try {
				Graphics2D g = (Graphics2D) Graphs;
				Color orange = new Color(255, 152, 31);
				
				if(biggestWidth == -1) {
					int enemyStringWidth = (int) g.getFontMetrics().getStringBounds(enemyInformation, g).getWidth();
					int foodStringWidth = (int) g.getFontMetrics().getStringBounds(foodInformation, g).getWidth();
					biggestWidth = ((enemyStringWidth > foodStringWidth) ? enemyStringWidth : foodStringWidth);
				}
				
				g.setColor(new Color(0, 0, 0, 160));
				g.fillRect(0, 0, 112, 34);
				g.fillRect(0, 34, 47 + biggestWidth, 31);
				
				int Y1 = 16;
				int Y2 = 48;
				
				drawDString(Manifest.name() + " v" + Manifest.version(), 6, Y1, g, orange);
				drawDString("Runtime: " + scriptTimer.toString(), 6, Y1 + 13, g, orange);
	
				drawDString("Killing:", 6, Y2, g, orange);
				drawDString("Food:", 6, Y2 + 13, g, orange);
				drawDString(enemyInformation, 45, Y2, g, Color.white);
				drawDString(foodInformation, 45, Y2 + 13, g, Color.white);
				
				Npc fightingNPC = Methods.getAttackingNPC();
				if(fightingNPC != null && fightingNPC.getMaxHealth() > 0) {
					double percentHealth = ((double) fightingNPC.getHealth()) / fightingNPC.getMaxHealth();
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 70, 160, 25);
					g.setColor(Color.red);
					g.fillRect(6, 76, 148, 13);
					g.setColor(new Color(0, 153, 0));
					g.fillRect(6, 76, (int) (((double) 148) * percentHealth), 13);
					if(fightingNPC.getHealth() != 0) {
						drawDString(fightingNPC.getHealth() + " / " + fightingNPC.getMaxHealth() + " HP", 10, 87, g, Color.white);
					} else {
						drawDString("(DEAD)", 10, 87, g, Color.white);
					}
				}
			} catch (Exception _e) {}
		}
	}
	
	public void drawDString(String text, int x, int y, Graphics2D g, Color col) {
		g.setColor(Color.black);
		g.drawString(text, x + 1, y + 1);
		g.setColor(new Color(255, 152, 31));
		g.setColor(col);
		g.drawString(text, x, y);
	}
}