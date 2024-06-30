package RecyclerViewHelpers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import levi.saravia.crudlevi.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val txtNombreCard = view.findViewById<TextView>(R.id.txtNombreCard)
}