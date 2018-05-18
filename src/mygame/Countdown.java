/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author eengine
 */
public class Countdown {
    float elapsedTime;
    float countTo;
    
    public Countdown(float countTo){
        this.elapsedTime = 0;
        this.countTo = countTo;
    }
    
    public void countTime (float elapsedTime){
        this.elapsedTime += elapsedTime;
    }
    
    public boolean isReady(){
        if(this.elapsedTime > this.countTo){
            return true;
        } else {
            return false;
        }
    }
}
