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
public class Enemy extends SceneNode {
    AssetManager assetManager;
    
    Countdown vulnerable;
    
    public Enemy(AssetManager am) {
        assetManager = am;
    }
    public String modelPath = "Models/Mobek.mesh.xml";
    
    public Spatial model;
    
    public void gotHit(int power) {
        this.vulnerable = new Countdown(0.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Red);
        mat.setColor("Specular", ColorRGBA.Red);
        model.setMaterial(mat);
        hp -= power;
        System.out.println("Got hit by "+power);
    }   
    private int hp = 100;
    
    @Override
    public SceneNodeConfig getConfig() {
        return new SceneNodeConfig(true,0);
    }

    public void setState(int i) {
        if(i == 0){
            Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            mat.setBoolean("UseMaterialColors", true);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setColor("Specular", ColorRGBA.White);
            model.setMaterial(mat);
        }
    }
}
