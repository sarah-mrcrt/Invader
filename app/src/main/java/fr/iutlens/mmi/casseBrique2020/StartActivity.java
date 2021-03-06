package fr.iutlens.mmi.casseBrique2020;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        player = MediaPlayer.create(StartActivity.this, R.raw.themesympa);
        player.setVolume(100, 100);
        player.start();
        player.setLooping(true);

        ImageView btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                player.stop();
            }

        });


    }
}
