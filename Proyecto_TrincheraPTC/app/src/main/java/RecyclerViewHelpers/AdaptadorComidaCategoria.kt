package RecyclerViewHelpers

import Modelo.ComidaCategoria
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.MenuCategoriaActivity
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorComidaCategoria(
    private val context: Context,
    private val categorias: List<ComidaCategoria>
) : RecyclerView.Adapter<AdaptadorComidaCategoria.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreCategoria)
        val ivImagen: ImageView = itemView.findViewById(R.id.ivImagenClientes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_menu_principal_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.tvNombre.text = categoria.nombre
        holder.ivImagen.setImageResource(categoria.imagen)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MenuCategoriaActivity::class.java)
            intent.putExtra("categoriaNombre", categoria.nombre)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return categorias.size
    }
}