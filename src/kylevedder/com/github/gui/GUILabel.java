/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

/**
 *
 * @author Kyle
 */
public class GUILabel
{

    private GameContainer gc;
    private String text;
    private float centerX;
    private float centerY;
    private UnicodeFont drawingFont;

    public GUILabel(GameContainer gc, float centerX, float centerY, UnicodeFont font, String text)
    {
        this.centerX = centerX;
        this.centerY = centerY;
        this.gc = gc;
        this.text = text;
        this.drawingFont = font;
    }

    /**
     * Sets the center of the label
     *
     * @param centerX
     * @param centerY
     */
    public void setCenter(float centerX, float centerY)
    {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * Sets the text of the label
     * @param text 
     */
    public void setText(String text)
    {
        this.text = text;
    }
    
    

    /**
     * Renders the label on the screen
     */
    public void render()
    {
        this.drawingFont.drawString(centerX - drawingFont.getWidth(text) / 2, centerY - drawingFont.getHeight(text) / 2, text);
    }

}
