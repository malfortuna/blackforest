package net.cutgar.warmup.model;

import org.flixel.FlxG;
import org.flixel.FlxSprite;

public class Hunter extends FlxSprite {
	
	//Palette: http://www.colourlovers.com/palette/1725317/Hunters_Moon
	boolean walkingright = true;
	
	public enum STATE {PATROL, AIM, FIRE, AIMDOWN, IDLE};
	public STATE currentState = STATE.PATROL;
	
	public Hunter(int x, int y){
		super(x, y);
		
		loadGraphic("pack.atlas:huntersheettest", true, true, 16, 16);
		addAnimation("idle", new int[]{3});
		play("idle");
		
		addAnimation("aim", new int[]{0,1,2}, 12, false);
		addAnimation("aimdown", new int[]{2,1,0}, 12, false);
		addAnimation("shoot", new int[]{2});
		addAnimation("walk", new int[]{3,4,5,6}, 4, true);
		
		acceleration.y = 200;
	}
	
	public void update(){
		
		if(FlxG.keys.justPressed("TAB")){
			currentState = STATE.values()[(currentState.ordinal()+1)%STATE.values().length];
		}

		if(currentState == STATE.PATROL){
			if(walkingright && x < FlxG.width-20){
				velocity.x = 20;
				play("walk");
				setFacing(RIGHT);
			}
			else if(walkingright){
				walkingright = !walkingright;
				velocity.x = -20;
				setFacing(LEFT);
				play("walk");
			}
			
			if(!walkingright && x > 20){
				velocity.x = -20;
				setFacing(LEFT);
				play("walk");
			}
			else if(!walkingright){
				walkingright = !walkingright;
				velocity.x = 20;
				play("walk");
				setFacing(RIGHT);
			}
		}
		else if(currentState == STATE.AIM){
			play("aim");
			currentState = STATE.FIRE;
			velocity.x = 0;
		}
		else if(currentState == STATE.AIMDOWN){
			play("aimdown");
			currentState = STATE.IDLE;
		}
		else if(currentState == STATE.IDLE){
			if(finished)
				play("idle");
		}
	}

}
