package com.es.sessionsecurity.controller

import com.es.sessionsecurity.model.Reserva
import com.es.sessionsecurity.model.Session
import com.es.sessionsecurity.service.ReservaService
import com.es.sessionsecurity.service.SessionService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reservas")
class ReservaController {

    @Autowired
    private lateinit var reservaService: ReservaService
    @Autowired
    private lateinit var sessionService: SessionService

    /*
    OBTENER TODAS LAS RESERVAS POR EL NOMBRE DE USUARIO DE UN CLIENTE
     */
    @GetMapping("/{nombre}")
    fun getByNombreUsuario(
        @PathVariable nombre: String,
        request : HttpServletRequest
    ) : ResponseEntity<List<Reserva>?> {

        /*
        COMPROBAR QUE LA PETICIÓN ESTÁ CORRECTAMENTE AUTORIZADA PARA REALIZAR ESTA OPERACIÓN
         */
        // 1º Extraemos la Cookie
        val cookie: Cookie? = request.cookies.find { c: Cookie? ->  c?.name == "tokenSession"}
        val token = cookie?.value

        //2º Comprobar la validez del token
        if (sessionService.checkToken(token, nombre)){
            //REALIZA LA CONSULTA A LA BASE DE DATOS
            val reservas = reservaService.getReservas(nombre)
            return ResponseEntity<List<Reserva>?>(reservas, HttpStatus.OK)
        }


        // RESPUESTA
        return ResponseEntity<List<Reserva>?>(null, HttpStatus.OK) // cambiar null por las reservas

    }

    /*
    INSERTAR UNA NUEVA RESERVA
     */
    @PostMapping("/{nombreUser}")
    fun insert(
        @PathVariable nombreUser: String,
        @RequestBody nuevaReserva: Reserva,
        request: HttpServletRequest
    ) : ResponseEntity<Reserva?>{

        val reserva = reservaService.insert(nuevaReserva, nombreUser)

        return ResponseEntity<Reserva?>(reserva, HttpStatus.CREATED); // cambiar null por la reserva
    }

}