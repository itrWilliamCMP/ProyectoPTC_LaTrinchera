package RecyclerViewHelpers

import Modelo.tbMenu
import Modelo.tbMenuConProductos
import Modelo.tbProductos
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import equipo.ptc.proyecto_trincheraptc.MenuCategoriaActivity
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorMenu(private var Datos: List<tbMenuConProductos>) :
    RecyclerView.Adapter<ViewHolderMenu>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolderMenu {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.
        activity_menu_principal_card, parent, false)

        return ViewHolderMenu(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMenu, position: Int) {
        val tbMenuConProductos = Datos[position]
        holder.tvNombreCategoria.text = tbMenuConProductos.categoria


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalle = Intent(context, MenuCategoriaActivity::class.java)
           pantallaDetalle.putExtra("id_producto", tbMenuConProductos.id_producto)
            pantallaDetalle.putExtra("id_menu", tbMenuConProductos.id_menu)
            pantallaDetalle.putExtra("categoria", tbMenuConProductos.categoria)
            pantallaDetalle.putExtra("producto", tbMenuConProductos.producto)
           pantallaDetalle.putExtra("descripcion", tbMenuConProductos.descripcion)
            pantallaDetalle.putExtra("precioventa", tbMenuConProductos.precioventa)
            pantallaDetalle.putExtra("stock", tbMenuConProductos.stock)
            pantallaDetalle.putExtra("imagen_categoria", tbMenuConProductos.imagen_categoria)
            pantallaDetalle.putExtra("imagen_producto", tbMenuConProductos.imagen_producto)
            context.startActivity(pantallaDetalle)
        }
    }
}