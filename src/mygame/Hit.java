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
public class Hit {
    
    public Hit(int power, Character attacker) {
        this.power = power;
        this.attacker = attacker;
    }
    public final int power;
    public final Character attacker;
    
}
