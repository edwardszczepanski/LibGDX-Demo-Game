package com.edwardszczepanski.ninecircles.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Scenes.Hud;
import com.edwardszczepanski.ninecircles.Sprites.InteractiveTileObject;


public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        //fixture.setUserData(this);
        //setCategoryFilter(NineCircles.BRICK_BIT);
    }

    /*
    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(NineCircles.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }
    */
}