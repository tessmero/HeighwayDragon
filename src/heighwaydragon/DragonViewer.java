/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heighwaydragon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Oliver
 */
public class DragonViewer extends JFrame{
    private static final int w = 800, h = 800;
    private static final Color 
            bgCol = Color.WHITE, 
            fgCol = Color.BLACK,
            cursorCol = Color.GREEN;
    private static final long delay = 0; //delay of zero indicates fast-as-possible updates
    
    private final Cursor cursor;
    private DragonScript script;
    
    private BufferedImage displayImage;
    private Graphics displayGraphics;
    private int scriptPos = 0;
    private double scale = 8;
    
    public DragonViewer( DragonScript script ) throws FileNotFoundException{
        displayImage = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
        displayGraphics = displayImage.getGraphics();
        displayGraphics.setColor( bgCol );
        displayGraphics.fillRect( 0, 0, w, h );
        displayGraphics.setColor( fgCol );
        cursor = new Cursor();
//        script.addCompletionListener( i->{
//            if( i > 6 )
//                System.out.println( "completion at depth " + i );
//        } );
        this.script = script;
        setContentPane( new JScrollPane( new JLabel(){
            @Override
            public void paint( Graphics g ){
                g.drawImage( displayImage, 0, 0, null );
                g.setColor( cursorCol );
                g.fillRect( transformXCoord(cursor.getX())-2, transformYCoord(cursor.getY())-2, 5, 5 );
            }
        } ) );
        setSize( 800, 800 );
        setLocationRelativeTo( null );
        
        if( delay > 0 ){
            new Timer().scheduleAtFixedRate( new TimerTask(){
                @Override
                public void run() {
                    doStep();
                }
            }, delay, delay);
        }else{
            new Thread(()->{
                while(true)
                    doStep();
            }).start();
        }
    }
    
    private char getNextScriptChar(){
        int result = script.getNextChar();
        if( result == -1 ){
            
            if( cursor.isAtStart() )
                System.out.println( "looped after " + cursor.getStepCount() + " steps" );
            
            script.reset();
            result = script.getNextChar();
        }
        
        //System.out.println( cursor.getStepCount() );
        
        return (char)result;
    }
    
    private int transformXCoord( int x ){
        return (int)(x*scale)+w/2;
    }
    
    private int transformYCoord( int y ){
        return (int)(y*scale)+h/2;
    }
    
    private void doStep(){
        int lastX = transformXCoord(cursor.getX());
        int lastY = transformYCoord(cursor.getY());
        while( true ){
            if( HeighwayDragon.advanceCursor( getNextScriptChar(), cursor ) ){
                int x = transformXCoord(cursor.getX());
                int y = transformYCoord(cursor.getY());
                displayGraphics.drawLine( lastX, lastY, x, y );
                if( isOutOfBounds( x, y ) )
                    scaleOut();
                repaint();
                return;
            }
        }
    }
    
    private boolean isOutOfBounds( int x, int y ){
        return x < 0 || y < 0 || x > w || y > h;
    }
    
    private void scaleOut(){
        BufferedImage temp = new BufferedImage( w, h, displayImage.getType() );
        displayGraphics = temp.getGraphics();
        displayGraphics.setColor( bgCol );
        displayGraphics.fillRect( 0, 0, w, h );
        displayGraphics.setColor( fgCol );
        displayGraphics.drawImage( displayImage.getScaledInstance( w/2, h/2, Image.SCALE_SMOOTH ), w/4, h/4, null );
        scale /= 2;
        displayImage = temp;
    }
}
