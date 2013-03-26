package net.cutgar.warmup;

import net.cutgar.warmup.model.Fox;
import net.cutgar.warmup.model.Grass;
import net.cutgar.warmup.model.Hunter;
import net.cutgar.warmup.model.Pheasant;
import net.cutgar.warmup.model.Rabbit;
import net.cutgar.warmup.model.Rabbit.STATE;

import org.flixel.FlxBasic;
import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxObject;
import org.flixel.FlxPoint;
import org.flixel.FlxRect;
import org.flixel.FlxState;
import org.flixel.FlxTileblock;
import org.flixel.event.IFlxCollision;

public class PlayState extends FlxState
{
	private FlxTileblock floor;
	public static Fox player;
	private FlxGroup grass_fore = new FlxGroup();
	private FlxGroup grass_back = new FlxGroup();
	private FlxGroup grass = new FlxGroup();
	private Hunter hunter;
	private FlxBasic rabbit;
	
	private int width = 600;
	private Pheasant bird;

	@Override
	public void create()
	{
		FlxG.setBgColor(0xff50595C);
		
		int[] colors = new int[]{0xff011612, 0xff033027, 0xff054639, 0xff085E4C, 0xff0D7560};
		for(int j=0; j<4; j++){
			for(int i=0; i<width; i++){
				if(Math.random() < j*0.2)
					continue;
				FlxTileblock tree = new FlxTileblock(i, 0, 8 + (int)(Math.random()*16), FlxG.height);
				tree.makeGraphic((int)tree.width, (int)tree.height, colors[(int)((Math.random() * colors.length))]);
				tree.setAlpha((j*0.2f));
				tree.scrollFactor.x = 0.2f+(j*0.2f);
				add(tree);
				
				i+= (int)tree.width;
			}
		}
		
		for(int i=0; i<width/8; i++){
			if(Math.random() < 0.9){
				Grass g = new Grass(i*8, FlxG.height-8);
				if(Math.random() < 0.5)
					grass_back.add(g);
				else
					grass_fore.add(g);
				grass.add(g);
			}
		}
		
		floor = new FlxTileblock(0, FlxG.height-2, width, 8);
		floor.makeGraphic(width, 8, 0xff789090);
		add(floor);
		
		add(grass_back);
		
		player = new Fox(100, FlxG.height-16);
		add(player);
		
		add(grass_fore);
		
		hunter = new Hunter(10, FlxG.height-16-8);
		add(hunter);
		
		rabbit = new Rabbit(5, FlxG.height - 16);
		add(rabbit);
		
		bird = new Pheasant(50);
		add(bird);
		
		FlxG.camera.follow(player);
		FlxG.camera.bounds = new FlxRect(0, 0, 800, 120);
		FlxG.worldBounds.width = width;
	}
	
	@Override
	public void update(){
		super.update();
		FlxG.collide(floor, player);
		FlxG.collide(floor, hunter);
		FlxG.collide(floor, rabbit);
		FlxG.overlap(player, grass, new IFlxCollision() {
			@Override
			public void callback(FlxObject o_player, FlxObject o_grass) {
				if(((Fox) o_player).velocity.x != 0)
					((Grass) o_grass).play("wave");
			}
		});
		
		FlxG.overlap(player, rabbit, new IFlxCollision() {
			
			@Override
			public void callback(FlxObject o_player, FlxObject o_rabbit) {
				if(o_rabbit.alive && o_player.velocity.y != 0){
					((Rabbit)o_rabbit).currentState = STATE.DEAD;
					o_rabbit.velocity = new FlxPoint(0, 0);
					o_rabbit.alive = false;
				}
				else if(!o_rabbit.alive && FlxG.keys.Z){
					((Fox)o_player).dragging = true;
					((Fox)o_player).draggedObject = (Rabbit) o_rabbit;
				}
			}
		});
		
	}
}