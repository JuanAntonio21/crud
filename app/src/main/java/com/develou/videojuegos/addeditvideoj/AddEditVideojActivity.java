package com.develou.videojuegos.addeditvideoj;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.develou.videojuegos.R;
import com.develou.videojuegos.videoj.VideojActivity;

public class AddEditVideojActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_VIDEOJ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String videojId = getIntent().getStringExtra(VideojActivity.EXTRA_VIDEOJ_ID);

        setTitle(videojId == null ? "Añadir juego" : "Editar juego");

        AddEditVideojFragment addEditVideojFragment = (AddEditVideojFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_vidj_container);
        if (addEditVideojFragment == null) {
            addEditVideojFragment = AddEditVideojFragment.newInstance(videojId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_vidj_container, addEditVideojFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
