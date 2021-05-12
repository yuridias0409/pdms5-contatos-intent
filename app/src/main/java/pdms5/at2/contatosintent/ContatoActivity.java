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
    private final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;

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
            case R.id.emailBt:
                Intent enviarEmailIntent = new Intent(Intent.ACTION_SENDTO);
                enviarEmailIntent.setData(Uri.parse("mailto: "));
                enviarEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contato.getEmail()});
                enviarEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato");
                enviarEmailIntent.putExtra(Intent.EXTRA_TEXT, contato.toString());
                startActivity(enviarEmailIntent);
                break;
            case R.id.pdfBt:
                break;
            case R.id.siteBt:
                Intent acessarSitePessoalIntent = new Intent(Intent.ACTION_VIEW);
                acessarSitePessoalIntent.setData(Uri.parse("https://" + contato.getSitePessoal()));
                startActivity(acessarSitePessoalIntent);
                break;
            case R.id.telefoneBt:
                verifyCallPhonePermission();
                break;
        }
    }

    private void verifyCallPhonePermission() {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel: " + activityContatoBinding.telefoneEt.getText().toString()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                startActivity(ligarIntent);
            }   else{
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        }   else{
            startActivity(ligarIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE){
            if(permissions[0].equals(Manifest.permission.CALL_PHONE) && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "É necessário esta permissão para essa funcionalidade", Toast.LENGTH_SHORT).show();
            }
            verifyCallPhonePermission();
        }
    }

}