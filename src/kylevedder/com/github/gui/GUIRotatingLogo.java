/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class GUIRotatingLogo
{

    private Image imageLead = null;
    private Image[] imageFollowers = null;
    
    private float angleRotation = 0f;
    private int timePassed = 0;
    
    private final float ANGLE_MULTIPLIER = 10f;
    private final int FOLLOWER_TIME_SPACING = 100;
    
    private final int LOOP_TIME_MILLIS;
    private final int LOGO_Y_LOCAL;

    public GUIRotatingLogo(String leadImagePath, String followImagePath, int numFollowers, int loopTimeMillis, int logoYlocation) throws SlickException
    {
        angleRotation = 0f;
        timePassed = 0;
        LOOP_TIME_MILLIS = loopTimeMillis;
        LOGO_Y_LOCAL = logoYlocation;
        imageLead = new Image(leadImagePath);        
        imageFollowers = new Image[numFollowers + 1];
        for (int i = 0; i < imageFollowers.length - 1; i++)
        {
            imageFollowers[i] = new Image(followImagePath);
            imageFollowers[i].setRotation(getRotationAngle(timePassed - FOLLOWER_TIME_SPACING * i));
        }        
    }    
    
    public void update(int deltaTime)
    {
        timePassed = (timePassed + deltaTime) % LOOP_TIME_MILLIS;
        imageLead.setRotation(getRotationAngle(timePassed));
        for (int i = 0; i < imageFollowers.length - 1; i++)
        {
            imageFollowers[i].setRotation(getRotationAngle(timePassed - FOLLOWER_TIME_SPACING * i));
        }
        
    }
    
    /**
     * Gets the rotation angle for each image given the time.
     * @param time
     * @return 
     */
    private float getRotationAngle(int time)
    {
        return ANGLE_MULTIPLIER * (float)Math.sin(2* Math.PI *(double)time/(LOOP_TIME_MILLIS));
    }
    
    public void render(GameContainer gc)
    {        
        for (int i = 0; i < imageFollowers.length - 1; i++)
        {
            imageFollowers[i].drawCentered(gc.getWidth()/2, LOGO_Y_LOCAL);
        }
        imageLead.drawCentered(gc.getWidth()/2, LOGO_Y_LOCAL);
    }

}
