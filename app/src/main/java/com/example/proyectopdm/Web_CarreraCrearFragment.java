package com.example.proyectopdm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Web_CarreraCrearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Web_CarreraCrearFragment extends Fragment {

    EditText webtxtNombreEscuela, web_txtNomCarrera, web_idArea;
    Button web_btn1;
    RequestQueue requestQueue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Web_CarreraCrearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Web_CarreraCrearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Web_CarreraCrearFragment newInstance(String param1, String param2) {
        Web_CarreraCrearFragment fragment = new Web_CarreraCrearFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web__carrera_crear, container, false);
        Button btnInsertarWeb = v.findViewById(R.id.webbtncarreraCrear);
        web_idArea = (TextInputEditText) v.findViewById(R.id.webTxtidArea);
        web_txtNomCarrera = (TextInputEditText) v.findViewById(R.id.webNombreCarrera);
        webtxtNombreEscuela = (TextInputEditText) v.findViewById(R.id.webtxtNombreEscuela);

        btnInsertarWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarInsersionWeb2("http://192.168.0.6:80/PDMGRUPO11/ws_insertar_carrera.php");
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void ejecutarInsersionWeb2(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Exito!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                String idArea = web_idArea.getText().toString();
                parametros.put("IDAREA", idArea);
                parametros.put("NOMBRECARRERA", web_txtNomCarrera.getText().toString());
                parametros.put("NOMBREESCUELA", webtxtNombreEscuela.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}