package com.dinfogarneau.coursmobile.exercices.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ToisiemeActivity extends AppCompatActivity{

    private Button getJetons;
    private EditText nbJetons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troisieme);

        getJetons = (Button) findViewById(R.id.getJetons);
        nbJetons = (EditText) findViewById(R.id.nbJetons);

        //Termine l'activité et envoi le nombre de jeton a ajouté.
        getJetons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nbJetons.getText().toString().length() == 0){
                    Toast.makeText(ToisiemeActivity.this, getString(R.string.valeurInv), Toast.LENGTH_SHORT).show();
                }
                else {
                    int jeton = Integer.parseInt(nbJetons.getText().toString());
                    Intent addJetons = new Intent();
                    addJetons.putExtra("jeton", jeton);
                    setResult(1, addJetons);
                    finish();
                }
            }
        });
    }

}
