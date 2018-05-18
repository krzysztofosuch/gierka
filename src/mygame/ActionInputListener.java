/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.input.controls.ActionListener;

/**
 *
 * @author krzysiek
 */
public class ActionInputListener implements ActionListener {
    Character character;
    public ActionInputListener(Character c) {
        this.character = c;
    }
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            switch (name) {
                case "Attack":
                    character.hit();
                    break;
                case "Attack2":
                    character.distanceHit();
                    break;
                    
            }
        } 
    }
    
}
