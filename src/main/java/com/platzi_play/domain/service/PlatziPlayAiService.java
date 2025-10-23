package com.platzi_play.domain.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface PlatziPlayAiService {

    @UserMessage("""
            Genera un saludo de bienvenida a la plataforma de Gestión de Peliculas {{appName}}.
            Usa menos de 120 caracteres y hazlo con el estilo de Platzi.
            """)
    String generateGreeting(@V("appName") String appName);

    @SystemMessage("""
            Eres un experto en cine que recomienda películas personalizadas según los gustos del usuario.
            Debes recomendar maximo 3 películas.
            No incluyas películas que estén por fuera de la plataforma PlatziPlay.
            """)
    String generateMoviesuggestions(@UserMessage String userMessage);
}
