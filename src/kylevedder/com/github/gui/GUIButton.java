/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import kylevedder.com.github.main.MainApp;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author Kyle
 */
public class GUIButton
{

    private GUIRectangle rect;
    private GUIMouseOverContent content;
    private TextField field = null;
    private MouseOverArea area = null;

    private GameContainer gc = null;

    private UnicodeFont regularFont = null;
    private UnicodeFont hoverFont = null;

    private boolean buttonClicked = false;
    
    private float vertPadding = 0f;
    private float horzPadding = 0f;

    public GUIButton(GameContainer gc, float centerX, float centerY, float width, float height, GUIMouseOverContent content)
    {
        this.gc = gc;
        this.rect = new GUIRectangle(centerX, centerY, width, height);
        this.content = content;
        this.area = new MouseOverArea(gc, this.content.getBaseImage(), this.rect.getRectangle());
        this.area.setMouseOverImage(content.getHoverImage());
        this.area.setMouseDownImage(content.getClickImage());
        this.regularFont = new UnicodeFont(new JLabel().getFont());
        this.hoverFont = new UnicodeFont(new JLabel().getFont());
        this.buttonClicked = false;
        this.area.addListener(new ComponentListener()
        {

            @Override
            public void componentActivated(AbstractComponent source)
            {
                buttonClicked = true;
            }
        });
    }

    /**
     * Sets the padding for the button text.
     * <p>
     * Positive values shift text right and down, negative values shift values left and down
     * </p.
     * @param horzPadding
     * @param vertPadding 
     */
    public void setTextPadding(float horzPadding, float vertPadding)
    {
        this.horzPadding = horzPadding;
        this.vertPadding = vertPadding;
    }
    
    /**
     * Sets the defaultFont for the button.
     *
     * @param font
     */
    public void setFont(UnicodeFont regularFont, UnicodeFont hoverFont)
    {
        this.regularFont = regularFont;
        this.hoverFont = hoverFont;
    }        

    /**
     * Sets the font colors.
     *
     * @param defaultColor
     * @param hoverColor
     */
    public void setFontColors(Color defaultColor, Color hoverColor)
    {
        this.regularFont = FontLoader.modifyFontColor(regularFont, defaultColor);
        this.hoverFont = FontLoader.modifyFontColor(hoverFont, hoverColor);
    }

    /**
     * Checks if the button is clicked.
     *
     * @return
     */
    public boolean isButtonClicked()
    {
        return buttonClicked;
    }

    /**
     * Resets the state of the button.
     */
    public void resetButtonClicked()
    {
        buttonClicked = false;
    }

    /**
     * Renders the GUIButton
     *
     * @param g
     */
    public void render(Graphics g)
    {
        this.area.render(gc, g);
        if (this.area.isMouseOver())
        {
            this.hoverFont.drawString(
                    this.area.getX() + this.area.getWidth() / 2 - this.hoverFont.getWidth(this.content.getHoverText()) / 2 + this.horzPadding,
                    this.area.getY() + this.area.getHeight() / 2 - this.hoverFont.getHeight(this.content.getHoverText()) / 2 + this.vertPadding,
                    this.content.getHoverText());
        }
        else
        {
            this.regularFont.drawString(
                    this.area.getX() + this.area.getWidth() / 2 - this.regularFont.getWidth(this.content.getBaseText()) / 2 + this.horzPadding,
                    this.area.getY() + this.area.getHeight() / 2 - this.regularFont.getHeight(this.content.getBaseText()) / 2 + this.vertPadding,
                    this.content.getBaseText());
        }
    }

}
