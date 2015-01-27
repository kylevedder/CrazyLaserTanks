/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.physics;

import kylevedder.com.github.physics.CenteredRectangle;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Kyle
 */
public class RigidBox extends PhysicsObject
{

    private float x;
    private float y;
    private float width;
    private float height;

    public RigidBox(float x, float y, float width, float height, float rotation)
    {
        this.hitBox = new CenteredRectangle(x, y, width, height, rotation);        
        this.vector = new Vector(0, 0);
    }
    
    public void update(int delta)
    {
        //if no collision with other boxes
        this.hitBox.updateDelta(this.vector.getXComp(), this.vector.getYComp(), 0);                
//        System.out.println(vector);
    }
    
    public void render(Graphics g)
    {
        this.hitBox.render(g);
    }
    
    
}
