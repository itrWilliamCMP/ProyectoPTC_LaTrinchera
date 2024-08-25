package RecyclerViewHelpers

import Modelo.MenuComidas


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorMenuCategorias (
    private val comidas: List<MenuComidas>
) : RecyclerView.Adapter<AdaptadorMenuCategorias.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.TxtNombre_Cliente)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val ivImagen: ImageView = itemView.findViewById(R.id.ivImagenClientes)
        val tvPrecio: TextView = itemView.findViewById(R.id.TxtFalta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_detalle_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = comidas[position]
        holder.tvNombre.text = categoria.nombre
        holder.tvDescripcion.text = categoria.descripcion
        holder.ivImagen.setImageResource(categoria.imagen) // Usando el recurso de imagen local
        holder.tvPrecio.text = "$${categoria.precio}" // Mostrando el precio
    }

    override fun getItemCount(): Int {
        return comidas.size
    }
}