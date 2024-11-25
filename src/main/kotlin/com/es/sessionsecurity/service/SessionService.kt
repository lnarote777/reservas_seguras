package com.es.sessionsecurity.service

import com.es.sessionsecurity.error.exception.BadRequestException
import com.es.sessionsecurity.error.exception.NotFoundException
import com.es.sessionsecurity.model.Rol
import com.es.sessionsecurity.model.Session
import com.es.sessionsecurity.model.Usuario
import com.es.sessionsecurity.repository.SessionRepository
import com.es.sessionsecurity.repository.UsuarioRepository
import com.es.sessionsecurity.util.CipherUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SessionService {

    @Autowired
    private lateinit var sessionRepository: SessionRepository
    @Autowired
        private lateinit var userRepository: UsuarioRepository

    @Autowired
    private lateinit var cipherUtils: CipherUtils

    fun checkToken(token: String?, nombre: String) : Boolean {


        var userBD: Usuario = userRepository
            .findByNombre(nombre)
            .orElseThrow{ NotFoundException("El usuario proporcionado no existe en BDD") }

        if (token == null) throw BadRequestException("Token is null")

        val tokenDecrypt =cipherUtils.decrypt(token, "tokensession")

        if (tokenDecrypt != nombre && userBD.rol != Rol.ADMIN) {
            throw BadRequestException("Token inválido para este usuario")
        }

        //1º Vamos a obtener la sesion asociaada al token
        val s: Session = sessionRepository
            .findByToken(token)
            .orElseThrow{RuntimeException("Token inálido")}

        // Por último comprobamos que la fecha sea váliada
        return s.fechaExp.isAfter(LocalDateTime.now())
    }

}