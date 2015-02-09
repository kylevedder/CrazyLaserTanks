/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.teams;

import java.util.ArrayList;
import java.util.Random;
import kylevedder.com.github.entity.AITankEntity;
import kylevedder.com.github.entity.TankEntity;
import kylevedder.com.github.entity.UserTankEntity;
import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.physics.ObjectRegister;
import kylevedder.com.github.slickstates.SlickStateSinglePlayer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Kyle
 */
public class SinglePlayerMatch
{

    private final float IN_FROM_EDGE_POSITIONING = 4f;
    private final float YOUR_START_X = BaseGround.GROUND_SIZE * IN_FROM_EDGE_POSITIONING;
    private final float OPPOSING_START_X = SlickStateSinglePlayer.WORLD_WIDTH * BaseGround.GROUND_SIZE - BaseGround.GROUND_SIZE * IN_FROM_EDGE_POSITIONING;

    private final float TANK_SPACING_Y = BaseGround.GROUND_SIZE * 2f;

    private int teamSize = 0;
    private SinglePlayerMatchData match;
    ArrayList<TankEntity> yourTeam;
    ArrayList<TankEntity> opposingTeam;
    UserTankEntity player;
    Random r;

    /**
     * Generates all the needed entities for a match.
     *
     * @param teamSize
     */
    public SinglePlayerMatch(int teamSize, SinglePlayerMatchData match, UserTankEntity player, ObjectRegister objRegister)
    {
        this.teamSize = teamSize;
        this.match = match;
        this.player = player;
        r = new Random(System.currentTimeMillis());
        yourTeam = new ArrayList<TankEntity>();
        opposingTeam = new ArrayList<TankEntity>();

        AITankEntity yourEntity;
        AITankEntity opposingEntity;

        //setup your team
        for (int i = 0; i < this.teamSize - 1; i++)
        {
            yourEntity = new AITankEntity(YOUR_START_X, TANK_SPACING_Y * (i + 1), r.nextInt(360)/*Random Angle*/, objRegister, this.match.yourTeam, this.match.opposingTeam);
            match.addToYourTeam(yourEntity);
            this.yourTeam.add(yourEntity);
        }
        //setup opposing team
        for (int i = 0; i < this.teamSize; i++)
        {

            opposingEntity = new AITankEntity(OPPOSING_START_X, TANK_SPACING_Y * (i + 1), r.nextInt(360)/*Random Angle*/, objRegister, this.match.opposingTeam, this.match.yourTeam);
            match.addToOpposingTeam(opposingEntity);
            this.opposingTeam.add(opposingEntity);

        }

        for (TankEntity e : yourTeam)
        {
            objRegister.add(e);
        }

        for (TankEntity e : opposingTeam)
        {
            objRegister.add(e);
        }
    }

    /**
     * Updates all Entities in the game.
     *
     * @param input
     * @param delta
     */
    public void update(Input input, int delta)
    {
        this.player.update(input, delta);
        for (TankEntity e : yourTeam)
        {
            e.update(input, delta);
        }
//        for(TankEntity e: opposingTeam)
//        {
//            e.update(input, delta);
//        }
    }

    /**
     * Renders all entities in the game.
     *
     * @param g
     */
    public void render(Graphics g)
    {
        for (TankEntity e : opposingTeam)
        {
            e.render();
            e.renderHelpers(g);
        }
        for (TankEntity e : yourTeam)
        {
            e.render();
            e.renderHelpers(g);
        }
        this.player.render();
        this.player.renderHelpers(g);

        for (TankEntity e : opposingTeam)
        {
            e.renderProjectiles();
        }
        for (TankEntity e : yourTeam)
        {
            e.renderProjectiles();
        }
        this.player.renderProjectiles();
    }

    /**
     * Checks to see if the match is over.
     *
     * @return
     */
    public boolean isMatchOver()
    {
        return this.match.isMatchOver();
    }

    /**
     * Gets the victor of the match.
     *
     * @return
     */
    public String getVictor()
    {
        return this.match.getVictor();
    }
}
