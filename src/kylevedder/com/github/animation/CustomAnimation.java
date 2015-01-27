/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.animation;

import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kyle
 */
public class CustomAnimation
{

    private Image[] images = null;
    private int duration;
    private int frameCount = 0;
    private long delta = 0;
    private boolean frozen = false;

    /**
     * Sets up an animation using a sheet and a duration
     *
     * @param sheet sprite sheet for each image
     * @param duration duration in milliseconds
     */
    public CustomAnimation(SpriteSheet sheet, int duration)
    {
        images = new Image[sheet.getVerticalCount() * sheet.getHorizontalCount()];
        this.duration = duration;
        for (int y = 0; y < sheet.getVerticalCount(); y++)
        {
            for (int x = 0; x < sheet.getHorizontalCount(); x++)
            {
                //translates "x,y" to just "i"
                images[y * (sheet.getHorizontalCount() - 1) + x] = sheet.getSubImage(x, y);
            }
        }
        frameCount = 0;
        delta = 0;
        frozen = false;
    }

    /**
     * Updates the delta count, accruing over time.
     *
     * @param delta
     */
    public void update(long delta)
    {
        this.delta += delta;
        frozen = false;
    }
    
    /**
     * Sets the duration which the animation updates in milliseconds.
     * @param duration duration in milliseconds
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    /**
     * Updates the delta count, not over accruing time.
     *
     * @param delta
     */
    public void freeze(long delta)
    {
        if (!this.frozen)
        {
            this.delta += delta;
        }
        frozen = true;
    }

    public Image getFrame(boolean reverse)
    {
        if (!frozen)
        {
            //get the number of frames jumped
            int add = (int) (this.delta / this.duration);
            //subtract the number of frames jumped, leaving any extra time behind
            this.delta -= (long) (add * this.duration);
            //if reversed, want to subtract add, so multiply by -1
            add *= (reverse) ? -1 : 1;
            frameCount = Utils.wrapInt(frameCount + add, 0, images.length - 1);
        }
        return images[frameCount];
    }

}
