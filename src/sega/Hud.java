/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sega;

import java.awt.*;
import java.awt.image.BufferedImage;

import static sega.Const.Hud.LIVE;
import static sega.Const.Hud.MONEY;

/**
 *
 * @author druger
 */
public class Hud {

    private int health;
    private int money;
    
    private BufferedImage live;
    private BufferedImage mon;
    private Font font;
    
    public Hud(){
        health = 4;
        money = 0;
        loadImage();
    }

    private void loadImage() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            live = loader.loadImage(LIVE);
            mon = loader.loadImage(MONEY);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getHealth(){ return health;}
    public int getMoney() { return money;}

    public void render(Graphics g) {
        font = new Font("Arial", Font.PLAIN, 20);
        g.drawImage(mon, 760, 565, null);
        g.drawImage(live, 10, 575, null);
        g.setFont(font);
        g.setColor(Color.blue);
        g.drawString( "x " + health, 45, 595);
        g.drawString(money + " x", 730, 595);
    }

}
