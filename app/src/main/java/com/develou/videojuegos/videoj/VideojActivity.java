package com.develou.videojuegos.videoj;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.develou.videojuegos.R;

public class VideojActivity extends AppCompatActivity {

    public static final String EXTRA_VIDEOJ_ID = "extra_videoj_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoj);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VideojFragment fragment = (VideojFragment)
                getSupportFragmentManager().findFragmentById(R.id.vid_container);

        if (fragment == null) {
            fragment = VideojFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.vid_container, fragment)
                    .commit();
        }
    }
}
