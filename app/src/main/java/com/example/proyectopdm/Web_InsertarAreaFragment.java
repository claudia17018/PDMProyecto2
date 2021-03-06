package com.example.proyectopdm;

import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Web_InsertarAreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Web_InsertarAreaFragment extends Fragment {
    EditText web_txtNomArea, web_txtDesArea;
    Button web_btn1;
    RequestQueue requestQueue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Web_InsertarAreaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Web_InsertarAreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Web_InsertarAreaFragment newInstance(String param1, String param2) {
        Web_InsertarAreaFragment fragment = new Web_InsertarAreaFragment();
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
        View v = inflater.inflate(R.layout.fragment_web__insertar_area, container, false);
        Button btnInsertarWeb = v.findViewById(R.id.web_btnCrear2Area);
        web_txtDesArea = (TextInputEditText) v.findViewById(R.id.web_textCreaAreaDes);

        web_txtNomArea = (TextInputEditText) v.findViewById(R.id.web_textCrearNomArea);

        btnInsertarWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarInsersionWeb("http://192.168.0.6:80/PDMGRUPO11/ws_insertar_area.php");
                //consultarestudiante("http://192.168.0.6:80/PDMGRUPO11/ws_consultar_estudiante.php?carnet='ML17018'");
            }
        });
        // Inflate the layout for this fragment
        return v;
    }


    private void ejecutarInsersionWeb(String URL){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Exito!",Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){

                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.print(web_txtNomArea.getText().toString()+"+++++++++++++++++++++++++++++++++++++++++++");
                Map<String, String> parametros = new HashMap<String, String>();
                System.out.print(web_txtNomArea.getText().toString()+"---------------------------------------");
                String nombreArea = web_txtNomArea.getText().toString();
                System.out.print(nombreArea+"----------------------------------------------");
                parametros.put("NOMBREAREA", nombreArea);

                parametros.put("DESCRIPCIONAREA",web_txtDesArea.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    /*
    private void consultarestudiante(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        web_txtDesArea.setText(jsonObject.getString("NOMBRESTUDIANTE"));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"ERROR!!",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }*/
}