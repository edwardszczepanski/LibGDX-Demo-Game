package com.edwardszczepanski.ninecircles.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.edwardszczepanski.ninecircles.Sprites.Bullet;
import com.edwardszczepanski.ninecircles.Sprites.Enemy;

public class WorldContactListener implements ContactListener{


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() instanceof Bullet){
            ((Bullet) fixA.getUserData()).destroyed = true;
            if(fixB.getUserData() instanceof Enemy){
                ((Enemy) fixB.getUserData()).health += -10;
                System.out.println(((Enemy) fixB.getUserData()).health);
            }
        }
        if(fixB.getUserData() instanceof Bullet){
            ((Bullet) fixB.getUserData()).destroyed = true;
            if(fixA.getUserData() instanceof Enemy){
                ((Enemy) fixA.getUserData()).health += -10;
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
