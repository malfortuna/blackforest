package net.cutgar.warmup.model;

import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxTimer;
import org.flixel.event.IFlxTimer;

public class Fox extends FlxSprite {
	
	//Palettes include: http://www.colourlovers.com/palette/233810/Fox_in_the_snow
	
	public int speed = 40; 
	public int jump = 50;
	private boolean barking;
	
	public boolean dragging = false;
	public Rabbit draggedObject;
	
	public Fox(int x, int y){
		super(x, y);
		
		loadGraphic("pack.atlas:fox", true, true, 8, 8);
		
		addAnimation("idle", new int[]{0});
		play("idle");
		
		addAnimation("run", new int[]{0,1,0,2}, 8, true);
		addAnimation("jump", new int[]{1});
		addAnimation("hide", new int[]{3});
		addAnimation("bark", new int[]{4,0}, 1);
		addAnimation("drag", new int[]{5,6}, 2, true);
		
		
		acceleration.y = 200;
	}
	
	public void update(){
		
		if(dragging && !FlxG.keys.Z){
			dragging = !dragging;
			play("idle");
			speed = 40;
		}
		
		if(dragging){
			speed = 20;
			draggedObject.updateLocation(this);
		}
		
		if(isTouching(FLOOR)){
			if(FlxG.keys.RIGHT){
				velocity.x = speed;
				setFacing(RIGHT);
			}
			else if(FlxG.keys.LEFT){
				velocity.x = -speed;
				setFacing(LEFT);
			}
			else{
				velocity.x = 0;
			}
		}
		
		if(dragging){
			if(velocity.x < 0)
				setFacing(RIGHT);
			else if(velocity.x > 0)
				setFacing(LEFT);
			play("drag");
			return;
		}
		
		if(!barking && isTouching(FLOOR) && FlxG.keys.SPACE){
			velocity.y = -jump;
			velocity.x *= 2;
			play("jump");
		}
		
		if(isTouching(FLOOR) && velocity.y == 0){
			if(FlxG.keys.DOWN){
				velocity.x = 0;
				play("hide");
			}
			else if(FlxG.keys.SHIFT && !barking){
				barking = true;
				play("bark");
				if(Math.random() < 0.5)
					FlxG.play("bark1.mp3");
				else
					FlxG.play("bark2.mp3");
				FlxTimer barkTimer = new FlxTimer();
				barkTimer.start(1, 1, new IFlxTimer() {
					@Override
					public void callback(FlxTimer Timer) {
						barking = false;
					}
				});
			}
			else{
				if(!barking){
					if(velocity.x != 0)
						play("run");
					else
						play("idle");
				}
			}
			
		}
	}

}
