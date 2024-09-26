package RecyclerViewHelpers

import Modelo.tbMenuConProductos
import Modelo.tbProductos
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.MenuCategoriaActivity
import equipo.ptc.proyecto_trincheraptc.ProductoActivity
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorDetalleMenu(private var Datos: List<tbMenuConProductos> ): RecyclerView.Adapter<ViewHolderDetalleMenu>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDetalleMenu {
        val vistaMenu = LayoutInflater.from(parent.context).inflate(R.layout.activity_detalle_card, parent, false)

        return ViewHolderDetalleMenu(vistaMenu)
    }

    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolderDetalleMenu, position: Int) {
        val tbMenuConProductos = Datos[position]
        holder.tvNombreProducto.text = tbMenuConProductos.producto

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalle = Intent(context, ProductoActivity::class.java)
            pantallaDetalle.putExtra("id_producto", tbMenuConProductos.id_producto)
            pantallaDetalle.putExtra("id_menu", tbMenuConProductos.id_menu)
            pantallaDetalle.putExtra("categoria", tbMenuConProductos.categoria)
            pantallaDetalle.putExtra("producto", tbMenuConProductos.producto)
            pantallaDetalle.putExtra("descripcion", tbMenuConProductos.descripcion)
            pantallaDetalle.putExtra("precioventa", tbMenuConProductos.precioventa)
            pantallaDetalle.putExtra("stock", tbMenuConProductos.stock)
            pantallaDetalle.putExtra("imagen_categoria", tbMenuConProductos.imagen_categoria)
            pantallaDetalle.putExtra("imagen_producto", tbMenuConProductos.imagen_comida)
            context.startActivity(pantallaDetalle)
        }
    }
}