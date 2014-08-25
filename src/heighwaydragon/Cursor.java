/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heighwaydragon;

/**
 *
 * @author Oliver
 */
public class Cursor {
    private Direction dir = Direction.UP;
    private int x = 0, y = 0;
    private int stepCount = 0;
    
    public void turnLeft(){
        dir = dir.turnLeft();
    }
    
    public void turnRight(){
        dir = dir.turnRight();
    }
    
    public void stepForward(){
        x += dir.dx;
        y += dir.dy;
        stepCount++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStepCount() {
        return stepCount;
    }
}
