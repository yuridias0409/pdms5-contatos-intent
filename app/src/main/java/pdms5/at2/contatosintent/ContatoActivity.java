package pdms5.at2.contatosintent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import pdms5.at2.contatosintent.Model.Contato;
import pdms5.at2.contatosintent.databinding.ActivityContatoBinding;

public class ContatoActivity extends AppCompatActivity {
    private ActivityContatoBinding activityContatoBinding;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ligando (binding) objetos com as Views
        activityContatoBinding = ActivityContatoBinding.inflate(getLayoutInflater());
        setContentView(activityContatoBinding.getRoot());

        //Libera o campo de celular
        activityContatoBinding.cellSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityContatoBinding.cellSt.isChecked()){
                    activityContatoBinding.cellEt.setVisibility(View.VISIBLE);
                }   else{
                    activityContatoBinding.cellEt.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onClick(View view){
        Contato contato = new Contato(
                activityContatoBinding.nomeEt.getText().toString(),
                activityContatoBinding.telefoneEt.getText().toString(),
                activityContatoBinding.cellEt.getText().toString(),
                activityContatoBinding.emailEt.getText().toString(),
                activityContatoBinding.siteEt.getText().toString(),
                activityContatoBinding.telComercialCb.isChecked()
        );

        switch (view.getId()){
            case R.id.salvarBt:
                Intent retornoIntent =  new Intent();
                retornoIntent.putExtra(Intent.EXTRA_USER, contato);
                setResult(RESULT_OK, retornoIntent);
                finish();
                break;
            case R.id.pdfBt:
                break;
        }
    }
}