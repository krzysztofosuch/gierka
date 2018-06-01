/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;


import com.jme3.input.controls.ActionListener;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author krzysiek
 */
public class DevToolkit implements ActionListener {
    SceneManager scene;
    public DevToolkit(SceneManager scene) {
        this.scene = scene;
    }
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        int iIsPressed = isPressed ? 1 : 0;
        switch (name) {
            case "GenerateEnemy":
                Random randomizer = new Random();
                Material mat = new Material(this.scene.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
                Spatial map = this.scene.getAssetManager().loadModel("Models/Mapka.mesh.xml");
                map.rotate((float)Math.PI, 0, 0);
                Material m = mat.clone();
                map.setMaterial(m);
                
                Enemy o = new Enemy(this.scene.getAssetManager());
                o.model = this.scene.getAssetManager().loadModel(o.modelPath);
                o.model.setMaterial(mat);
                o.model.setLocalScale(0.2f);
                o.model.setLocalTranslation((randomizer.nextInt(10)-5), 0.5f, (randomizer.nextInt(10)-5));
                this.scene.getRootNode().attachChild(o.model);
            
        }
    }
    
}
