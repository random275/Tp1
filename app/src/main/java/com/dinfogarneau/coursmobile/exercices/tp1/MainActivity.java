package com.dinfogarneau.coursmobile.exercices.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.app.Activity;

public class MainActivity extends AppCompatActivity {

    public static final String NOM_UTILISATEUR = "NOM_UTILISATEUR";
    public static final String SESSION = "SESSION";
    private Button btn_connexion;
    private EditText EditNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_connexion = (Button)findViewById(R.id.btn_connexion);
        EditNom = (EditText)findViewById(R.id.EditNom);
    }

    //Gère la connection à une session et à la création d'un utilisateur.
    public void connectionUtilisateur (View view){
        String nom = EditNom.getText().toString();
        if (EditNom.getText().length() == 0){
            Toast.makeText(MainActivity.this, getString(R.string.errUtilisateur), Toast.LENGTH_LONG).show();
        }
        else {

            SharedPreferences sharedPreferences = getSharedPreferences("Utilisateur", Context.MODE_PRIVATE);
            //Créer un utilisateur si il n'y en a pas de se nom et met son nom dans la session.
            if (!sharedPreferences.contains(nom)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SESSION, nom);
                editor.putInt(nom, 15);
                editor.commit();
            }
            //Met le nom de l'utilisateur dans la session, si celui-ci existe déjà
            else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SESSION, nom);
                editor.commit();
            }

            //Débute l'activitée.
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(NOM_UTILISATEUR, nom);
            startActivity(intent);
        }
    }
}