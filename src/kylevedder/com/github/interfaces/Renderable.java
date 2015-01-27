/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.interfaces;

import org.newdawn.slick.Input;

/**
 *
 * @author Kyle
 */
public interface Renderable
{
    public void update(Input input, int delta);
    
    public void render();
}
