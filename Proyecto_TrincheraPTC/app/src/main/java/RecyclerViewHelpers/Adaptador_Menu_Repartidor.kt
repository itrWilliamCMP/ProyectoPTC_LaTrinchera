package RecyclerViewHelpers

import Modelo.tbMenu_Repartidor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import equipo.ptc.proyecto_trincheraptc.R
import javax.mail.FetchProfile.Item

class Adaptador_Menu_Repartidor(var Datos: List<tbMenu_Repartidor>) : RecyclerView.Adapter<View_Folder_RepartidorMenu>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_Folder_RepartidorMenu {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_menu_repartidor, parent, false)
        return View_Folder_RepartidorMenu(view)
    }

    override fun getItemCount(): Int {
        return Datos.size
    }

    override fun onBindViewHolder(holder: View_Folder_RepartidorMenu, position: Int) {
        //Controlar card
        val item = Datos[position]
        holder.TxtNombre_Cliente.text = item.nombreCliente
        holder.TxtUbicacion.text = item.direccion
    }
}
