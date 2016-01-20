package com.edwardszczepanski.ninecircles.Scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.edwardszczepanski.ninecircles.NineCircles;

public class Hud implements Disposable{
    public Stage stage;
    public ExtendViewport viewport;
    public Table table;
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    // Now we create our widgets. Our widgets will be labels, essentially text, that allow us to display Game Information
    private Label countdownLabel;
    static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;
    private BitmapFont white;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb); // We must create order by creating a table in our stage

        table = new Table();
        table.top(); // Will put it at the top of our stage
        table.setFillParent(true);


        white = new BitmapFont(Gdx.files.internal("font/white16.fnt"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle(white, Color.WHITE);

        countdownLabel = new Label(String.format("%03d", worldTimer), labelStyle);
        scoreLabel = new Label(String.format("%06d", score), labelStyle);
        timeLabel = new Label("TIME", labelStyle);
        levelLabel = new Label("WASTE LAND", labelStyle);
        worldLabel = new Label("ROUND 1", labelStyle);
        marioLabel = new Label("SCORE:", labelStyle);

        table.add(marioLabel).expandX().padTop(10); // This expand X makes everything in the row share the row equally
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row(); // THIS CREATES A NEW ROW
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        // add table to our stage
        stage.addActor(table);

    }

    public void update(float dt) {
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
