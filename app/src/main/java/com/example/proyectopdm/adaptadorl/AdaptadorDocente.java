package com.example.proyectopdm.adaptadorl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopdm.R;
import com.example.proyectopdm.databinding.ListDocentesBinding;
import com.example.proyectopdm.docente.Docente;

import java.util.ArrayList;

public class AdaptadorDocente extends RecyclerView.Adapter<AdaptadorDocente.DocenteViewHolder> implements View.OnClickListener{
    private ArrayList<Docente> listaDocente;
    private Context context;
    private View.OnClickListener listener;
    RecyclerView rvPrograms;
    final View.OnClickListener onClickListener = new MyOnClickListener();

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class DocenteViewHolder extends RecyclerView.ViewHolder{
        TextView docenteNombre;
        TextView docenteEmail;
        TextView docenteApellido;
        public DocenteViewHolder(@NonNull View itemView) {
            super(itemView);
            docenteNombre = itemView.findViewById(R.id.docenteNombreList);
            docenteEmail = itemView.findViewById(R.id.docenteCorreoList);
            docenteApellido = itemView.findViewById(R.id.docenteApellidoList);
        }
    }
    /*
    public AdaptadorDocente(Context context, ArrayList<Docente> listaDocente, RecyclerView rvPrograms){
        this.context = context;
        this.listaDocente = listaDocente;
        this.rvPrograms = rvPrograms;
    }*/

    public  AdaptadorDocente(ArrayList<Docente> listaDocente, Context context){
        this.listaDocente = listaDocente;

    }

    @Override
    public DocenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_docentes, null, false);

        view.setOnClickListener(this);
        return new DocenteViewHolder(view);
        //return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DocenteViewHolder holder, int position) {
        holder.docenteNombre.setText(String.valueOf(listaDocente.get(position).getNombreTutor()));
        holder.docenteEmail.setText(String.valueOf(listaDocente.get(position).getEmailTutor()));
        holder.docenteApellido.setText(String.valueOf(listaDocente.get(position).getApellidosTutor()));

        //Docente docente = listaDocente.get(position);
        //holder.docenteNombre.setText(""+docente.getNombreTutor());
        //holder.docenteEmail.setText(""+docente.getEmailTutor());
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }

    @Override
    public int getItemCount() {
        return listaDocente.size();
    }
    /*
        public class DocenteViewHolder extends RecyclerView.ViewHolder {
            TextView dato;
            TextView dato2;
            public DocenteViewHolder(@NonNull View itemView) {
                super(itemView);
                dato = itemView.findViewById(R.id.docenteNombreList);
                dato2 = itemView.findViewById(R.id.docenteCorreoList);
            }
        }
    */
    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int itemPosition = rvPrograms.getChildLayoutPosition(view);
            String item = listaDocente.get(itemPosition).getNombreTutor();

            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
        }
    }
}