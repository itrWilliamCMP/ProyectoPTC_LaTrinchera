package RecyclerViewHelpers

import android.view.TextureView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.R

class ViewHolderSopas(view: View): RecyclerView.ViewHolder(view) {
    val txtNombreCard = view.findViewById<TextureView>(R.id.txtnombreCard)
}