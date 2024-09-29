package Modelo

data class tbMenu_Repartidor(
    val id_cliente: Int,
    val nombre_clie: String,
    val telefono_clie: String,
    val correoElectronico: String,
    val contrasena: String,
    val direccion_entrega: String,
    val imagen_clientes: String
)