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
public class InputListener implements ActionListener {
    Character character;
    public InputListener(Character c) {
        this.character = c;
    }
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        int iIsPressed = isPressed ? 1 : 0;
        switch (name) {
            case "Up":
                character.movementState.inputNorth = 1*iIsPressed;break;
            case "Down":
                character.movementState.inputSouth = 1*iIsPressed;break;
            case "Left":
                character.movementState.inputEast = 1*iIsPressed;break;
            case "Right":
                character.movementState.inputWest = 1*iIsPressed;break;
        }
        if (isPressed) {
            character.walk();
        } else {
            character.stop();
        }
    }
    
}
