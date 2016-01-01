package com.edwardszczepanski.ninecircles.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Screens.PlayScreen;

import java.util.ArrayList;

public class Hero extends Sprite{
    public World world;
    public Body b2body;
    public TextureRegion battleCruiser;
    public float radius = 35;
    public float currentAngle;
    public ArrayList<Bullet> bulletList;
    private float xDif;
    private float yDif;


    public Hero(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("BattleCruiser"));
        this.world = world;
        defineHero();
        battleCruiser = new TextureRegion(getTexture(), 1, 28, 78, 69);
        // This is to know where the ship is shooting for the bullet sprite orientation and physics
        currentAngle = 0;

        bulletList = new ArrayList<Bullet>();

        // Setting bounds of sprite
        setBounds(0, 0, 78 / NineCircles.PPM, 69 / NineCircles.PPM);
        setRegion(battleCruiser);
        // This is so it will rotate around the center of the sprite
        setOrigin(getWidth() / 2,getWidth() / 2);
    }

    // This method is to connect the Box2D object with the sprite
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2);

        // This is math to get the orientation of the graphic correct
        xDif = Gdx.input.getX() - Gdx.graphics.getWidth() / 2 ;
        yDif = Gdx.input.getY() - Gdx.graphics.getHeight() / 2;
        setRotation((float) Math.toDegrees((Math.atan2(xDif * -1, yDif * -1))));

    }

    public void rotateSprite(float degrees){
        rotate(degrees);
    }

    public void heroBullet(World world, PlayScreen screen, float xPos, float yPos, float angle, float shooterRadius){
        bulletList.add(new Bullet(world, screen, xPos, yPos, angle, shooterRadius));
    }

    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / NineCircles.PPM, 32 / NineCircles.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / NineCircles.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.createFixture(fdef).setUserData(this);

    }
}
