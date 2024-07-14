package RecyclerViewHelpers

import Modelo.tbProductos
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorCarroCompras (
    var tvTotal: TextView,
    var carroCompras: ArrayList<tbProductos>
): RecyclerView.Adapter<AdaptadorCarroCompras.ViewHolder>() {

    var total: Double = 0.0

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNomProducto = itemView.findViewById(R.id.tvNomProducto) as TextView
        val tvDescripcion = itemView.findViewById(R.id.TvCantidad) as TextView
        val tvPrecio = itemView.findViewById(R.id.tvPrecioCantidad) as TextView

        init {

            if (tvDescripcion == null) {
                Log.e("ViewHolder", "tvCantProductos no se encontró en la vista.")
            }
            if (tvPrecio == null) {
                Log.e("ViewHolder", "tvPrecioCantidad no se encontró en la vista.")
            }
        }
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = carroCompras[position]

        holder.tvNomProducto.text = producto.producto
        holder.tvDescripcion.text = producto.descripcion
        holder.tvPrecio.text = "$${producto.precioventa}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return carroCompras.size
    }

}