/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 *
 * @author krzysiek
 */
public class Enemy extends Character {
    
    Countdown vulnerable;
    
    public Enemy(SceneManager scene) {
        super(scene);
        this.hp = 100 + (int) ((Math.random() * 30) -15);
    }
    public String modelPath = "Models/Mobek.mesh.xml";
    
    public Spatial model;
    
    private int hp = 100;
    
    @Override
    public void gotHit(int power) {
        this.vulnerable = new Countdown(0.5f);
        Material mat = new Material(this.scene.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Red);
        mat.setColor("Specular", ColorRGBA.Red);
        model.setMaterial(mat);
        hp -= power;
        System.out.println("Got hit by "+power);
    }   
    
    public boolean isDead(){
        if(this.hp <= 0){
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public SceneNodeConfig getConfig() {
        return new SceneNodeConfig(true,0);
    }

    public void setState(int i) {
        if(i == 0){
            Material mat = new Material(this.scene.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
            mat.setBoolean("UseMaterialColors", true);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setColor("Specular", ColorRGBA.White);
            model.setMaterial(mat);
        }
    }
}
