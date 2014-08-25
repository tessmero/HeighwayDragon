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
public enum Direction {
    UP( 0, 1 ),
    RIGHT( 1, 0 ),
    DOWN( 0, -1 ),
    LEFT( -1, 0 );
    
    public final int dx, dy;
    
    private Direction( int dx, int dy ){
        this.dx = dx;
        this.dy = dy;
    }
    
    public Direction turnRight(){
        Direction[] vals = values();
        for( int i = 0 ; i < vals.length ; i++ )
            if( vals[i] == this )
                return vals[(i+1)%4];
        throw new Error( "low-level direction logic broken" );
    }
    
    public Direction turnLeft(){
        Direction[] vals = values();
        for( int i = 0 ; i < vals.length ; i++ )
            if( vals[i] == this )
                return vals[(i+3)%4];
        throw new Error( "low-level direction logic broken" );
    }
}
