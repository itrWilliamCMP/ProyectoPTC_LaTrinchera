package Modelo

data class tbMenuConProductos(
    val id_menu: Number,
    var categoria: String,
    val id_producto: Int,
    val producto: String,
    val descripcion: String,
    val precioventa: Number,
    val stock: Int,
    val imagen_categoria: String,
    val imagen_producto: String,
    ) {

}
