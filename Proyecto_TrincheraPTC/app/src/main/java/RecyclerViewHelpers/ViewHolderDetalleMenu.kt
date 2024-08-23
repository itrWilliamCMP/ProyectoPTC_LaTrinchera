package RecyclerViewHelpers

import Modelo.tbMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import equipo.ptc.proyecto_trincheraptc.R

class ViewHolderDetalleMenu(view: View): RecyclerView.ViewHolder(view) {
    val tvNombreProducto = view.findViewById<TextView>(R.id.tvNombreProducto)

}
