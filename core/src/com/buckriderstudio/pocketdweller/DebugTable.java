package com.buckriderstudio.pocketdweller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.buckriderstudio.pocketdweller.systems.FovSystem;

public class DebugTable extends Table {
    private Skin skin;

    private FovSystem fovSystem;

    private Label fpsLabel;
    private Label fovSystemTime;

    public DebugTable(FovSystem fovSystem) {
        this.fovSystem = fovSystem;
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
        add(new Label("FOV: ", skin));
        fovSystemTime = new Label("", skin);
        add(fovSystemTime);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        fpsLabel.setText(Gdx.graphics.getFramesPerSecond());
        fovSystemTime.setText(fovSystem.getAverageTime() + "ms");

    }
}
