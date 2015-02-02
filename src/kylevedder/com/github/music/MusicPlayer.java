/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.music;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class MusicPlayer
{

    private Music menuMusic;
    private Music gameMusic;

    public MusicPlayer(String menuMusic, String gameMusic) throws SlickException
    {
        this.menuMusic = new Music(menuMusic);
        this.gameMusic = new Music(gameMusic);
    }

    /**
     * Starts menu music and stops all other music.
     */
    public void startMenuMusic()
    {
        if (this.gameMusic.playing())
        {
            this.gameMusic.stop();
        }
        if (!this.menuMusic.playing())
        {
            this.menuMusic.loop();
        }
    }

    /**
     * Starts game music and stops all other music.
     */
    public void startGameMusic()
    {
        if (this.menuMusic.playing())
        {
            this.menuMusic.stop();
        }
        if (!this.gameMusic.playing())
        {
            this.gameMusic.loop();
        }
    }

    /**
     * Stops all music.
     */
    public void stopMusic()
    {
        if (this.menuMusic.playing())
        {
            this.menuMusic.stop();
        }
        if (this.gameMusic.playing())
        {
            this.gameMusic.stop();
        }
    }

}
