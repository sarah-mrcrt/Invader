package fr.iutlens.mmi.invader;

import android.graphics.RectF;

import java.util.List;

import fr.iutlens.mmi.invader.utils.SpriteSheet;

/**
 * Created by dubois on 05/12/2018.
 */

class Raquette extends Sprite {
    private final List<Projectile> laser;
    private final int dxLaser;
    private final int dyLaser;
    float vx;
    private float x_old;

    Raquette(int id, float x, float y, List<Projectile> laser) {
        super(id, x, y);
        this.laser = laser;
        final SpriteSheet laserSprite = SpriteSheet.get(R.mipmap.laser);
        dxLaser = sprite.w/2- laserSprite.w/2;
        dyLaser = -laserSprite.h;
        x_old = x;
        vx=0;
    }

    @Override
    public boolean act() {
        RectF bounds = getBoundingBox();
        //Calcule de la nouvelle vitesse de la Raquette (celle-ci influe sur la balle)
        vx = x - x_old;
        x_old = x;

        if(vx>15) {
            //Vitesse maximum que peut donner la raquette
            vx = 15;
        }

        return false;

    }



    public void fire() {
        laser.add(new Projectile(R.mipmap.ball,x+dxLaser,y+dyLaser,-20, 5));
    }

    public void testIntersection(List<Projectile> balle) {
        for(Projectile p : laser){
            RectF bbox = p.getBoundingBox();

                if (bbox.intersect(getBoundingBox())){
                    p.hit = true;
                    RectF intersection = new RectF();

                    //Trajcetoire dépendante du centre

                    //Trajectoire dépendante des côtés
                    intersection.setIntersect(bbox,getBoundingBox());
                    if (intersection.width() > intersection.height()){
                        //Quand le projectile est touché de manière horizontale
                        //ici seulement le côté de l'écran
                        p.hitH = true;
                        //Changer la trajetoire de la balle (de manière horizontale)
                        p.vx += vx;
                    } else {
                        //Quand le projectile est touché de manière verticale
                        //ici seulement les côtés de l'écran
                        p.hitV = true;
                    }
                }
        }
    }

    public void setX(float x) {
        this.x = x-sprite.w/2;
    }
}
//Mettre une interface (jouer)
//2 vies
//Raquette avec coté bout rond donc changer intersection