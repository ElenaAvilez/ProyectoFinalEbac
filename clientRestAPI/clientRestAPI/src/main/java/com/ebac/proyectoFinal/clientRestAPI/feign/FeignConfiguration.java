package com.ebac.proyectoFinal.clientRestAPI.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class FeignConfiguration {

    @Value("${api.url}")
    private String apiUrl;

    private final ObjectMapper mapper;

    public FeignConfiguration() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    @Bean(name = "feignApiClientProveedores")
    FeignApiClientProveedores getProveedorClient(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignApiClientProveedores.class, apiUrl);
    }
    @Bean(name = "feignApiClientProductos")
    FeignApiClientProductos getProductosClient(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignApiClientProductos.class, apiUrl);
    }
    @Bean(name = "feignApiClientLogin")
    FeignApiClientLogin getLoginClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignApiClientLogin.class, apiUrl);
    }

    @Bean
    FeignServiceLogin feignServiceLogin(FeignApiClientLogin feignApiClientLogin, String apiUrl) {
        return new FeignServiceLogin(feignApiClientLogin, apiUrl);
    }

    @Bean(name = "apiUrl")
    String getApiUrl (){
        return apiUrl;
    }
}
