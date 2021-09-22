package com.dinfogarneau.coursmobile.exercices.tp1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


public class SecondActivity extends AppCompatActivity{

    public static final String SESSION = "SESSION";

    public TextView salutUser;
    private Button btnBanque;
    private Button btnRoulette;

    int jetons;
    String nom;

    ActivityResultLauncher<Intent> resultatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Charger();

        salutUser = (TextView) findViewById(R.id.salutUser);
        btnBanque = (Button) findViewById(R.id.btnBanque);
        btnRoulette = (Button) findViewById(R.id.btnRoulette);

        Affichage();
        
        resultatActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    int nvJeton = data.getIntExtra("jeton", 0);

                    int code = result.getResultCode();
                    if(code == 1){
                        //salutUser.setText("Nice" + nvJeton);
                        jetons += nvJeton;
                        Affichage();
                        Enregistrement();
                    }

                }

            });

        //Envoi à l'activité de banque.
        btnBanque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(SecondActivity.this, ToisiemeActivity.class);
                    resultatActivity.launch(intent);
            }
        });
        //Ouvre l'activité de Roulette.
        btnRoulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jetons != 0){
                    Intent intent = new Intent(SecondActivity.this, QuatriemeActivity.class);
                    resultatActivity.launch(intent);
                }
                else {
                    Intent intent = new Intent(SecondActivity.this, ToisiemeActivity.class);
                    Toast.makeText(SecondActivity.this, getString(R.string.banqueOblige), Toast.LENGTH_SHORT).show();
                    resultatActivity.launch(intent);
                }
            }
        });
    }
    //Met l'affichage a jour.
    public void Affichage(){
        salutUser.setText(getString(R.string.Bonjour) + " " +nom + "; " + getString(R.string.nbJeton) + jetons);
    }
    //Enregistre le nombre de jetons.
    public void Enregistrement(){
        SharedPreferences sharedPreferences = getSharedPreferences("Utilisateur", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(nom, jetons);
        editor.commit();
    }
    //Charge le nom d'utilisateur et le nombre de jetons.
    public void Charger(){
        SharedPreferences sharedPreferences = getSharedPreferences("Utilisateur", Context.MODE_PRIVATE);
        nom = sharedPreferences.getString(SESSION, "").toString();
        jetons = sharedPreferences.getInt(nom, 0);
    }
}
