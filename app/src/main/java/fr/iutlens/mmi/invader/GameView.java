package fr.iutlens.mmi.invader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.iutlens.mmi.invader.utils.RefreshHandler;
import fr.iutlens.mmi.invader.utils.SpriteSheet;
import fr.iutlens.mmi.invader.utils.TimerAction;


public class GameView extends View implements TimerAction, View.OnTouchListener {
    private RefreshHandler timer;

    // taille de l'écran virtuel
    public final static int SIZE_X = 2000;
    public final static int SIZE_Y = 2400;

    // transformation (et son inverse)
    private Matrix transform;
    private Matrix reverse;

    //liste des sprites à afficher


    private Armada briques;
    private Raquette raquette;
    private List<Projectile> missile;
    private List<Projectile> balle;


    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    /**
     * Initialisation de la vue
     *
     * Tous les constructeurs (au-dessus) renvoient ici.
     *
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle) {

        // Chargement des feuilles de sprites
        SpriteSheet.register(R.mipmap.alien,2,1,this.getContext());
        SpriteSheet.register(R.mipmap.missile,4,1,this.getContext());
        SpriteSheet.register(R.mipmap.laser,1,1,this.getContext());
        SpriteSheet.register(R.mipmap.canon,1,1,this.getContext());
        SpriteSheet.register(R.mipmap.ball,1,1,this.getContext());

        transform = new Matrix();
        reverse = new Matrix();

        missile = new ArrayList<>();
        balle = new ArrayList<>();

        briques = new Armada(R.mipmap.alien,missile);
        raquette = new Raquette(R.mipmap.canon,800, 2200, balle);


//        hero = new Hero(R.drawable.running_rabbit,SPEED);



        // Gestion du rafraichissement de la vue. La méthode update (juste en dessous)
        // sera appelée toutes les 30 ms
        timer = new RefreshHandler(this);
        timer.scheduleRefresh(30);

        this.setOnTouchListener(this);

    }


    public static void act(List list){
        Iterator it = list.iterator();
        while (it.hasNext()) if (((Sprite) it.next()).act()) it.remove();
    }
    /**
     * Mise à jour (faite toutes les 30 ms)
     */
    @Override
    public void update() {
        if (this.isShown()) { // Si la vue est visible
            timer.scheduleRefresh(30); // programme le prochain rafraichissement

            briques.testIntersection(balle);
            raquette.testIntersection(balle);

            briques.act();
            raquette.act();
            act(missile);
            act(balle);

            invalidate(); // demande à rafraichir la vue
        }
    }

    /**
     * Méthode appelée (automatiquement) pour afficher la vue
     * C'est là que l'on dessine le décor et les sprites
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // On met une couleur de fond
        canvas.drawColor(0xff000077);

        // On choisit la transformation à appliquer à la vue i.e. la position
        // de la "camera"

        canvas.concat(transform);

        for(Sprite s : missile){
            s.paint(canvas);
        }
        for(Sprite s : balle){
            s.paint(canvas);
        }
        raquette.paint(canvas);
        briques.paint(canvas);


        // Dessin des différents éléments
/*        level.paint(canvas,current_pos);

        float x = 1;
        float y = hero.getY();
        hero.paint(canvas,level.getX(x),level.getY(y));
*/
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setZoom(w, h);
    }

    /***
     * Calcul du centrage du contenu de la vue
     * @param w
     * @param h
     */
    private void setZoom(int w, int h) {
        if (w<=0 ||h <=0) return;

        // Dimensions dans lesquelles ont souhaite dessiner
        RectF src = new RectF(0,0,SIZE_X,SIZE_Y);

        // Dimensions à notre disposition
        RectF dst = new RectF(0,0,w,h);

        // Calcul de la transformation désirée (et de son inverse)
        transform.setRectToRect(src,dst, Matrix.ScaleToFit.CENTER);
        transform.invert(reverse);
    }

    public void onLeft() {
        raquette.setDirection(-1);
    }

    public void onRight(){
        raquette.setDirection(+1);
    }

    public void onFire(){
        raquette.fire();

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //Mettre un intervalle de click pour la raqu
        //coord[0] pour X et coord[1] pour Y
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN ||
                motionEvent.getAction() == MotionEvent.ACTION_MOVE  ){
            float coord[] = {motionEvent.getX(),motionEvent.getY()};
            reverse.mapPoints(coord);

            if (coord[1]> SIZE_Y *0.8f){
                raquette.setX(coord[0]);
            }


            return true;

        }

        return false;
    }
}

//Calculer vitesse raquette (Xnouveau = Xancien + V --->  VX= Xnouveau - Xancien)
//Raquette avec coté bout ront donc changer intersecton
//Mettre un intervalle de click pour la raquette (pour ne pas suivre la balle du doigt)
//Jeu finis pouvoir recommencer
//Quand la balle est à l'extérieur, jeu terminé