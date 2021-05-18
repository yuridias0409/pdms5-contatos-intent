package pdms5.at2.contatosintent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import pdms5.at2.contatosintent.Model.Contato;
import pdms5.at2.contatosintent.databinding.ActivityContatoBinding;

public class ContatoActivity extends AppCompatActivity {
    private ActivityContatoBinding activityContatoBinding;
    private Contato contato;
    private int posicao = -1;
    private final int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;

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

        // Tira o botão de PDF em caso de adição de contato
        activityContatoBinding.pdfBt.setVisibility(View.GONE);

        //Adiciona um título novo
        getSupportActionBar().setSubtitle("Adição de Contato");

        // Verifica se algum Contato foi recebido
        contato = (Contato) getIntent().getSerializableExtra(Intent.EXTRA_USER);

        if(contato != null){
            //Recebendo posição
            posicao = getIntent().getIntExtra(Intent.EXTRA_INDEX, -1);

            if(posicao == -1){
                activityContatoBinding.salvarBt.setVisibility(View.GONE);
                activityContatoBinding.pdfBt.setVisibility(View.VISIBLE);
                alterarAtivacaoViews(false);
                getSupportActionBar().setSubtitle("Detalhes do Contato");
            }   else {
                activityContatoBinding.pdfBt.setVisibility(View.GONE);
                alterarAtivacaoViews(true);
                getSupportActionBar().setSubtitle("Edição do Contato");
            }

            // Usando os dados para preencher o contato
            activityContatoBinding.nomeEt.setText(contato.getNome());
            activityContatoBinding.telefoneEt.setText(contato.getTelefone());

            if(!contato.getCelular().equals("")){
                activityContatoBinding.cellSt.setChecked(true);
                activityContatoBinding.cellEt.setVisibility(View.VISIBLE);
                activityContatoBinding.cellEt.setText(contato.getCelular());
            }

            activityContatoBinding.emailEt.setText(contato.getEmail());
            activityContatoBinding.siteEt.setText(contato.getSitePessoal());

            activityContatoBinding.telComercialCb.setChecked(contato.getTefoneComercial());
        }
    }

    private void alterarAtivacaoViews(boolean ativo){
        activityContatoBinding.nomeEt.setEnabled(ativo);
        activityContatoBinding.telefoneEt.setEnabled(ativo);
        activityContatoBinding.cellSt.setEnabled(ativo);
        activityContatoBinding.cellEt.setEnabled(ativo);
        activityContatoBinding.cellEt.setEnabled(ativo);
        activityContatoBinding.emailEt.setEnabled(ativo);
        activityContatoBinding.siteEt.setEnabled(ativo);
        activityContatoBinding.telComercialCb.setEnabled(ativo);
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
                retornoIntent.putExtra(Intent.EXTRA_INDEX, posicao);
                setResult(RESULT_OK, retornoIntent);
                finish();
                break;
            case R.id.pdfBt:
                verifyWriteExternalStoragePermission();
                break;
        }
    }

    private void verifyWriteExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }   else{
                gerarDocumentoPdf();
            }
        }   else{
            gerarDocumentoPdf();
        }
    }

    private void gerarDocumentoPdf(){
        // Pegando a altura e a largura da View Raiz para gerar a imagem que vai para o PDF
        View conteudo = activityContatoBinding.getRoot();
        int largura = conteudo.getWidth();
        int altura = conteudo.getHeight();

        //Criando o documento PDF
        PdfDocument documentoPdf = new PdfDocument();

        //Criando a configuração de uma página e iniciando uma página a partir da configuração
        PdfDocument.PageInfo configuracaoPagina = new PdfDocument.PageInfo.Builder(largura, altura, 1).create();
        PdfDocument.Page pagina = documentoPdf.startPage(configuracaoPagina);

        //Criando um snapshot da view na página PDF
        conteudo.draw(pagina.getCanvas());

        documentoPdf.finishPage(pagina);

        //Salvar arquivo PDF
        File diretorioDocumentos = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
        try {
            File documento = new File(diretorioDocumentos, contato.getNome().replace(" ", "_") + ".pdf");
            documento.createNewFile();
            documentoPdf.writeTo(new FileOutputStream(documento));
            documentoPdf.close();
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            verifyWriteExternalStoragePermission();
        }
    }
}