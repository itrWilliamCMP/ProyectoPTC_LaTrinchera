package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.UUID

class Register : AppCompatActivity() {
    val codigo_opcion_galeria = 102
    val codigo_opcion_tomar_foto = 103
    val CAMERA_REQUEST_CODE = 0
    val STORAGE_REQUEST_CODE = 1
    val uuid = UUID.randomUUID().toString()

    lateinit var imgRegistrarPFP: ImageView
    lateinit var miPath: String

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            pedirPermisoCamara()
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, codigo_opcion_tomar_foto)
        }
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            pedirPermisoAlmacenamiento()
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, codigo_opcion_galeria)
        }
    }

    private fun pedirPermisoCamara() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    private fun pedirPermisoAlmacenamiento() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, codigo_opcion_tomar_foto)
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
                return
            }
            STORAGE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, codigo_opcion_galeria)
                } else {
                    Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show()
                }
            }

            else -> {
                //Else por si sale un permiso no controlado.
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                codigo_opcion_galeria -> {
                    val imageUri: Uri? = data?.data
                    imageUri?.let {
                        val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                        subirimagenFirebase(imageBitmap) { url ->
                            miPath = url
                            imgRegistrarPFP.setImageURI(it)
                        }
                    }
                }

                codigo_opcion_tomar_foto -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    imageBitmap?.let {
                        subirimagenFirebase(it) { url ->
                            miPath = url
                            imgRegistrarPFP.setImageBitmap(it)
                        }
                    }
                }
            }
        }
    }

    private fun subirimagenFirebase(bitmap: Bitmap, onSuccess: (String) -> Unit) {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${uuid}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener {
            Toast.makeText(this@Register, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //1- Mando a llamar a todos los elementos de la vista
        imgRegistrarPFP = findViewById(R.id.imgRegistrarPFP)
        val btnGaleria = findViewById<Button>(R.id.btnSubirImagen)
        val btnFoto = findViewById<Button>(R.id.btnTomarFoto)
        val txtRegistrarNombre = findViewById<EditText>(R.id.txtRegistrarNombre)
        val imgAtras = findViewById<ImageView>(R.id.imgAtras)
        val txtRegistrarCorreo = findViewById<EditText>(R.id.txtRegistrarCorreo)
        val txtRegistrarContrasena = findViewById<EditText>(R.id.txtRegistrarContrasena)
        val txtConfirmacionContrasena = findViewById<EditText>(R.id.txtConfimacionContrasena)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        val txtRegistrarTelefono = findViewById<EditText>(R.id.txtRegistrarTelefono)
        val txtRegistrarDireccion = findViewById<EditText>(R.id.txtRegistrarDireccion)

        txtRegistrarTelefono.filters = arrayOf(android.text.InputFilter.LengthFilter(8)) // Limitar a 9 dígitos

        txtRegistrarTelefono.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val textoFiltrado = s?.replace(Regex("[^0-8]"), "") // Remueve cualquier carácter que no sea número
                if (textoFiltrado != s.toString()) {
                    txtRegistrarTelefono.setText(textoFiltrado)
                    txtRegistrarTelefono.setSelection(textoFiltrado?.length ?: 0) // Mueve el cursor al final del texto
                }
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        btnGaleria.setOnClickListener {
            checkStoragePermission()
        }

        btnFoto.setOnClickListener {
            checkCameraPermission()
        }

        // Función para encriptar la contraseña (corregida)
        fun hashSHA256(contraseniaEscrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraseniaEscrita.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        //2- Programar los botones
        //Al darle clic al boton se hace un insert a la base con los valores que escribe el usuario
        btnRegistrar.setOnClickListener {
            // obtengo los valores ingresados por el usuario.
            val correo = txtRegistrarCorreo.text.toString()
            val contrasena = txtRegistrarContrasena.text.toString()
            val confirmacionContrasena = txtConfirmacionContrasena.text.toString()
            val telefono = txtRegistrarTelefono.text.toString()

            //Validaciones
            // Validación: Que no quede ningun campo vacío.
            if (correo.isEmpty() || contrasena.isEmpty() || confirmacionContrasena.isEmpty()) {
                Toast.makeText(this, "Campo Vacio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación: Que el correo electrónico tenga una @ obligatoriamente.
            if (!correo.matches(".*@.*".toRegex())) {
                Toast.makeText(this, "Ingrese correo valido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validación: Que la contraseña contenga entre 6 y 24 caracteres.
            if (contrasena.length < 6 || contrasena.length > 24) {
                Toast.makeText(this, "Ingrese una clave entre 6 y 24 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validación: Que las contraseña sean las mismas.
            if (contrasena != confirmacionContrasena) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validación: Que el teléfono solo tenga números y sea de 9 dígitos
            if (!telefono.matches("\\d{8}".toRegex())) {
                Toast.makeText(this, "Ingrese un número de teléfono válido de 9 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Si todas las validaciones pasan, procede con el registro
            GlobalScope.launch(Dispatchers.IO) {
                //Creo un objeto de la clase conexion

                //Encripto la contraseña usando la función de arriba
                val contraseniaEncriptada = hashSHA256(txtRegistrarContrasena.text.toString())

                val objConexion = ClaseConexion().cadenaConexion()

                //Creo una variable que contenga un PrepareStatement
                val CreacionDelUsuario = objConexion?.prepareStatement("INSERT INTO clientes_PTC (nombre_clie, correoElectronico, contrasena, imagen_clientes, telefono_clie, direccion_entrega) VALUES ( ?, ?, ?, ?, ?, ?)")!!
                CreacionDelUsuario.setString(1, txtRegistrarNombre.text.toString())
                CreacionDelUsuario.setString(2, txtRegistrarCorreo.text.toString())
                CreacionDelUsuario.setString(3, contraseniaEncriptada)
                CreacionDelUsuario.setString(4, miPath)
                CreacionDelUsuario.setString(5, txtRegistrarTelefono.text.toString())
                CreacionDelUsuario.setString(6, txtRegistrarDireccion.text.toString())
                CreacionDelUsuario.executeUpdate()

                withContext(Dispatchers.Main) {
                    //Se Abre otra corrutina para mostrar un mensaje y limpiar los campos


                    //Se hace en el Hilo Main por que el hilo IO no permite mostrar nada en pantalla
                    Toast.makeText(this@Register, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtRegistrarCorreo.setText("")
                    txtRegistrarContrasena.setText("")
                    txtConfirmacionContrasena.setText("")
                }
            }
        }
        //Al darle clic a la flechta de arriba - Regresar al Login
        imgAtras.setOnClickListener {
            val pantallaLogin = Intent(this, Login::class.java)
            startActivity(pantallaLogin)
        }
        //Al darle clic a al boton que ya tengo una cuenta - Regresar al Login
        btnRegresar.setOnClickListener {
            val pantallaLogin = Intent(this, Login::class.java)
            startActivity(pantallaLogin)
        }
    }
}