package levi.saravia.crudlevi

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import org.w3c.dom.Text
import java.util.UUID

class registrer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreoRegistro = findViewById<EditText>(R.id.txtCorreoregistro)
        val txtPaswordcontrasenaregistro = findViewById<EditText>(R.id.txtPaswordcontrasenaregistro)
        val btnregistrarse = findViewById<Button>(R.id.btnregistrarse)
        val btniniciarsesion = findViewById<Button>(R.id.btniniciarsesion)

        btnregistrarse.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){

                val ObjConexion = ClaseConexion().cadenaConexion()

                val crearUsuario = ObjConexion?.prepareStatement("Insert into tbUsuarios(UUID_usuario, correoElectronico, clave) values (?, ?, ?)")!!

                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtCorreoRegistro.text.toString())
                crearUsuario.setString(3, txtPaswordcontrasenaregistro.text.toString())
                crearUsuario.executeUpdate()

                withContext(Dispatchers.Main){
                    Toast.makeText(this@registrer, "¡Usuario creado!", Toast.LENGTH_SHORT).show()
                    txtCorreoRegistro.setText("")
                    txtPaswordcontrasenaregistro.setText("")
                }
            }
        }

        btniniciarsesion.setOnClickListener {
            val pantallaLogin = Intent(this, Login::class.java)
            startActivity(pantallaLogin)
        }
    }
}
