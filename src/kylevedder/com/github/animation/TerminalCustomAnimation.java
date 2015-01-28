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
public class TerminalCustomAnimation extends CustomAnimation
{

    public TerminalCustomAnimation(SpriteSheet sheet, int duration)
    {
        super(sheet, duration);
    }

    @Override
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
            if(frameCount + add > this.images.length -1)
                return null;
            else
            this.frameCount = (frameCount + add);
        }
        return images[frameCount];
    }
    
    
    
}
