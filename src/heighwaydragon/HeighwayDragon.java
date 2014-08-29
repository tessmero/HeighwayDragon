/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heighwaydragon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

/**
 *
 * @author Oliver
 */
public class HeighwayDragon {
    
    public static void main(String[] args) throws Exception {
        //System.out.println( Arrays.toString( getPosition( getDragon( 10 ), 500 ) ) );
        //System.out.println( new DragonScript( 2 ) );
        new DragonViewer( new DragonScript( 50 ) ).setVisible( true );
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
    
    public static boolean advanceCursor( char c, Cursor cursor ){
        if( c == 'F' ){
            cursor.stepForward();
            return true;
        }else if( c == 'R' )
            cursor.turnRight();
        else if( c == 'L' )
            cursor.turnLeft();
        return false;
    }
}
