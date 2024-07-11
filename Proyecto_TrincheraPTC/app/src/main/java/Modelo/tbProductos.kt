package Modelo

data class tbProductos(
    val id_producto: Int,
    val id_menu: Int,
    val producto: String,
    val descripcion: String,
    val precioventa: Number,
    val stock: Int
)
