/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.teams;

import kylevedder.com.github.entity.TankEntity;
import kylevedder.com.github.entity.UserTankEntity;
import kylevedder.com.github.main.Camera;
import kylevedder.com.github.main.MainApp;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Kyle
 */
public class SinglePlayerMatchData
{

    private final String STALEMATE = "Stalemate...";
    private final String VICTORY_SUFFIX = " Wins!";

    Team yourTeam = null;
    Team opposingTeam = null;
    UserTankEntity player;
    
    Camera camera;

    public SinglePlayerMatchData(String yourTeamName, String opposingTeamName, UserTankEntity player, Camera camera)
    {
        this.yourTeam = new Team(yourTeamName);
        this.opposingTeam = new Team(opposingTeamName);
        this.player = player;
        this.camera = camera;
    }

    /**
     * Adds the given member to your team
     *
     * @param e
     */
    public void addToYourTeam(TankEntity e)
    {
        yourTeam.addMember(e);
    }

    /**
     * Adds the given member to opposing team
     *
     * @param e
     */
    public void addToOpposingTeam(TankEntity e)
    {
        opposingTeam.addMember(e);
    }

    /**
     * Gets yourTeam size
     *
     * @return
     */
    public int getYourTeamSize()
    {
        return this.yourTeam.size();
    }

    /**
     * Gets opposingTeam size
     *
     * @return
     */
    public int getOpposingTeamSize()
    {
        return this.opposingTeam.size();
    }

    /**
     * Gets the TankEntity at the given index from team 1.
     *
     * @param index
     * @return
     */
    public TankEntity getYourTeamEntity(int index)
    {
        return this.yourTeam.getEntity(index);
    }

    /**
     * Gets the TankEntity at the given index from team 2.
     *
     * @param index
     * @return
     */
    public TankEntity getOpposingTeamEntity(int index)
    {
        return this.opposingTeam.getEntity(index);
    }

    /**
     * Checks to see if both teams are alive.
     *
     * @return
     */
    public boolean isMatchOver()
    {
        return !(this.yourTeam.isTeamAlive() && this.opposingTeam.isTeamAlive());
    }

    /**
     * Gets the name of the winning team.
     * <p>
     * Note: will return null if match is not over!
     * </p>
     *
     * @return
     */
    public String getVictor()
    {
        if (isMatchOver())
        {
            if (this.yourTeam.isTeamAlive() || !this.player.isDestroyed())
            {
                return this.yourTeam.toString() + this.VICTORY_SUFFIX;
            }
            else if (this.opposingTeam.isTeamAlive())
            {
                return this.opposingTeam.toString() + this.VICTORY_SUFFIX;
            }
            else
            {
                return this.STALEMATE;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Draws the winner on the screen.
     *
     * @param g
     */
    public void render(Graphics g)
    {
        if (this.isMatchOver())
        {
            g.drawString(this.getVictor(),
                    (MainApp.gameEngine.screenManager.getCurrentResWidth() / 2 + camera.getRenderOffsetX()) / camera.getZoom(),
                    (MainApp.gameEngine.screenManager.getCurrentResHeight() / 2 + camera.getRenderOffsetY()) / camera.getZoom());
        }
    }

}
