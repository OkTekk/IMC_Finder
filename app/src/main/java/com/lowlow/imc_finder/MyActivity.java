package com.lowlow.imc_finder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {
    private EditText poids = null, taille = null;
    private RadioGroup mesures;
    private CheckBox superMode = null;
    private Button startButton = null, razButton = null;
    private TextView resultat = null;

    private final String megaString = "Paré pour vaincre Cell ! Trop fort :D";
    private final String tFloatZero = "0 Cm ?\nQuand tu cours l'herbe doit te chatouiller les aisselles xD";
    private final String pFloatZero = "0 Kg ?\nT'es une gymnaste Russe ou quoi?...";
    private final String ptFloatZero = "http://fr.wikipedia.org/wiki/Impesanteur";
    private final String mesuresZero = "Cochez une unité de mesure...";
    private final String enterValues = "Veuillez entrer des valeurs !";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_main_linear);
        setContentView(R.layout.activity_main_relative);
//		setContentView(R.layout.activity_main_tab);

        poids = (EditText) findViewById(R.id.poids2);
        poids.addTextChangedListener(textWatcher);

        taille = (EditText) findViewById(R.id.taille2);
        taille.addTextChangedListener(textWatcher);

        mesures = (RadioGroup) findViewById(R.id.mesures);

        superMode = (CheckBox) findViewById(R.id.superMode);
        superMode.setOnClickListener(superWatcher);

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(startWatcher);

        razButton = (Button) findViewById(R.id.razButton);
        razButton.setOnClickListener(razWatcher);

        resultat = (TextView) findViewById(R.id.resultView);
    }

    public float CalcIMC(float poids, float taille) {
        if(mesures.getCheckedRadioButtonId() == R.id.centimetre) {
            taille = taille / 100; }

        taille = (float) Math.pow(taille, 2);
        float imc = poids / taille;

        return imc;
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            resultat.setText(R.string.result2);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    private OnClickListener superWatcher = new OnClickListener(){

        @Override
        public void onClick(View v) {
            if(!((CheckBox) v).isChecked() && resultat.getText().equals(megaString)) {
                resultat.setText(R.string.result2); }
        }
    };

    private OnClickListener startWatcher = new OnClickListener(){

        @Override
        public void onClick(View v) {
            if(!superMode.isChecked()) {
                String pString = poids.getText().toString();
                String tString = taille.getText().toString();

                if(mesures.getCheckedRadioButtonId() == R.id.centimetre || mesures.getCheckedRadioButtonId() == R.id.metre) {
                    if(pString.length() == 0 || tString.length() == 0) {
                        Toast.makeText(MyActivity.this, enterValues, Toast.LENGTH_SHORT).show(); }

                    else {
                        float pFloat = Float.valueOf(pString);
                        float tFloat = Float.valueOf(tString);

                        if(pFloat == 0 && tFloat == 0){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ptFloatZero));
                            startActivity(browserIntent); }

                        else if(pFloat == 0) {
                            Toast.makeText(MyActivity.this, pFloatZero, Toast.LENGTH_SHORT).show(); }

                        else if(tFloat == 0) {
                            Toast.makeText(MyActivity.this, tFloatZero, Toast.LENGTH_SHORT).show(); }

                        else { resultat.setText("Votre IMC est " + String.valueOf(CalcIMC(pFloat, tFloat))); }}}

                else { Toast.makeText(MyActivity.this, mesuresZero, Toast.LENGTH_SHORT).show(); }}

            else { resultat.setText(megaString); }
        }
    };

    private OnClickListener razWatcher = new OnClickListener(){

        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();

            resultat.setText(R.string.result2);
        }
    };
}