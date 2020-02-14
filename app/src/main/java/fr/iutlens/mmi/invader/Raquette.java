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



    //Trajectoire de la balle au premier lancer
    public void fire() {
        laser.add(new Projectile(R.mipmap.ball,x+dxLaser,y+dyLaser,-20, 0));
    }

    //Trajectoire de la balle selon l'interesction de la raquette
    public void testIntersection(List<Projectile> balle) {
        for(Projectile p : laser){
            RectF bbox = p.getBoundingBox();

                if (bbox.intersect(getBoundingBox())){
                    p.hit = true;
                    RectF intersection = new RectF();

                    //Trajcetoire dépendante du centre
                    intersection.setIntersect(bbox,getBoundingBox());
                    if (intersection.width() > intersection.height()){
                        //Quand le projectile est touché de manière horizontale
                        //ici le côté de haut l'écran
                        p.hitH = true;
                        //Changer la trajetoire de la balle (de manière horizontale)
                        p.vx += vx;
                    } //else {
                        //Quand le projectile est touché de manière verticale
                        //ici seulement les côtés gauche et droit de l'écran
                        //p.hitV = true;
                    //}

                    //Trajectoire dépendante des côtés :
                    //1 - Récuperer la Zone carré sur la raquette (intervalle de 0 à 1)
                    //2 - Coté arrondi = hauteur/2 (à gauche et à droite)
                    //3 - Voir les coordonnée de la balle et si elle appartient à l'intervalle de la raquette

                    if(x+sprite.h/2 < bbox.centerX()){
                        p.vx += -5;
                    }
                    if(x+sprite.h/2 > bbox.centerX()){
                        p.vx += 5;
                    }
                }
        }
    }

    public void setX(float x) {
        this.x = x-sprite.w/2;
    }
}

// Rebond vers le haut sur le côté de la raquette
// Problème de rebond au centre