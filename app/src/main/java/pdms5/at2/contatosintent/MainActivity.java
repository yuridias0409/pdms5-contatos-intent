package pdms5.at2.contatosintent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import pdms5.at2.contatosintent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ligando (binding) objetos com as Views
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //Libera o campo de celular
        activityMainBinding.cellSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityMainBinding.cellSt.isChecked()){
                    activityMainBinding.cellEt.setVisibility(View.VISIBLE);
                }   else{
                    activityMainBinding.cellEt.setVisibility(View.GONE);
                }
            }
        });


    }
}