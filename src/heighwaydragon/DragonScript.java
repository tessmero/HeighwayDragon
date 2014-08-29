/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heighwaydragon;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Oliver
 */
public class DragonScript {
    
    private int index = 0;
    private DragonScript child = null;
    private final String topLevel;
    private final int depth;
    private final List< CompletionListener > completionListeners = new ArrayList();
    
    public DragonScript( int depth ){
        this( "Fa", depth );
    }
    
    public DragonScript( String topLevel, int depth, CompletionListener cl ){
        this( topLevel, depth );
        addCompletionListener( cl );
    }
    
    public DragonScript( String topLevel, int depth ){
        this.topLevel = topLevel;
        this.depth = depth;
    }
    
    public void reset(){
        index = 0;
        child = null;
    }
    
    public int getNextChar(){
        if( child != null ){
            int result = child.getNextChar();
            if( result == -1 )
                child = null;
            else
                return result;
        }
        
        if( index >= topLevel.length() ){
            announceCompletion( depth );
            return -1;
        }
        int result = topLevel.toCharArray()[index++];
        if( depth > 0 && result == 'a' )
            child = new DragonScript( "aRbFR", depth-1, i->announceCompletion(i) );
        else if( depth > 0 && result == 'b' )
            child = new DragonScript( "LFaLb", depth-1, i->announceCompletion(i) );
        else
            return result;
        
        return getNextChar();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int c;
        while( (c = getNextChar()) != -1 )
            sb.append( (char)c );
        return sb.toString();
    }
    
    public void addCompletionListener( CompletionListener cl ){
        completionListeners.add( cl );
    }
    
    private void announceCompletion( int completedDepth ){
        for( CompletionListener cl : completionListeners )
            cl.scriptCompleted( completedDepth );
    }
    
    public interface CompletionListener{
        void scriptCompleted( int depth );
    }
}
