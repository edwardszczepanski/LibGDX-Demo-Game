package com.edwardszczepanski.ninecircles.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Screens.PlayScreen;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.PointLight;


public class Hero extends Sprite{
    public enum State { RUNNING, STANDING, SHOOTING};
    public State currentState;
    public State previousState;
    private World world;
    private Body b2body;
    private TextureRegion battleCruiser;
    private static final float radius = 15;
    private ArrayList<Bullet> bulletList;
    private float xDif;
    private float yDif;
    private ConeLight heroCone;
    private PointLight pointLight;
    private Animation playerRun;
    private TextureRegion playerStanding;
    private TextureRegion playerShooting;
    private float stateTimer;
    private float lastShotTime;
    private float playerSpeed;
    public Music running;

    public Hero(World world, PlayScreen screen){
        super(screen.getAtlasTwo().findRegion("player"));

        this.world = world;
        defineHero();

        // Below the running animation is defined
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; ++i){
            frames.add(new TextureRegion(getTexture(), 1 + i * 79, 1, 79, 127));
        }
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        playerShooting = new TextureRegion(getTexture(), 1 + 6 * 79, 1, 79, 127);

        playerStanding = new TextureRegion(getTexture(), 1 + 0 * 79, 1, 79, 127);

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        bulletList = new ArrayList<Bullet>();
        lastShotTime = 0;
        playerSpeed = 1.0f;

        running = Gdx.audio.newMusic(Gdx.files.internal("running.mp3"));
        running.setLooping(true);


        // Setting bounds of sprite x, y, width, height
        setBounds(1, 1, 79 / NineCircles.PPM, (127 - 45) / NineCircles.PPM);
        setRegion(playerStanding);
        // This is so it will rotate around the center of the sprite
        setOrigin(getWidth() / 2 ,getWidth() / 2);
        defineLights();
    }

    public State getState(){
        if (b2body.getLinearVelocity().x != 0 || b2body.getLinearVelocity().y != 0){
            return State.RUNNING;
        }
        else if (System.nanoTime() - lastShotTime < 5 * 100000000.0){
            return State.SHOOTING;
        }
        else {
            return State.STANDING;
        }
    }


    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case SHOOTING:
                region = playerShooting;
                break;
            case STANDING:
                region = playerStanding;
                break;
            default:
                region = playerStanding;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;

        return region;
    }

    public void defineLights(){
        pointLight = new PointLight(PlayScreen.rayHandler, 150, Color.WHITE, 3f * radius/ NineCircles.PPM,0,0);
        pointLight.setSoftnessLength(0f);
        pointLight.attachToBody(b2body);
        heroCone = new ConeLight(PlayScreen.rayHandler,150, Color.WHITE, 500/NineCircles.PPM, 300/50,300/50, -50, radius*.8f);
        heroCone.setSoftnessLength(0f);
        heroCone.setContactFilter(NineCircles.DEFAULT_BIT, NineCircles.BULLET_BIT, NineCircles.DEFAULT_BIT);
    }
    public void updateConeLight(){
        heroCone.setDirection(getRotation() + 90);
        heroCone.setPosition(b2body.getPosition().x, b2body.getPosition().y);
    }

    // This method is to connect the Box2D object with the sprite
    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2);
        // This is math to get the orientation of the graphic correct
        xDif = Gdx.input.getX() - Gdx.graphics.getWidth() / 2 ;
        yDif = Gdx.input.getY() - Gdx.graphics.getHeight() / 2;
        setRotation((float) Math.toDegrees((Math.atan2(xDif * -1, yDif * -1))));
        // This is to get the animation to update properly
        setRegion(getFrame(delta));
    }

    public void heroBullet(World world, PlayScreen screen, float xPos, float yPos, float angle, float shooterRadius){
        bulletList.add(new Bullet(world, screen, xPos, yPos, angle, shooterRadius));
        lastShotTime = System.nanoTime();
    }

    public ArrayList<Bullet> getBulletList(){
        return bulletList;
    }

    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(20 / NineCircles.PPM, 120 / NineCircles.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / NineCircles.PPM);
        fdef.filter.categoryBits = NineCircles.HERO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.createFixture(fdef).setUserData(this);
    }



    public Body getHeroBody(){
        return b2body;
    }

    public float getHeroRadius(){
        return radius;
    }
    public ConeLight getHeroCone(){
        return heroCone;
    }

    public PointLight getHeroPoint(){
        return pointLight;
    }

    public float getSpeed(){
        return playerSpeed;
    }
}
