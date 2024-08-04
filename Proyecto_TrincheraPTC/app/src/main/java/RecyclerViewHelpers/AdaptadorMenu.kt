package RecyclerViewHelpers

import Modelo.tbMenu
import Modelo.tbProductos
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import equipo.ptc.proyecto_trincheraptc.MenuCategoriaActivity
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorMenu(private var Datos: List<tbMenu>) : RecyclerView.Adapter<ViewHolderMenu>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMenu {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_menu_principal_card, parent, false)

        return ViewHolderMenu(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMenu, position: Int) {
        val tbMenu = Datos[position]
        holder.tvNombreCategoria.text = tbMenu.categoria


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalle = Intent(context, MenuCategoriaActivity::class.java)
//            pantallaDetalle.putExtra("id_producto")
            pantallaDetalle.putExtra("id_menu", tbMenu.id_menu)
            pantallaDetalle.putExtra("categoria", tbMenu.categoria)
//            pantallaDetalle.putExtra("producto")
//            pantallaDetalle.putExtra("descripcion")
//            pantallaDetalle.putExtra("precioventa")
//            pantallaDetalle.putExtra("stock")
            context.startActivity(pantallaDetalle)
        }
    }
}