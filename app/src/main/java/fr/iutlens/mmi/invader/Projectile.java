package fr.iutlens.mmi.invader;

import fr.iutlens.mmi.invader.utils.SpriteSheet;

/**
 * Created by dubois on 06/12/2018.
 */

class Projectile extends Sprite {
    private int vy;
    private int vx;
    private int frame;
    public boolean hitH;
    public boolean hitV;

    public Projectile(int id, float x, float y, int vy,int vx) {
        super(id,x,y);
        this.vy = vy;
        this.vx = vx;
        hitH = false;
        hitV = false;
    }

    @Override
    public boolean act() {
        if (sprite.n>1) state = (frame/3)%sprite.n;
        if (y<0 || hitH){
            vy = -vy;
        }

        if(x<0 || hitV){
            vx = -vy;
        }

        if (x<0 || x+ sprite.w > GameView.SIZE_X){
            vx = -vx;
        }
        y += vy;
        x += vx;
        frame++;

        hitH = false;
        return  y> GameView.SIZE_Y;

    }
}
