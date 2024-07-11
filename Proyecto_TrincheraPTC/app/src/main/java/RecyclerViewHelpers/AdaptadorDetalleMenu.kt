package RecyclerViewHelpers

import Modelo.tbProductos
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorDetalleMenu(private var Datos: List<tbProductos> ): RecyclerView.Adapter<ViewHolderDetalleMenu>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDetalleMenu {
        val vistaMenu = LayoutInflater.from(parent.context).inflate(R.layout.activity_detalle_card, parent, false)

        return ViewHolderDetalleMenu(vistaMenu)
    }

    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolderDetalleMenu, position: Int) {
        val tbProductos = Datos[position]
        holder.txtNombreCard.text = tbProductos.producto
    }
}