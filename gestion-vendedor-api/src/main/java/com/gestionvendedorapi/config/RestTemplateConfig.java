package com.gestionvendedorapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Esta clase es para configurar la comunicación entre APIs.
// Declara un bean (un objeto que Spring administra y comparte) de RestTemplate 
// (una herramienta que permite hacer peticiones HTTP desde Java)
// que puede ser usado en cualquier clase que necesite realizar llamadas HTTP a otras APIs.


@Configuration
public class RestTemplateConfig
{
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}