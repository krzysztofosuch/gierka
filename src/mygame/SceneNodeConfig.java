/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author krzysiek
 */
public class SceneNodeConfig {
    public boolean hpIndicator = true;
    public float ttl;
    public SceneNodeConfig(boolean hpIndicator, float ttl) {
        this.hpIndicator = hpIndicator;
        this.ttl = ttl;
    }
    public static SceneNodeConfig blank() {
        return new SceneNodeConfig(false, 0) {
        };
    }
}
