package com.edwardszczepanski.ninecircles.Sprites;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.ninecircles.NineCircles;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;


        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();


        // Different type of bodies. Static, dynamic - player affected by forces, kinematic - not affected by forces but can move at a constant velocity like a moving platform
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ NineCircles.PPM, (bounds.getY() + bounds.getHeight()/2)/NineCircles.PPM); // I don't follow the math

        body = world.createBody(bdef);

        // define polygon shape itself now
        shape.setAsBox(bounds.getWidth() / 2 / NineCircles.PPM, bounds.getHeight() / 2 / NineCircles.PPM); // it starts at the center
        fdef.shape = shape;

        fixture = body.createFixture(fdef); // We have the ground taken care of
        body.createFixture(fdef).setUserData(this);


    }

    //public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * NineCircles.PPM / 16),
                (int)(body.getPosition().y * NineCircles.PPM / 16));

    }
}
