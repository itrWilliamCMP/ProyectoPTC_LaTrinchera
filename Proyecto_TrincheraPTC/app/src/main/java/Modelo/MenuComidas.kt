package Modelo

data class MenuComidas(
    val nombre: String,
    val descripcion: String,
    val imagen: Int, // ID del recurso de imagen
    val precio: Double
)
