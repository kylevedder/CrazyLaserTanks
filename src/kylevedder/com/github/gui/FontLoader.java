/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Kyle
 */
public class FontLoader
{

    private UnicodeFont uFont = null;
    private Font awtFont = null;
    private float size = 0f;

    @SuppressWarnings("unchecked")//STFU about the add ColorEffect
    public FontLoader(String fontPath, float defaultSize)
    {
        this.size = defaultSize;
        InputStream inputStream = ResourceLoader.getResourceAsStream(fontPath);

        try
        {
            awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont(defaultSize);
            uFont = new UnicodeFont(awtFont);
            uFont.getEffects().add(new ColorEffect(java.awt.Color.yellow));
            uFont.addAsciiGlyphs();
            uFont.loadGlyphs();
        }
        catch (FontFormatException ex)
        {
            Logger.getLogger(FontLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(FontLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(FontLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the font of the initial size.
     *
     * Note: If loaded incorrectly, font may be null.
     *
     * @return
     */
    public UnicodeFont getFont()
    {
        return this.uFont;
    }

    /**
     * Gets a new sized font based on the AWT Font.
     *
     * Note: If loaded incorrectly, font may be null.
     *
     * @param size
     * @return
     */
    public UnicodeFont getSizedFont(float size)
    {
        if (awtFont != null)
        {
            return new UnicodeFont(awtFont.deriveFont(size));
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Sets the font color
     * @param color 
     */
    @SuppressWarnings("unchecked")//STFU about the add ColorEffect
    public void setColor(Color color)
    {
        try
        {
            this.uFont = new UnicodeFont(awtFont.deriveFont(this.size));            
            this.uFont.getEffects().add(new ColorEffect(color));
            this.uFont.addAsciiGlyphs();
            this.uFont.loadGlyphs();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(FontLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the font color.
     * @param font
     * @param color
     * @return 
     */
     @SuppressWarnings("unchecked")//STFU about the add ColorEffect
    public static UnicodeFont modifyFontColor(UnicodeFont font, java.awt.Color color)
    {
        try
        {            
            font = new UnicodeFont(font.getFont());
            font.getEffects().add(new ColorEffect(color));
            font.addAsciiGlyphs();
            font.loadGlyphs();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(FontLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return font;
    }

}
