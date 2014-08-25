/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heighwaydragon;

import java.util.Arrays;

/**
 *
 * @author Oliver
 */
public class HeighwayDragon {
    
    public static void main(String[] args) {
        System.out.println( Arrays.toString( getPosition( getDragon( 10 ), 500 ) ) );
    }
    
    /**
     * non-optimized implementation as described in ProjectEuler Problem 220
     */
    private static int[] getPosition( String dragon, int nSteps ){
        if( nSteps <= 0 )
            throw new Error( "nSteps must be positive" );
        Cursor cursor = new Cursor();
        while( true ){
            for( char c : dragon.toCharArray() ){
                advanceCursor( c, cursor );
                int i = cursor.getStepCount();
                if( i > nSteps )
                    throw new Error( "low-level logic broken" );
                if( i == nSteps )
                    return new int[]{ cursor.getX(), cursor.getY() };
            }
        }
    }
    
    public static void advanceCursor( char c, Cursor cursor ){
        if( c == 'F' )
            cursor.stepForward();
        else if( c == 'R' )
            cursor.turnRight();
        else if( c == 'L' )
            cursor.turnLeft();
    }
    
    public static String getDragon( int i ){
        if( i == 0 )
            return "Fa";
        else{
            String s = getDragon( i-1 );
            
            //debug
//            System.out.println( "building dragon " + i );
            
            StringBuilder sb = new StringBuilder();
            for( char c : s.toCharArray() ){
                if( c == 'a' )
                    sb.append( "aRbFR" );
                else if( c == 'b' )
                    sb.append( "LFaLb" );
                else
                    sb.append( c );
            }
            return sb.toString();
        }
    }
}
