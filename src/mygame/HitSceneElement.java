/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.scene.Spatial;

/**
 *
 * @author eengine
 */
public class HitSceneElement extends SceneElement {
    
    private Character attacker;
    
    public HitSceneElement(Spatial s, SceneNodeConfig config, Character attacker) {
        super(s, config);
        this.attacker = attacker;
    }
    
    @Override
    public void die(){
        this.attacker.removeHit(this);
    }
    
}
