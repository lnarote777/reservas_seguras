package com.es.sessionsecurity.service

import com.es.sessionsecurity.error.exception.BadRequestException
import com.es.sessionsecurity.error.exception.NotFoundException
import com.es.sessionsecurity.model.Reserva
import com.es.sessionsecurity.model.Usuario
import com.es.sessionsecurity.repository.ReservaRepository
import com.es.sessionsecurity.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReservaService {
    @Autowired
    private lateinit var reservaRepository: ReservaRepository
    @Autowired
        private lateinit var usuarioRepository: UsuarioRepository

    fun insert(nuevaReserva: Reserva, nombreUser: String): Reserva {

        var userBD: Usuario = usuarioRepository
            .findByNombre(nombreUser)
            .orElseThrow{ NotFoundException("El usuario proporcionado no existe en BDD") }

        if (nuevaReserva.id == null) {
            throw BadRequestException("ID no puede ser null o estar vacío")
        }
        if (nuevaReserva.origen.isEmpty()) {
            throw BadRequestException("El origen de la reserva no puede estar vacío")
        }
        if (nuevaReserva.destino.isEmpty()) {
            throw BadRequestException("El destino de la reserva no puede estar vacío")
        }

        if (nuevaReserva.fechaIda.isAfter(nuevaReserva.fechaRegreso)){
            throw BadRequestException("La fecha de ida no puede ser depués de la fecha de regreso")
        }

        nuevaReserva.usuario = userBD

        reservaRepository.save(nuevaReserva)

        return nuevaReserva

        
    }

    fun getReservas(nombreUser: String): List<Reserva> {
        return reservaRepository.findByUsuario_Nombre(nombreUser)
    }

}