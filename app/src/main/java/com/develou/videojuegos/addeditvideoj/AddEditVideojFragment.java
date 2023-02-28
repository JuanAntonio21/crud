package com.develou.videojuegos.addeditvideoj;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.develou.videojuegos.R;
import com.develou.videojuegos.data.Videoj;
import com.develou.videojuegos.data.VideojDbHelper;

import androidx.fragment.app.Fragment;
public class AddEditVideojFragment extends Fragment {
    private static final String ARG_VIDEOJ_ID = "arg_videoj_id";

    private String videojId;

    private VideojDbHelper ayuda;

    private FloatingActionButton guardar;
    private TextInputEditText nombre;
    private TextInputEditText precio;
    private TextInputEditText genero;
    private TextInputEditText desc;
    private TextInputLayout nombreL;
    private TextInputLayout precioL;
    private TextInputLayout generoL;
    private TextInputLayout descL;
    private ImageView avatarM;

    public AddEditVideojFragment() {
        // Required empty public constructor
    }

    public static AddEditVideojFragment newInstance(String vidId) {
        AddEditVideojFragment fragment = new AddEditVideojFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEOJ_ID, vidId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videojId = getArguments().getString(ARG_VIDEOJ_ID);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_videoj, container, false);


        guardar = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        nombre = (TextInputEditText) root.findViewById(R.id.nombre);
        precio = (TextInputEditText) root.findViewById(R.id.et_precio);
        genero = (TextInputEditText) root.findViewById(R.id.et_genero);
        desc = (TextInputEditText) root.findViewById(R.id.et_desc);
        nombreL = (TextInputLayout) root.findViewById(R.id.til_nombre);
        precioL = (TextInputLayout) root.findViewById(R.id.til_phone_number);
        generoL = (TextInputLayout) root.findViewById(R.id.til_genero);
        descL = (TextInputLayout) root.findViewById(R.id.til_desc);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditVid();
            }

        });

        ayuda = new VideojDbHelper(getActivity());

        // Carga de datos
        if (videojId != null) {
            loadVid();
        }

        return root;
    }

    private void loadVid() {
        new GetVidByIdTask().execute();
    }

    private void addEditVid() {
        boolean error = false;

        String nom = nombre.getText().toString();
        String prec = precio.getText().toString();
        String gen = genero.getText().toString();
        String des = desc.getText().toString();

        if (TextUtils.isEmpty(nom)) {
            nombreL.setError(getString(R.string.borrar_ac));
            error = true;
        }

        if (TextUtils.isEmpty(prec)) {
            precioL.setError(getString(R.string.borrar_ac));
            error = true;
        }

        if (TextUtils.isEmpty(gen)) {
            generoL.setError(getString(R.string.borrar_ac));
            error = true;
        }


        if (TextUtils.isEmpty(des)) {
            descL.setError(getString(R.string.borrar_ac));
            error = true;
        }

        if (error) {
            return;
        }

        Videoj vid = new Videoj(nom, gen, prec, des, "");

        new AddEditVidTask().execute(vid);

    }

    private void showVideojScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showvideoJ(Videoj vid) {
        nombre.setText(vid.getNombre());
        precio.setText(vid.getPrecio());
        genero.setText(vid.getGen());
        desc.setText(vid.getDes());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar videoj", Toast.LENGTH_SHORT).show();
    }

    private class GetVidByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return ayuda.getVideojById(videojId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showvideoJ(new Videoj(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditVidTask extends AsyncTask<Videoj, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Videoj... vid) {
            if (videojId != null) {
                return ayuda.updateVideoj(vid[0], videojId) > 0;

            } else {
                return ayuda.saveVid(vid[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showVideojScreen(result);
        }

    }

}
