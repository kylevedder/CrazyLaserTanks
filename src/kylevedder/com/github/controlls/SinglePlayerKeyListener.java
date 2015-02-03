/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.controlls;

import kylevedder.com.github.slickstates.SlickStateSinglePlayer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 *
 * @author Kyle
 */
public class SinglePlayerKeyListener implements KeyListener
{
    private final int ESC_KEY = 1;
    
    private SlickStateSinglePlayer sp;

    public SinglePlayerKeyListener(SlickStateSinglePlayer sp)
    {
        this.sp = sp;
    }    
    
    @Override
    public void keyPressed(int key, char c)
    {
        if(key == ESC_KEY)
        {
            this.sp.togglePaused();
        }
    }

    @Override
    public void keyReleased(int key, char c)
    {
        
    }

    @Override
    public void setInput(Input input)
    {
        
    }

    @Override
    public boolean isAcceptingInput()
    {
        return true;
    }

    @Override
    public void inputEnded()
    {
        
    }

    @Override
    public void inputStarted()
    {
        
    }
    
}
