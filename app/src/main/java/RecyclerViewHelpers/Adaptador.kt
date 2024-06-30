package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import levi.saravia.crudlevi.R
import modelo.tbTickets

class Adaptador(var Datos: List<tbTickets>):  RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //Esto sirve para unir el rcv con la Card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.ticket_card, parent, false)
        return ViewHolder(vista)
    }

    //Sirve para devolver datos mostrados
    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = Datos[position]
        holder.txtNombreCard.text = item.Titulo
    }
}