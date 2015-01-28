/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.teams;

import java.util.ArrayList;
import java.util.Random;
import kylevedder.com.github.entity.TankEntity;
import kylevedder.com.github.entity.UserTankEntity;
import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.main.GameEngine;
import kylevedder.com.github.physics.ObjectRegister;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Kyle
 */
public class SinglePlayerMatchGenerator
{

    private final float IN_FROM_EDGE_POSITIONING = 4f;
    private final float YOUR_START_X = BaseGround.GROUND_SIZE * IN_FROM_EDGE_POSITIONING;
    private final float OPPOSING_START_X = GameEngine.WORLD_WIDTH * BaseGround.GROUND_SIZE - BaseGround.GROUND_SIZE * IN_FROM_EDGE_POSITIONING;

    private final float TANK_SPACING_Y = BaseGround.GROUND_SIZE * 2f;

    private int teamSize = 0;
    private SinglePlayerMatch match;
    ArrayList<TankEntity> yourTeam;
    ArrayList<TankEntity> opposingTeam;
    UserTankEntity player;
    Random r;

    /**
     * Generates all the needed entities for a match.
     *
     * @param teamSize
     */
    public SinglePlayerMatchGenerator(int teamSize, SinglePlayerMatch match, UserTankEntity player, ObjectRegister objRegister)
    {
        this.teamSize = teamSize;
        this.match = match;
        this.player = player;
        r = new Random(System.currentTimeMillis());
        yourTeam = new ArrayList<TankEntity>();
        opposingTeam = new ArrayList<TankEntity>();

        TankEntity yourEntity;
        TankEntity opposingEntity;

        //setup your team
        for (int i = 0; i < this.teamSize - 1; i++)
        {
            yourEntity = new TankEntity(YOUR_START_X, TANK_SPACING_Y * (i + 1), r.nextInt(360)/*Random Angle*/);
            match.addToYourTeam(yourEntity);
            this.yourTeam.add(yourEntity);
        }        
        //setup opposing team
        for (int i = 0; i < this.teamSize; i++)
        {

            opposingEntity = new TankEntity(OPPOSING_START_X, TANK_SPACING_Y * (i + 1), r.nextInt(360)/*Random Angle*/);
            match.addToOpposingTeam(opposingEntity);
            this.opposingTeam.add(opposingEntity);

        }
        
        for(TankEntity e: yourTeam)
        {
            objRegister.add(e);
        }
        
        for(TankEntity e: opposingTeam)
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
        for(TankEntity e: yourTeam)
        {
            e.update(input, delta);
        }
        for(TankEntity e: opposingTeam)
        {
            e.update(input, delta);
        }
    }

    /**
     * Renders all entities in the game.
     *
     * @param g
     */
    public void render(Graphics g)
    {                
        for(TankEntity e: opposingTeam)
        {
            e.render();
            e.renderHelpers(g);
        }    
        for(TankEntity e: yourTeam)
        {
            e.render();
            e.renderHelpers(g);
        }
        this.player.render();
        this.player.renderHelpers(g);
        this.match.render(g);
    }

}