package pdms5.at2.contatosintent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import pdms5.at2.contatosintent.Model.Contato;
import pdms5.at2.contatosintent.databinding.ActivityContatosBinding;

public class ContatosActivity extends AppCompatActivity {
    private ActivityContatosBinding activityContatosBinding;
    private ArrayList<Contato> contatosList;
    private ArrayAdapter<String> contatosAdapter;
    private final int NOVO_CONTATO_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatosBinding = ActivityContatosBinding.inflate(getLayoutInflater());
        setContentView(activityContatosBinding.getRoot());

        // Instanciar o data source
        contatosList = new ArrayList<>();
        popularContatosList();

        // Instanciar o adapter
        contatosAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contatosList);

        // Associando o adapter com o listView
        activityContatosBinding.contatosLv.setAdapter(contatosAdapter);

    }

    private void popularContatosList(){
        for (int i = 0; i < 20; i++){
            contatosList.add(
                    new Contato(
                            "Nome " + i,
                            "Telefone " + i,
                            "Celular " + i,
                            "Email " + i,
                            "www.site " + i + ".com.br",
                            (i % 2 == 0) ? false : true
                    )
            );
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contatos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.novoContatoMi){
            Intent novoContatoIntent = new Intent(this, ContatoActivity.class);
            startActivityForResult(novoContatoIntent, NOVO_CONTATO_REQUEST_CODE);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NOVO_CONTATO_REQUEST_CODE && resultCode == RESULT_OK){
            Contato contato = (Contato) data.getSerializableExtra(Intent.EXTRA_USER);
            if (contato != null){
                contatosList.add(contato);
                contatosAdapter.notifyDataSetChanged();
            }
        }
    }
}