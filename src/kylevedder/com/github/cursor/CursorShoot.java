/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.cursor;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class CursorShoot extends BaseCursor
{

    public CursorShoot(Input input) throws SlickException
    {
        super(input);
        cursorImage = new Image("kylevedder/com/github/resources/images/crosshairs.png");
    }
    
}
