package com.example.gestiondeinventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesion extends AppCompatActivity {

    Button iniciarsesion;
    EditText usuario, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        mAuth = FirebaseAuth.getInstance();


        usuario = findViewById(R.id.usuario);
        password = findViewById(R.id.password);
        iniciarsesion = findViewById((R.id.iniciarsesion));

        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NombreUsuario = usuario.getText().toString().trim();
                String PassUsuario = password.getText().toString().trim();


                if (NombreUsuario.isEmpty() && PassUsuario.isEmpty()){
                    Toast.makeText(IniciarSesion.this,"Ingrese los Datos" , Toast.LENGTH_SHORT).show();
                }else{
                    iniciarUsuario(NombreUsuario, PassUsuario);
                }


            }
        });


    }

    private void iniciarUsuario(String NombreUsuario, String PassUsuario) {
        mAuth.signInWithEmailAndPassword(NombreUsuario, PassUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(IniciarSesion.this, GestionDeInventario.class));
                    Toast.makeText(IniciarSesion.this,"Bienvenido" , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(IniciarSesion.this,"Error" , Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(IniciarSesion.this,"Error al Iniciar Sesion" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(IniciarSesion.this, GestionDeInventario.class));
            finish();
        }
    }
}