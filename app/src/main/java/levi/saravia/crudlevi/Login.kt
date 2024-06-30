package levi.saravia.crudlevi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnlogear = findViewById<Button>(R.id.btnlogear)

        btnlogear.setOnClickListener {

            val pantallaInicial = Intent(this, ptinicial::class.java)

            GlobalScope.launch(Dispatchers.IO) {

                val objConexion = ClaseConexion().cadenaConexion()

                val verificarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE correoElectronico = ? AND clave = ?")!!
                verificarUsuario.setString(1, txtCorreo.text.toString())
                verificarUsuario.setString(2, txtContrasena.text.toString())
                val resultado = verificarUsuario.executeQuery()

                if (resultado.next()) {
                    startActivity(pantallaInicial)
                } else {
                    println("Usuario no encontrado, verifique las credenciales")
                }
            }
        }
    }
}