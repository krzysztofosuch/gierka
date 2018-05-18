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
    
    private final Hit hit;
    private boolean harmless = false;
    public HitSceneElement(Spatial s, SceneNodeConfig config, Hit hit) {
        super(s, config);
        this.hit = hit;
    }
    
    @Override
    public void die(){
        this.hit.attacker.removeHit(this);
    }
    
    public Hit getHit() {
        return this.hit;
    }
    
    public void setHarmless() {
        this.harmless = true;
    }
    
    public boolean isHamless() {
        return this.harmless;
    }
    
}
