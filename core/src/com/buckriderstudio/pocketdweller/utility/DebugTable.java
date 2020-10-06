package com.buckriderstudio.pocketdweller.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.buckriderstudio.pocketdweller.world.World;
import com.kotcrab.vis.ui.widget.VisLabel;

public class DebugTable extends Table {
    private Skin skin;

    private World world;

    private VisLabel fpsLabel;
    private VisLabel fovCalculations;

    public DebugTable(World world) {
        this.world = world;
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));

        initLabels();
    }

    private void initLabels(){
        setFillParent(true);
        left();
        bottom();

        add(new VisLabel("FPS:"));
        fpsLabel = new VisLabel("");
        add(fpsLabel);
        row();
        add(new VisLabel("FOV: "));
        fovCalculations = new VisLabel("");
        add(fovCalculations);
        row();
    }


    @Override
    public void act(float delta) {
        super.act(delta);


        fpsLabel.setText(Gdx.graphics.getFramesPerSecond());
        fovCalculations.setText((float)(world.getFovTime() / 1000000f) + "ms.");


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        toFront();
        super.draw(batch, parentAlpha);
    }
}
