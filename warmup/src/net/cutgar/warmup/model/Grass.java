package net.cutgar.warmup.model;

import org.flixel.FlxSprite;

public class Grass extends FlxSprite {
	
	public Grass(int x, int y){
		super(x, y);
		
		loadGraphic("pack.atlas:grass", true, false, 8, 8);
		
		addAnimation("idle", new int[]{0});
		addAnimation("wave", new int[]{1,2,0},4,false);
		
		play("idle");
	}

}
