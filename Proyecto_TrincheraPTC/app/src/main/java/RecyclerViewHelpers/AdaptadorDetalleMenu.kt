package RecyclerViewHelpers

import Modelo.tbProductos
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.MenuCategoriaActivity
import equipo.ptc.proyecto_trincheraptc.ProductoActivity
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorDetalleMenu(private var Datos: List<tbProductos> ): RecyclerView.Adapter<ViewHolderDetalleMenu>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDetalleMenu {
        val vistaMenu = LayoutInflater.from(parent.context).inflate(R.layout.activity_detalle_card, parent, false)

        return ViewHolderDetalleMenu(vistaMenu)
    }

    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolderDetalleMenu, position: Int) {
        val tbProductos = Datos[position]
        holder.tvNombreProducto.text = tbProductos.producto

        holder.tvNombreProducto.setOnClickListener(){
            val context = holder.itemView.context
            val pantallaDetalleMenu = Intent(context, ProductoActivity::class.java)
            context.startActivity(pantallaDetalleMenu )
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalle = Intent(context, ProductoActivity::class.java)
            pantallaDetalle.getIntArrayExtra("id_producto")
            pantallaDetalle.getIntArrayExtra("id_menu")
            pantallaDetalle.getStringExtra("producto")
            pantallaDetalle.getStringExtra("descripcion")
            pantallaDetalle.getStringExtra("precioventa")
            pantallaDetalle.getIntArrayExtra("stock")
            context.startActivity(pantallaDetalle)
        }


    }
}