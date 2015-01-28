/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.controlls;

import kylevedder.com.github.main.Camera;
import kylevedder.com.github.main.MainApp;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

/**
 *
 * @author Kyle
 */
public class CustomMouseListener implements MouseListener
{
    private volatile boolean isOperating = true;
    
    private final int TICKS_PER_SCROLL = 120;
    
    public static final int BUTTON_LEFT = 0;
    public static final int BUTTON_RIGHT = 1;
    public static final int BUTTON_MIDDLE = 2;

    @Override
    public void mouseWheelMoved(int change)
    {
        //120 is 1 tick scrolled down
        MainApp.gameEngine.camera.addZoom(change / TICKS_PER_SCROLL * Camera.ZOOM_AMOUNT);        
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount)
    {
        if(button == BUTTON_MIDDLE)
        {
            MainApp.gameEngine.camera.setZoom(1f);            
        }
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
        
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {

    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
        
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
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
        this.isOperating = false;        
    }

    @Override
    public void inputStarted()
    {

    }

}
