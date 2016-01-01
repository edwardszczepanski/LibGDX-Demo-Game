package com.edwardszczepanski.ninecircles.Sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Screens.PlayScreen;

public class Enemy extends Sprite{
    public World world;
    public Body enemyBody;
    public TextureRegion battleCruiser;
    private int health;
    private boolean destroyed;

    public Enemy(World world, PlayScreen screen, float startX, float startY){
        super(screen.getAtlas().findRegion("BattleCruiser"));
        this.world = world;
        defineEnemy(startX, startY);
        battleCruiser = new TextureRegion(getTexture(), 1, 28, 78, 69);

        health = 50;
        destroyed = false;

        // Setting bounds of sprite
        setBounds(0, 0, 78 / NineCircles.PPM, 69 / NineCircles.PPM);
        setRegion(battleCruiser);
        // This is so it will rotate around the center of the sprite
        setOrigin(getWidth() / 2, getWidth() / 2);
    }

    // This method is to connect the Box2D object with the sprite
    public void update(float delta){
        setPosition(enemyBody.getPosition().x - getWidth() / 2, enemyBody.getPosition().y - getWidth() / 2);
    }

    public void rotateSprite(float degrees){
        rotate(degrees);
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
        shape.setRadius(35 / NineCircles.PPM);


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
