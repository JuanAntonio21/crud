package com.develou.videojuegos.videojdetail;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.develou.videojuegos.R;
import com.develou.videojuegos.addeditvideoj.AddEditVideojActivity;
import com.develou.videojuegos.data.Videoj;
import com.develou.videojuegos.data.VideojDbHelper;
import com.develou.videojuegos.videoj.VideojActivity;
import com.develou.videojuegos.videoj.VideojFragment;

/**
 * Vista para el detalle del abogado
 */
public class VideojDetailFragment extends Fragment {
    private static final String ARG_VIDEOJ_ID = "videoId";

    private String videojId;

    private CollapsingToolbarLayout view;
    private ImageView mAvatar;
    private TextView Precio;
    private TextView Genero;
    private TextView Descripcion;

    private VideojDbHelper ayuda;


    public VideojDetailFragment() {
        // Required empty public constructor
    }

    public static VideojDetailFragment newInstance(String lawyerId) {
        VideojDetailFragment fragment = new VideojDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEOJ_ID, lawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            videojId = getArguments().getString(ARG_VIDEOJ_ID);
        }

        setHasOptionsMenu(true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_videoj_detail, container, false);
        view = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        Precio = (TextView) root.findViewById(R.id.tv_precio);
        Genero = (TextView) root.findViewById(R.id.tv_genero);
        Descripcion = (TextView) root.findViewById(R.id.tv_desc);

        ayuda = new VideojDbHelper(getActivity());

        loadVideoJ();

        return root;
    }

    private void loadVideoJ() {
        new GetVidejoJByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar_ac:
                showEditScreen();
                break;
            case R.id.borrar_ac:
                new DeleteVideojTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VideojFragment.REQUEST_UPDATE_DELETE_GAME) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showVideoJ(Videoj vid) {
        view.setTitle(vid.getNombre());
        Glide.with(getContext())
                .load(Uri.parse("file:///android_asset/" + vid.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        Precio.setText(vid.getPrecio());
        Genero.setText(vid.getGen());
        Descripcion.setText(vid.getDes());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditVideojActivity.class);
        intent.putExtra(VideojActivity.EXTRA_VIDEOJ_ID, videojId);
        startActivityForResult(intent, VideojFragment.REQUEST_UPDATE_DELETE_GAME);
    }

    private void showVideoJScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar JUEGO", Toast.LENGTH_SHORT).show();
    }

    private class GetVidejoJByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return ayuda.getVideojById(videojId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showVideoJ(new Videoj(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteVideojTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return ayuda.deleteVid(videojId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showVideoJScreen(integer > 0);
        }

    }

}
