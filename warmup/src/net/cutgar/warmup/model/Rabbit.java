package net.cutgar.warmup.model;

import net.cutgar.warmup.model.Hunter.STATE;

import org.flixel.FlxG;
import org.flixel.FlxSprite;

public class Rabbit extends FlxSprite{

	//Palette used: http://www.colourlovers.com/palette/2533201/Snow_Hare.
	
	public enum STATE {IDLE, RUN, DEAD};
	public STATE currentState = STATE.IDLE;
	private boolean walkingright = true;
	
	public Rabbit(int x, int y){
		super(x, y);
		
		loadGraphic("pack.atlas:rabbit", true, true, 8, 8);
		
		addAnimation("idle", new int[]{0});
		addAnimation("run", new int[]{3,1,3,2}, 8, true);
		addAnimation("dead", new int[]{4});
		
		play("idle");
		acceleration.y = 200;
	}
	
	public void update(){
		
		if(FlxG.keys.justPressed("Q")){
			currentState = STATE.values()[(currentState.ordinal()+1)%STATE.values().length];
		}
		
		if(currentState == STATE.IDLE){
			velocity.x = 0;
			play("idle");
		}
		else if(currentState == STATE.RUN){
			if(walkingright && x < FlxG.width-20){
				velocity.x = 50;
				play("run");
				setFacing(RIGHT);
			}
			else if(walkingright){
				walkingright  = !walkingright;
				velocity.x = -50;
				setFacing(LEFT);
				play("run");
			}
			
			if(!walkingright && x > 20){
				velocity.x = -50;
				setFacing(LEFT);
				play("run");
			}
			else if(!walkingright){
				walkingright = !walkingright;
				velocity.x = 50;
				play("run");
				setFacing(RIGHT);
			}
		}
		else if(currentState == STATE.DEAD){
			play("dead");
		}
		
	}

	public void updateLocation(Fox fox) {
		if(fox.velocity.x == 0)
			return;
		
		if(fox.getFacing() == RIGHT)
			this.x = fox.x+8;
		else if(fox.getFacing() == LEFT)
			this.x = fox.x-8;
	}
	
}
