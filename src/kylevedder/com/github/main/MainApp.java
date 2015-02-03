/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import java.lang.reflect.Field;
import kylevedder.com.github.music.MusicPlayer;
import kylevedder.com.github.slickstates.SlickStateMainMenu;
import kylevedder.com.github.slickstates.SlickStateSinglePlayer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Crazy Laser Tanks!
 *
 * @author Kyle
 */
public class MainApp extends StateBasedGame
{

    public static final int NUM_COLLISION_UPDATES = 16;        

    public static final int MAX_DELTA = 20;
    public static final int MIN_DELTA = 10;
    
    public static AppGameContainer app;

    public static MusicPlayer musicPlayer = null;
    public static ScreenManager screenManager = null;

    /**
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException
    {        
        musicPlayer = new MusicPlayer("kylevedder/com/github/resources/music/Ouroboros.ogg", "kylevedder/com/github/resources/music/Club_Diver.ogg");
        app = initApp("Tank Game Three");
    }

    /**
     * Initialization of the main game container
     *
     * @return New Game Container
     * @throws SlickException
     */
    private static AppGameContainer initApp(String title) throws SlickException
    {
        app = new AppGameContainer(new MainApp(title));
        app.setDisplayMode(ScreenManager.WINDOWED_SCREEN_WIDTH, ScreenManager.WINDOWED_SCREEN_HEIGHT, false);
        app.setMouseGrabbed(false);
        app.setVSync(true);
        app.setTargetFrameRate(60);
        app.setAlwaysRender(true);        
        app.setSmoothDeltas(true);
        app.setShowFPS(true);        
        app.setVerbose(true);
        screenManager = new ScreenManager(app, false);        
        app.start();
        return app;
    }

    public MainApp(String title)
    {
        super(title);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException
    {
        addState(new SlickStateMainMenu());
        addState(new SlickStateSinglePlayer());
    }

}
