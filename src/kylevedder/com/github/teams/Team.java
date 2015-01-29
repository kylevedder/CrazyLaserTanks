/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.teams;

import java.util.ArrayList;
import kylevedder.com.github.entity.TankEntity;

/**
 *
 * @author Kyle
 */
public class Team
{

    private ArrayList<TankEntity> teamList = null;
    private String teamName = null;

    /**
     * Holds all the members of a specific team.
     */
    public Team(String teamName)
    {
        this.teamList = new ArrayList<TankEntity>();
        this.teamName = teamName;
    }

    /**
     * Adds a member to the team.
     *
     * @param e
     */
    public void addMember(TankEntity e)
    {
        if (e != null)
        {
            teamList.add(e);
        }
    }
    
    /**
     * Gets the size of the team.
     * @return 
     */
    public int size()
    {
        return this.teamList.size();
    }
    
    /**
     * Gets the team's entity list.
     * @return 
     */
    public ArrayList<TankEntity> getEntityList()
    {
        return teamList;
    }
    
    /**
     * Gets the TankEntity at the given index.
     * @param index
     * @return 
     */
    public TankEntity getEntity(int index)
    {
        return this.teamList.get(index);
    }

    /**
     * Checks if anyone on the team is still alive.
     *
     * @return
     */
    public boolean isTeamAlive()
    {
        for (TankEntity e : teamList)
        {
            if (!e.isDestroyed())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return this.teamName;
    }
    
    
}
