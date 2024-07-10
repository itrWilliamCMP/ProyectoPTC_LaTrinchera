package RecyclerViewHelpers

import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.R

class ViewHolderMenu(view: View): RecyclerView.ViewHolder(view) {
    val txtNombreCard = view.findViewById<TextView>(R.id.txtCardMenu)
}