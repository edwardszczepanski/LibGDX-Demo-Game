package com.edwardszczepanski.ninecircles.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
    public Body b2body;
    public TextureRegion battleCruiser;

    public Enemy(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("BattleCruiser"));
        this.world = world;
        defineEnemy();
        battleCruiser = new TextureRegion(getTexture(), 1, 28, 78, 69);


        // Setting bounds of sprite
        setBounds(0, 0, 78 / NineCircles.PPM, 69 / NineCircles.PPM);
        setRegion(battleCruiser);
        // This is so it will rotate around the center of the sprite
        setOrigin(getWidth() / 2,getWidth() / 2);
    }

    // This method is to connect the Box2D object with the sprite
    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2);

    }

    public void rotateSprite(float degrees){
        rotate(degrees);
    }

    public void defineEnemy(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(50 / NineCircles.PPM, 50 / NineCircles.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(35 / NineCircles.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
