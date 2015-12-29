package com.edwardszczepanski.ninecircles.Sprites;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.ninecircles.NineCircles;

public class Hero {
    public World world;
    public Body b2body;

    public Hero(World world){
        this.world = world;
        defineHero();
    }
    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / NineCircles.PPM, 32 / NineCircles.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / NineCircles.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);


    }
}
