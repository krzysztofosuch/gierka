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
public abstract class SceneNodeConfig {
    boolean hpIndicator = true;
    float ttl;
    
    public static SceneNodeConfig blank() {
        return new SceneNodeConfig() {
        };
    }
}
