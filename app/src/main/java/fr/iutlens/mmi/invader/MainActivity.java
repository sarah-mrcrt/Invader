package fr.iutlens.mmi.invader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.gameView);
    }


    public void onFire(View view) {
        gameView.onFire();
    }
}
