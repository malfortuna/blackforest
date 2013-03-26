package net.cutgar.warmup.model;

import org.flixel.FlxG;
import org.flixel.FlxSprite;

public class Pheasant extends FlxSprite {
	
	public enum STATE {PERCHED, FLYING};
	public STATE currentState = STATE.FLYING;
	
	int flight_height = 50;
	int delta_height = 20;
	
	public Pheasant(int x){
		super(x, 50);
		
		loadGraphic("pack.atlas:pheasant", true, true, 16, 16);
		
		addAnimation("idle", new int[]{0});
		play("idle");
		
		addAnimation("fly", new int[]{1,2,3}, 5, true);
		
		velocity.y = 5;
		velocity.x = 50;
		setFacing(RIGHT);
	}
	
	public void update(){

		if(currentState == STATE.FLYING){
			play("fly");
			
			if(y < flight_height - delta_height){
				velocity.y = 5;
			}
			else if(y > flight_height){
				velocity.y = -5;
			}
			
			if(x < 20){
				velocity.x = 50;
				setFacing(RIGHT);
			}
			else if(x > FlxG.width - 20){
				velocity.x = -50;
				setFacing(LEFT);
			}
			
		}
		
	}

}
