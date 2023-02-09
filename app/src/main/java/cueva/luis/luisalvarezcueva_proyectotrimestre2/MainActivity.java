
package cueva.luis.luisalvarezcueva_proyectotrimestre2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    Intent intentMain;

    Button btLogin;
    Button btLogup;
    EditText etEmail;
    EditText etPassword;

    TextView tvCorreo;
    TextView tvContrasenna;

    Button btPreferencias;

    ConstraintLayout layoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentMain = new Intent(this, AgendaActivity.class);

        btLogin = findViewById(R.id.login);
        btLogup = findViewById(R.id.logup);
        etEmail = findViewById(R.id.Email);
        etPassword = findViewById(R.id.Password);

        tvCorreo = findViewById(R.id.tvCorreoId);
        tvContrasenna = findViewById(R.id.tvContrasennaId);

        btPreferencias = findViewById(R.id.btPreferencias);
        layoutMain = findViewById(R.id.layoutMain);

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

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

        btLogup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agenda agenda = new Agenda(etEmail.getText().toString());
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("agendas");
                db.child(etEmail.getText().toString().replace('.', '_'));
                logup(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

        btPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Preferencias.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (PreferenceManager.getDefaultSharedPreferences(this).getString("1", "")) {
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

    private void logup(String email, String password){
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void login(String email, String password){
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            startActivity(intentMain);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void temaClaro(){
        layoutMain.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        btPreferencias.setTextColor(ContextCompat.getColor(this, R.color.black));
        btLogin.setTextColor(ContextCompat.getColor(this, R.color.black));
        btLogup.setTextColor(ContextCompat.getColor(this, R.color.black));

        etEmail.setTextColor(ContextCompat.getColor(this, R.color.black));
        etPassword.setTextColor(ContextCompat.getColor(this, R.color.black));

        tvCorreo.setTextColor(ContextCompat.getColor(this, R.color.black));
        tvContrasenna.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    private void temaOscuro(){
        layoutMain.setBackgroundColor(ContextCompat.getColor(this, R.color.black));

        btPreferencias.setTextColor(ContextCompat.getColor(this, R.color.white));
        btLogin.setTextColor(ContextCompat.getColor(this, R.color.white));
        btLogup.setTextColor(ContextCompat.getColor(this, R.color.white));

        etEmail.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPassword.setTextColor(ContextCompat.getColor(this, R.color.white));

        tvCorreo.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvContrasenna.setTextColor(ContextCompat.getColor(this, R.color.white));
    }
}