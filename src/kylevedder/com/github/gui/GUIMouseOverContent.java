/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class GUIMouseOverContent
{
    private String baseText;
    private String hoverText;    
    private Image baseImage = null;
    private Image hoverImage = null;
    private Image clickImage = null;

    public GUIMouseOverContent(String baseText, String hoverText, Image baseImage, Image hoverImage, Image clickImage)
    {
        setup(baseText, hoverText, baseImage, hoverImage, clickImage);
    }
    
    public GUIMouseOverContent(String baseText, String hoverText, Image baseImage, Image hoverImage)
    {
        setup(baseText, hoverText, baseImage, hoverImage, hoverImage);
    }
    
    public GUIMouseOverContent(String baseText, String hoverText, String baseImage, String hoverImage)
    {       
        try
        {
            setup(baseText, hoverText, new Image(baseImage), new Image(hoverImage), new Image(hoverImage));
        }
        catch (SlickException ex)
        {
            Logger.getLogger(GUIMouseOverContent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public GUIMouseOverContent(String baseText, String hoverText, String baseImage, String hoverImage, String clickImage)
    {       
        try
        {
            setup(baseText, hoverText, new Image(baseImage), new Image(hoverImage), new Image(clickImage));
        }
        catch (SlickException ex)
        {
            Logger.getLogger(GUIMouseOverContent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Sets up the content.
     * @param baseText
     * @param hoverText
     * @param clickText
     * @param baseImage
     * @param hoverImage
     * @param clickImage 
     */
    private void setup(String baseText, String hoverText, Image baseImage, Image hoverImage, Image clickImage)
    {
        this.baseText = baseText;
        this.hoverText = hoverText;
        this.baseImage = baseImage;
        this.hoverImage = hoverImage;        
        this.clickImage = clickImage;
    }

    public void setBaseImage(Image baseImage)
    {
        this.baseImage = baseImage;
    }

    public void setBaseText(String baseText)
    {
        this.baseText = baseText;
    }

    public void setHoverImage(Image hoverImage)
    {
        this.hoverImage = hoverImage;
    }

    public void setHoverText(String hoverText)
    {
        this.hoverText = hoverText;
    }        

    public String getBaseText()
    {
        return baseText;
    }

    public String getHoverText()
    {
        return hoverText;
    }

    public Image getBaseImage()
    {
        return baseImage;
    }

    public Image getHoverImage()
    {
        return hoverImage;
    }

    public Image getClickImage()
    {
        return clickImage;
    }    
}
