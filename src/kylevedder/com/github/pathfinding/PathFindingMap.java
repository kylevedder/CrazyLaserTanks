/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.pathfinding;

import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.ground.GroundHolder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 *
 * @author Kyle
 */
public class PathFindingMap implements TileBasedMap
{
    private GroundHolder gh;

    public PathFindingMap(GroundHolder gh)
    {
        this.gh = gh;
    }

    @Override
    public int getWidthInTiles()
    {
        return gh.getWidth() * BaseGround.GROUND_SIZE;
    }

    @Override
    public int getHeightInTiles()
    {
        return gh.getHeight() * BaseGround.GROUND_SIZE;
    }

    @Override
    public void pathFinderVisited(int x, int y)
    {        
    }

    @Override
    public boolean blocked(PathFindingContext context, int tx, int ty)
    {
        return gh.getGroundArray()[gh.entityXtoGroundX(tx)][gh.entityYtoGroundY(ty)].isCollidable();
    }

    @Override
    public float getCost(PathFindingContext context, int tx, int ty)
    {
        return 1;
    }

}
