package com.buckriderstudio.pocketdweller.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DebugTable extends Table {
    private Skin skin;


    private Label fpsLabel;

    public DebugTable() {
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));

        initLabels();
    }

    private void initLabels(){
        setFillParent(true);
        left();
        top();

        add(new Label("FPS:", skin));
        fpsLabel = new Label("", skin);
        add(fpsLabel);
        row();
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        fpsLabel.setText(Gdx.graphics.getFramesPerSecond());

    }
}
