package com.develou.videojuegos.videoj;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.develou.videojuegos.R;
import com.develou.videojuegos.addeditvideoj.AddEditVideojActivity;
import com.develou.videojuegos.data.VideojDbHelper;
import com.develou.videojuegos.videojdetail.VideojDetailActivity;

import static com.develou.videojuegos.data.VideojContract.VideojEntry;



public class VideojFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_GAME = 2;

    private VideojDbHelper ayuda;

    private ListView lista;
    private VideojCursorAdapter adapter;
    private FloatingActionButton añadirB;


    public VideojFragment() {
        // Required empty public constructor
    }

    public static VideojFragment newInstance() {
        return new VideojFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_videoj, container, false);

        // Referencias UI
        lista = (ListView) root.findViewById(R.id.videojuegos_list);
        adapter = new VideojCursorAdapter(getActivity(), null);
        añadirB = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        lista.setAdapter(adapter);

        // Eventos
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) adapter.getItem(i);
                @SuppressLint("Range") String currentVideojuegosId = currentItem.getString(
                        currentItem.getColumnIndex(VideojEntry.ID));

                showDetailScreen(currentVideojuegosId);
            }
        });
        añadirB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(VideojDbHelper.DATABASE_NAME);

        // Instancia de helper
        ayuda = new VideojDbHelper(getActivity());

        // Carga de datos
        loadVideojuegos();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditVideojActivity.REQUEST_ADD_VIDEOJ:
                    showSuccessfullSavedMessage();
                    loadVideojuegos();
                    break;
                case REQUEST_UPDATE_DELETE_GAME:
                    loadVideojuegos();
                    break;
            }
        }
    }

    private void loadVideojuegos() {
        new VideojuegosLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Videojuego guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditVideojActivity.class);
        startActivityForResult(intent, AddEditVideojActivity.REQUEST_ADD_VIDEOJ);
    }

    private void showDetailScreen(String vidId) {
        Intent intent = new Intent(getActivity(), VideojDetailActivity.class);
        intent.putExtra(VideojActivity.EXTRA_VIDEOJ_ID, vidId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_GAME);
    }

    private class VideojuegosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return ayuda.getAllVid();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                adapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
