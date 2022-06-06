package com.example.proyectopdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables para HttpurlConnection
    EditText editText;
    Button btn;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    //Declaracion de variables
    EditText user,pass;
    Button btnEntrar,btnRegistar;
    BD db;
    DT dt;

    //Variable para libreria de audio
    Button addVoice;

    //Variables para libreria de Imagenes
    ImageView imgGallery;
    Button btnGallery;
    private  final int GALLERY_REQ_CODE = 1000;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conectar a Firebase
        editText=findViewById(R.id.editFile);
        btn=findViewById(R.id.btnFile);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("proyectoPDM");

        btn.setEnabled(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDF();
            }
        });



        //Enlazar las variables con el contenido del layout

        user=(EditText) findViewById(R.id.user);
        pass=(EditText) findViewById(R.id.password);
        btnEntrar=(Button) findViewById(R.id.btnIngresar);

        //Codigo para libreria de imagenes
        imgGallery=(ImageView) findViewById(R.id.imgPerfil);
        btnGallery=(Button) findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });




       // btnRegistar=(Button) findViewById(R.id.btnRegistrarse);
        db=new BD(this);

        //Asignacion de eventos a los botones
        btnEntrar.setOnClickListener(this);
        //btnRegistar.setOnClickListener(this);

        db.abrir();
        db.llenarDB();
        db.cerrar();
    }

    //Connection for Httpurlconnection
    private void selectPDF(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);
    }

    //Codigo para libreria de audio
    public void getSpeechInput(View view) {

		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(intent, 10);
		} else {
			Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
		}
	}

    //Codigo para libreria de audio
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		//for connection
        if(requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData()!=null){
            btn.setEnabled(true);
            editText.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    uploadPDFFileFirebase(data.getData());
                }
            });
        }

		//for image
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                imgGallery.setImageURI(data.getData());
            }
        }

        //for voice
		switch (requestCode) {
			case 10:
				if (resultCode == RESULT_OK && data != null) {
					ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					user.setText(result.get(0));
				}
				break;
		}
	}

	//codigo para htpurlconnection
    private void uploadPDFFileFirebase(Uri data){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();
        StorageReference reference = storageReference.child("uploadPDF"+System.currentTimeMillis()+".pdf");

        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri=uriTask.getResult();
                PutPDF putPDF = new PutPDF(editText.getText().toString(),uri.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);
                Toast.makeText(MainActivity.this, "File Upload", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot){

                double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File uploaded..."+(int) progress + "%");

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //Definicion de comportamiento de los botones
             case R.id.btnIngresar:
                 db.abrir();
                String u=user.getText().toString();
                String p=pass.getText().toString();
                if(u.equals("")||p.equals("")){
                    Toast.makeText(this, "ERROR: Campos vacios", Toast.LENGTH_SHORT).show();
                }else if(db.login(u,p)==1){

                    Usuario us =db.getUsuario(u,p);
                    dt=new DT();
                    dt.setIdU(us.getId());
                    db.insertarDT(dt);

                    if(db.consultarNivelAcceso(dt.getIdU())==1||db.consultarNivelAcceso(dt.getIdU())==2){
                        Toast.makeText(this, "DATOS CORRECTOS", Toast.LENGTH_SHORT).show();
                        Intent i3=new Intent(MainActivity.this,MenuOpcionesActivity.class);
                        // Pasando el id del usuario
                        i3.putExtra("id",us.getId());
                        startActivity(i3);
                    }else{
                        Toast.makeText(this, "DATOS CORRECTOS", Toast.LENGTH_SHORT).show();
                        Intent i9=new Intent(MainActivity.this,MenuOpciones2Activity.class);
                        // Pasando el id del usuario
                        i9.putExtra("id",us.getId());
                        startActivity(i9);
                    }

                    user.setText("");
                    pass.setText("");
                db.cerrar();
                }else{
                    Toast.makeText(this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
                break;
            /*case R.id.btnRegistrarse:
                Intent i=new Intent(MainActivity.this,RegistrarUsuarioActivity.class);
                startActivity(i);
                break;*/
        }
    }
}