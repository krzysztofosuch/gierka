/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 *
 * @author krzysiek
 */
public class Enemy {
    AssetManager assetManager;
    public Enemy(AssetManager am) {
        assetManager = am;
    }
    public String modelPath = "Models/Mobek.mesh.xml";
    
    public Spatial model;
    public void gotHit() {
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Red);
        mat.setColor("Specular", ColorRGBA.Red);
        model.setMaterial(mat);
    }
}
