package com.dinfogarneau.coursmobile.exercices.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuatriemeActivity extends AppCompatActivity{

    public static final String SESSION = "SESSION";

    int jetons;
    String nom;
    Boolean gameMode;

    private EditText editMise;
    private EditText editPredit;
    private Button btnJouerPair;
    private Button btnJouerPredit;
    private TextView mJNombre;
    private TextView jetonRestant;
    private RadioGroup pairImpair;
    private RadioButton pair;
    private RadioButton impair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quatrieme);

        editMise = (EditText) findViewById(R.id.editMise);
        editPredit = (EditText) findViewById(R.id.editPredit);
        btnJouerPair = (Button) findViewById(R.id.btnJouerPair);
        btnJouerPredit = (Button) findViewById(R.id.btnJouerPredit);
        mJNombre = (TextView) findViewById(R.id.mJNombre);
        jetonRestant = (TextView) findViewById(R.id.jetonRestant);
        pair = (RadioButton) findViewById(R.id.pair);
        impair = (RadioButton) findViewById(R.id.impair);

        Charger();

        Affichage();

        //Gère le jeu à édition de nombre
        btnJouerPredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMise.getText().toString().length() == 0){
                    Toast.makeText(QuatriemeActivity.this, getString(R.string.errMise), Toast.LENGTH_SHORT).show();
                }
                else if (editPredit.getText().toString().length() == 0){
                    Toast.makeText(QuatriemeActivity.this, getString(R.string.errPrediction), Toast.LENGTH_SHORT).show();
                }
                else {
                    int mise = Integer.parseInt(editMise.getText().toString());
                    if (jetons == 0){
                        Toast.makeText(QuatriemeActivity.this, getString(R.string.errPasJetons), Toast.LENGTH_SHORT).show();
                    }
                    else if (mise > jetons){
                        Toast.makeText(QuatriemeActivity.this, getString(R.string.errJetons), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Random random = new Random();
                        int nb = random.nextInt(6);
                        String nbs = String.valueOf(nb);
                        if (Integer.parseInt(editPredit.getText().toString()) == nb){
                            Gain(mise);
                            Charger();
                            Affichage();
                            Toast.makeText(QuatriemeActivity.this, getString(R.string.victoire) + " " + editMise.getText().toString(), Toast.LENGTH_SHORT).show();
                            Clear();
                        }
                        else {
                            Perte(mise);
                            Charger();
                            Affichage();
                            Clear();
                            Toast.makeText(QuatriemeActivity.this, nbs, Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
        //Gère le jeu à prédiction de pair ou impair.
        btnJouerPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMise.getText().toString().length() == 0){
                    Toast.makeText(QuatriemeActivity.this, getString(R.string.errMise), Toast.LENGTH_SHORT).show();
                }
                else if (!pair.isChecked() && !impair.isChecked()){
                    Toast.makeText(QuatriemeActivity.this, getString(R.string.errRadio), Toast.LENGTH_SHORT).show();
                }
                else {
                    int mise = Integer.parseInt(editMise.getText().toString());
                    if (jetons == 0){
                        Toast.makeText(QuatriemeActivity.this, getString(R.string.errPasJetons), Toast.LENGTH_SHORT).show();
                    }
                    else if (mise > jetons){
                        Toast.makeText(QuatriemeActivity.this, getString(R.string.errJetons), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Random random = new Random();
                        int nb = random.nextInt(6);
                        if (pair.isChecked()){
                            if ((nb/2)*2 == nb){
                                Gain(mise);
                                Charger();
                                Affichage();
                                Clear();
                                Toast.makeText(QuatriemeActivity.this, nb, Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Perte(mise);
                                Charger();
                                Affichage();
                                Clear();
                                Toast.makeText(QuatriemeActivity.this, nb, Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            if ((nb/2)*2 == nb){
                                Perte(mise);
                                Charger();
                                Affichage();
                                Clear();
                            }
                            else {
                                Gain(mise);
                                Charger();
                                Affichage();
                                Clear();
                            }
                        }
                    }
                }
            }
        });
    }
    //Charge le nom d'utilisateur et le nombre de jetons.
    public void Charger(){
        SharedPreferences sharedPreferences = getSharedPreferences("Utilisateur", Context.MODE_PRIVATE);
        nom = sharedPreferences.getString(SESSION, "").toString();
        jetons = sharedPreferences.getInt(nom, 0);
    }
    //Enregistre les jetons gagnés
    public void Gain(int gain){
        int val;
        SharedPreferences sharedPreferences = getSharedPreferences("Utilisateur", Context.MODE_PRIVATE);
        val = sharedPreferences.getInt(nom, 0);
        val = val + gain;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(nom, val);
        editor.commit();
    }
    //Enregistre les jetons perdus
    public void Perte(int perte){
        int val;
        SharedPreferences sharedPreferences = getSharedPreferences("Utilisateur", Context.MODE_PRIVATE);
        val = sharedPreferences.getInt(nom, 0);
        val = val - perte;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(nom, val);
        editor.commit();
    }
    //Met à jour l'affichage des jetons restants.
    public void Affichage(){
        jetonRestant.setText(getString(R.string.Jeton).toString() + " " + jetons);
    }
    //Vide les zones d'édition
    public void Clear(){
        editMise.getText().clear();
        editPredit.getText().clear();
        pair.setChecked(false);
        impair.setChecked(false);
    }
}
