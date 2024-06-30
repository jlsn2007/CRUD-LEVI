package levi.saravia.crudlevi

import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tbTickets
import java.sql.SQLException
import java.util.UUID

class ptinicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ptinicial)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtnumticket = findViewById<EditText>(R.id.txtnumticket)
        val txttitulo = findViewById<EditText>(R.id.txttitulo)
        val txtdescrip = findViewById<EditText>(R.id.txtdescrip)
        val txtresponsable = findViewById<EditText>(R.id.txtresponsable)
        val txtemail = findViewById<EditText>(R.id.txtemail)
        val txttelefono = findViewById<EditText>(R.id.txttelefono)
        val txtubicacion = findViewById<EditText>(R.id.txtubicacion)
        val txtestado = findViewById<EditText>(R.id.txtestado)
        val btnguardar = findViewById<Button>(R.id.btnguardar)
        val rcvtickets = findViewById<RecyclerView>(R.id.rcvtickets)


        //crear linearlayout para mostrar datos en lista

        rcvtickets.layoutManager = LinearLayoutManager(this)

        fun obtenerTickets(): List<tbTickets>{

            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("Select * from tbTicket")!!

            val listadotickets = mutableListOf<tbTickets>()

            while (resultSet.next()){
                val UUID_Ticket = resultSet.getString( "UUID_Ticket")
                val Num_ticket = resultSet.getInt("Num_ticket")
                val Titulo = resultSet.getString("Titulo")
                val Descripcion = resultSet.getString("Descripcion")
                val Responsable = resultSet.getString("Responsable")
                val Email_autor = resultSet.getString("Email_autor")
                val Telefono = resultSet.getInt("Telefono")
                val Ubicacion = resultSet.getString("Ubicacion")
                val Estado_ticket = resultSet.getString("Estado_ticket")

                val uniondatos = tbTickets(UUID_Ticket, Num_ticket, Titulo, Descripcion, Responsable, Email_autor, Telefono, Ubicacion, Estado_ticket)

                listadotickets.add(uniondatos)
            }

            return listadotickets

        }

        CoroutineScope(Dispatchers.IO).launch {

            val tickets = obtenerTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(tickets)
                rcvtickets.adapter = adapter
            }
        }

        btnguardar.setOnClickListener{

            CoroutineScope(Dispatchers.IO).launch{

                    val objConexion = ClaseConexion().cadenaConexion()

                    val agregarticket = objConexion?.prepareStatement("INSERT INTO tbTicket (UUID_Ticket, Num_ticket, Titulo, Descripcion, Responsable, Email_autor, Telefono, Ubicacion, Estado_ticket) values (?, ?, ?, ?, ?, ?, ?, ?, ?)")!!
                    agregarticket.setString(1, UUID.randomUUID().toString())
                    agregarticket.setInt(2, txtnumticket.text.toString().toInt())
                    agregarticket.setString(3, txttitulo.text.toString())
                    agregarticket.setString(4, txtdescrip.text.toString())
                    agregarticket.setString(5, txtresponsable.text.toString())
                    agregarticket.setString(6, txtemail.text.toString())
                    agregarticket.setInt(7, txttelefono.text.toString().toInt())
                    agregarticket.setString(8, txtubicacion.text.toString())
                    agregarticket.setString(9, txtestado.text.toString())
                    agregarticket.executeUpdate()

                    val tickets = obtenerTickets()

                    withContext(Dispatchers.Main) {
                        val adapter = rcvtickets.adapter as Adaptador
                        adapter.Datos = tickets
                        adapter.notifyDataSetChanged()


                       Toast.makeText(this@ptinicial, "Ticket guardado exitosamente", Toast.LENGTH_SHORT).show()
                    }

            }
        }

    }
}