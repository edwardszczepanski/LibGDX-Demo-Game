package com.edwardszczepanski.NineCircles.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map){
        // defining what the body consists of
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

            for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX()+rect.getWidth()/2), (rect.getY() + rect.getHeight()/2)); // I don't follow the math

                body = world.createBody(bdef);

                // define polygon shape itself now
                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2); // it starts at the center
                fdef.shape = shape;
                body.createFixture(fdef); // We have the ground taken care of
            }




    }
}
