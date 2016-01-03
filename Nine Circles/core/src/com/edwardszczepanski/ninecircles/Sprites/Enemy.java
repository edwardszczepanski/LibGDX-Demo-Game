package com.edwardszczepanski.ninecircles.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Screens.PlayScreen;

import box2dLight.PointLight;

public class Enemy extends Sprite{
    private World world;
    private Body enemyBody;
    private TextureRegion battleCruiser;
    private int health;
    private boolean destroyed;
    private PointLight pointLight;
    private float enemyRadius;

    private float xDif, yDif;

    public Enemy(World world, PlayScreen screen, float startX, float startY){
        super(screen.getAtlas().findRegion("BattleCruiser"));
        this.world = world;
        enemyRadius = 35;
        defineEnemy(startX, startY);
        battleCruiser = new TextureRegion(getTexture(), 1, 28, 78, 69);

        pointLight = new PointLight(PlayScreen.rayHandler, 150, Color.RED, enemyRadius/ NineCircles.PPM,0,0);
        pointLight.setSoftnessLength(0f);
        pointLight.attachToBody(enemyBody);
        health = 30;

        destroyed = false;

        // Setting bounds of sprite
        setBounds(0, 0, 78 / NineCircles.PPM, 69 / NineCircles.PPM);
        setRegion(battleCruiser);
        // This is so it will rotate around the center of the sprite
        setOrigin(getWidth() / 2, getWidth() / 2);
    }

    // This method is to connect the Box2D object with the sprite
    public void update(float delta, float heroX, float heroY) {
        setPosition(enemyBody.getPosition().x - getWidth() / 2, enemyBody.getPosition().y - getWidth() / 2);

        xDif = enemyBody.getPosition().x - heroX;
        yDif = enemyBody.getPosition().y -heroY;

        setRotation((float) Math.toDegrees((Math.atan2(xDif * 1, yDif * -1))));
    }

    public int getHealth(){
        return health;
    }
    public void setHealth(int inputHealth){
        health = inputHealth;
    }

    public void setDestroyed(boolean input){
         destroyed = input;
    }
    public boolean isDestroyed(){
        return destroyed;
    }

    public void defineEnemy(float startX, float startY){
        BodyDef bdef = new BodyDef();
        bdef.position.set(startX, startY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        enemyBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(enemyRadius / NineCircles.PPM);


        fdef.shape = shape;

        enemyBody.createFixture(fdef);
        enemyBody.createFixture(fdef).setUserData(this);

    }

    public void deleteBody(){
        world.destroyBody(enemyBody);
        enemyBody.setUserData(null);
        enemyBody = null;
    }
}
