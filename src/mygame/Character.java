/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author krzysiek
 */
public class Character {
    private SceneManager scene;
    public String name;
    public String modelPath = "Models/Katalog/meHumanMale.001.mesh.xml";
    public String hit1Path = "Models/Hit1.mesh.xml";
    public String hit2Path = "Models/Hit2.mesh.xml";
    public float walkSpeed = 2f;
    public MovementState movementState;
    private final LastValueHolder<Vector3f> lookingVectorHolder;
    
    public Character(SceneManager sm) {
        AssetManager assetManager;
        Material mat = new Material(sm.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Spatial model = sm.getAssetManager().loadModel(modelPath);
        model.setMaterial(mat);
        scene = sm.attachAndGetManager(model);
        model.setLocalScale(0.2f);
        model.setLocalTranslation(0, 0.5f, 0);
        movementState = new MovementState();
        lookingVectorHolder = new LastValueHolder<>((Vector3f value) -> !value.equals(new Vector3f(0,0,0)), new Vector3f(1, 0, 0));
    }
    public Spatial getModel() {
        return scene.getRootNode();
    }
    public Vector3f getLookingVector() {
        return lookingVectorHolder.getValue(movementState.getInputVector());
    }
    public void walk() {
    }
    
    public void stop() {
    }
    
    public void hit() {
        System.out.println("HICIOR!");
        Spatial hitModel = scene.getAssetManager().loadModel(Math.random() > 0.5 ? hit1Path : hit2Path);
        scene.attach(new SceneElement(hitModel, 0.2f));
    }
}
