/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heighwaydragon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Oliver
 */
public class DragonViewer extends JFrame{
    private static final int nSteps = 5000;
    private static final int w = 1000, h = 1000;
    private static final Color bgCol = Color.WHITE, fgCol = new Color( 0, 0, 0, 100 );
    private static final double scale = 5;
    
    public DragonViewer( String dragon ){
        BufferedImage bi = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = bi.createGraphics();
        g.setColor( bgCol );
        g.fillRect( 0, 0, w, h );
        g.setColor( fgCol );
        Cursor cursor = new Cursor();
        int lastX = (int)(cursor.getX()*scale)+w/2;
        int lastY = (int)(cursor.getY()*scale)+h/2;
        for( char c : dragon.toCharArray() ){
            if( HeighwayDragon.advanceCursor( c, cursor ) ){
                int x = (int)(cursor.getX()*scale)+w/2;
                int y = (int)(cursor.getY()*scale)+h/2;
                g.drawLine( lastX, lastY, x, y );
                lastX = x;
                lastY = y;
            }
        }
        setContentPane( new JScrollPane( new JLabel( new ImageIcon( bi ) ) ) );
        setSize( 800, 800 );
        setLocationRelativeTo( null );
    }
}
