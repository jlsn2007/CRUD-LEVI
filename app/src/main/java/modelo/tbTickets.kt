package modelo

import java.util.UUID

data class tbTickets(
    val UUID_Ticket: String,
    val Num_ticket: Int,
    val Titulo: String,
    val Descripcion: String,
    val Responsable: String,
    val Email_autor: String,
    val Telefono: Int,
    val Ubicacion: String,
    val Estado_ticket: String
)
