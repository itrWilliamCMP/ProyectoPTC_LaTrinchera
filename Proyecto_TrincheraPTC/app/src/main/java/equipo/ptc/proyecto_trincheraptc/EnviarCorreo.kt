package equipo.ptc.proyecto_trincheraptc

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

suspend fun enviarCorreo(receptor: String, sujeto: String, mensaje: String) = withContext(
    Dispatchers.IO) {
    // Configuración del servidor SMTP
    val props = Properties().apply {
        put("mail.smtp.host", "smtp.gmail.com")
        put("mail.smtp.port", "587") // Usa puerto 587 para STARTTLS
        put("mail.smtp.auth", "true")
        put("mail.smtp.starttls.enable", "true") // Habilita STARTTLS
        put("mail.smtp.ssl.protocols", "TLSv1.2") // Fuerza el uso de TLSv1.2
    }

    val session = Session.getInstance(props, object : javax.mail.Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication("latrinchera05@gmail.com", "iohr tcck fbhg ahvj")
        }
    })

    try {
        val message = MimeMessage(session).apply {
            //Con qué correo enviaré el mensaje
            setFrom(InternetAddress("latrinchera05@gmail.com"))
            addRecipient(Message.RecipientType.TO, InternetAddress(receptor))
            subject = sujeto
            setContent(mensaje, "text/html; charset=utf-8")
        }
        Transport.send(message)
        println("Correo enviado satisfactoriamente")
    } catch (e: MessagingException) {
        e.printStackTrace()
        println("CORREO NO ENVIADO EXE")
    }
}
