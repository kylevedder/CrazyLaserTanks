/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.states;

import kylevedder.com.github.music.MusicPlayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public interface BasicState
{

    public void init(GameContainer gc, StateManager stateManager, MusicPlayer musicPlayer) throws SlickException;

    public void update(GameContainer gc, int deltaTime) throws SlickException;

    public void render(GameContainer gc, Graphics g) throws SlickException;
}
