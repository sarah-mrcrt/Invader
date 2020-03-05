package fr.iutlens.mmi.casseBrique2020;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.gameView);

        player = MediaPlayer.create(MainActivity.this, R.raw.themesympa);
        player.setVolume(100, 100);
        player.start();
        player.setLooping(true);

    }


    public void onFire(View view) {
        gameView.onFire();
    }


}
