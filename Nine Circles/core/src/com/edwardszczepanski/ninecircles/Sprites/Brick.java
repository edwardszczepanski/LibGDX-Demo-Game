package com.edwardszczepanski.ninecircles.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Screens.PlayScreen;


public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen.getWorld(), screen.getMap(), bounds);
        fixture.setUserData(this);
        setCategoryFilter(NineCircles.BULLET_BIT);
    }

}