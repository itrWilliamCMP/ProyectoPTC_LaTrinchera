package RecyclerViewHelpers

import Modelo.tbMenu
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorMenu(private var Datos: List<tbMenu>) : RecyclerView.Adapter<ViewHolderMenu>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMenu {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_menu_principal_card, parent, false)

        return ViewHolderMenu(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMenu, position: Int) {
        val tbMenu = Datos[position]
        holder.txtCardMenu.text = tbMenu.categoria
    }
}