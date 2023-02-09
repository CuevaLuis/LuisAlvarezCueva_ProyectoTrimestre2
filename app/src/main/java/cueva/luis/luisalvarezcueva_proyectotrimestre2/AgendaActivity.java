package cueva.luis.luisalvarezcueva_proyectotrimestre2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgendaActivity extends AppCompatActivity {

    private DatabaseReference db;

    Button btPreferencias;

    EditText etNombre;
    EditText etTelefono;

    TextView tvNombre;
    TextView tvTelefono;

    Button btSave;
    Button btList;
    Button btDel;

    ConstraintLayout agendaLayout;
    RecyclerView listaContacto;

    Agenda agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);

        tvNombre = findViewById(R.id.tvNombre);
        tvTelefono = findViewById(R.id.tvTelefono);

        btSave = findViewById(R.id.btGuardar);
        btList = findViewById(R.id.btList);
        btDel = findViewById(R.id.btBorrar);

        listaContacto = findViewById(R.id.listContacto);

        agendaLayout = findViewById(R.id.agendaLayout);

        btPreferencias = findViewById(R.id.btPreferencias2);

        switch (PreferenceManager.getDefaultSharedPreferences(this).getString("1", "")){
            case "C":
                temaClaro();
                break;
            case "O":
                temaOscuro();
                break;
            default:
                break;
        }

        btPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgendaActivity.this, Preferencias.class);
                startActivity(i);
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContacto(etNombre.getText().toString(), etTelefono.getText().toString());
            }
        });

        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarAgenda();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (PreferenceManager.getDefaultSharedPreferences(this).getString("1", "")){
            case "C":
                temaClaro();
                break;
            case "O":
                temaOscuro();
                break;
            default:
                break;
        }
    }

    private Contacto saveContacto(String nombre, String telefono){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Contacto contacto = new Contacto(nombre, telefono);

        db = FirebaseDatabase.getInstance().getReference("agendas");
        Log.i("EMAIL", new Agenda(auth.getCurrentUser().getEmail()).getUsuario());
        db.child(new Agenda(auth.getCurrentUser().getEmail()).getUsuario().replace('.', '_')).push().setValue(contacto);
        return contacto;
    }

    private Agenda cargarAgenda(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String usuarioDB = auth.getCurrentUser().getEmail().replace('.', '_');

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query reference = database.getReference("agendas").equalTo(usuarioDB).orderByKey();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Contacto> contactos = new ArrayList<Contacto>();
                for (DataSnapshot hijo: snapshot.getChildren()) {
                    Contacto contacto = hijo.getValue(Contacto.class);
                    Log.i("CONTACTO", "Encontrado");
                    //contactos.add(contacto.);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return agenda;
    }

    private void temaClaro(){
        agendaLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        btPreferencias.setTextColor(ContextCompat.getColor(this, R.color.black));
        btSave.setTextColor(ContextCompat.getColor(this, R.color.black));
        btList.setTextColor(ContextCompat.getColor(this, R.color.black));
        btDel.setTextColor(ContextCompat.getColor(this, R.color.black));

        etTelefono.setTextColor(ContextCompat.getColor(this, R.color.black));
        etNombre.setTextColor(ContextCompat.getColor(this, R.color.black));

        tvTelefono.setTextColor(ContextCompat.getColor(this, R.color.black));
        tvNombre.setTextColor(ContextCompat.getColor(this, R.color.black));

        listaContacto.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
    }

    private void temaOscuro(){
        agendaLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black));

        btPreferencias.setTextColor(ContextCompat.getColor(this, R.color.white));
        btSave.setTextColor(ContextCompat.getColor(this, R.color.white));
        btList.setTextColor(ContextCompat.getColor(this, R.color.white));
        btDel.setTextColor(ContextCompat.getColor(this, R.color.white));

        etTelefono.setTextColor(ContextCompat.getColor(this, R.color.white));
        etNombre.setTextColor(ContextCompat.getColor(this, R.color.white));

        tvTelefono.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvNombre.setTextColor(ContextCompat.getColor(this, R.color.white));

        listaContacto.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }
}