/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.menu;

import kylevedder.com.github.gui.FontLoader;
import kylevedder.com.github.states.StateManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class BaseMenu
{

    int BUTTON_HEIGHT = 42;
    int BUTTON_WIDTH = 800;
    int BUTTON_HORZ_SPACING = 10;
    int BUTTON_VERT_SPACING = 10;
    int BUTTON_VERT_TEXT_OFFSET = -5;
    
    float TEXT_SIZE = 32f;

    Image normalImage;
    Image hovImage;
    Image clickImage;
    
    FontLoader fontLoader;

    public BaseMenu(GameContainer gc, StateManager stateManager) throws SlickException
    {
        normalImage = new Image("images/buttons/normal.png").getScaledCopy(BUTTON_WIDTH, BUTTON_HEIGHT);        
        hovImage = new Image("images/buttons/hover.png").getScaledCopy(BUTTON_WIDTH, BUTTON_HEIGHT);
        clickImage = new Image("images/buttons/click.png").getScaledCopy(BUTTON_WIDTH, BUTTON_HEIGHT);
        
        fontLoader = new FontLoader("font/youre-gone/YoureGone.ttf", TEXT_SIZE);        
    }
    
    public void update() throws SlickException
    {
        
    }
    
    public void render(Graphics g, boolean render)
    {
        
    }

}
