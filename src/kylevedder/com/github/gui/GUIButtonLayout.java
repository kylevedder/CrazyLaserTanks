/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.gui;

import java.util.ArrayList;

/**
 *
 * @author Kyle
 */
public class GUIButtonLayout
{

    /**
     * Organizes the given buttons vertically.
     *
     * @param buttons
     * @param verticalSpacing
     * @param centerX
     * @param startY
     * @return
     */
    public static ArrayList<GUIButton> organizeVertically(ArrayList<GUIButton> buttons, float verticalSpacing, float centerX, float startY)
    {
        ArrayList<GUIButton> newButtons = buttons;        
        for (int i = 0; i < newButtons.size(); i++)
        {
            newButtons.get(i).setCenter(centerX, startY + verticalSpacing * i);            
        }
        return newButtons;
    }
}
