package RecyclerViewHelpers

import Modelo.tbMenu_Repartidor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import equipo.ptc.proyecto_trincheraptc.R

class Adaptador_Menu_Repartidor(private val clientes: List<tbMenu_Repartidor>) :
    RecyclerView.Adapter<Adaptador_Menu_Repartidor.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreCliente: TextView = itemView.findViewById(R.id.TxtNombre_Cliente)
        val txtUbicacion: TextView = itemView.findViewById(R.id.TxtUbicacion)
        val ivImagenClientes: ImageView = itemView.findViewById(R.id.ivImagenClientes)
        val txtPedidos: TextView = itemView.findViewById(R.id.TxtPedidos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_card_repartidor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.txtNombreCliente.text = cliente.nombre_clie
        holder.txtUbicacion.text = cliente.direccion_entrega
        holder.txtPedidos.text = "Pedido de ${cliente.nombre_clie}"

        // Cargar imagen del cliente o usar un placeholder
        if (cliente.imagen_clientes.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(cliente.imagen_clientes)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(holder.ivImagenClientes)
        } else {
            holder.ivImagenClientes.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    override fun getItemCount(): Int {
        return clientes.size
    }
}