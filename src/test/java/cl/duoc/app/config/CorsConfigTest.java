package cl.duoc.app.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.mockito.Mockito.*;

class CorsConfigTest {

    @InjectMocks
    private CorsConfig corsConfig;

    @Mock
    private CorsRegistry corsRegistry;

    @Mock
    private CorsRegistration corsRegistration;

    @Value("${cors.allowed-origins:http://localhost:4200,https://tu-dominio-en-la-nube.com}")
    private String[] allowedOrigins;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simular el flujo encadenado de CorsRegistration
        when(corsRegistry.addMapping("/**")).thenReturn(corsRegistration);
        when(corsRegistration.allowedOriginPatterns(any())).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders(any())).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(any())).thenReturn(corsRegistration);
        when(corsRegistration.allowCredentials(true)).thenReturn(corsRegistration);
    }

    @Test
    void testCorsConfigurerAddsMappings() {
        // Arrange
        WebMvcConfigurer configurer = corsConfig.corsConfigurer();

        // Act
        configurer.addCorsMappings(corsRegistry);

        // Assert
        verify(corsRegistry, times(1)).addMapping("/**");
        verify(corsRegistration, times(1)).allowedOriginPatterns(allowedOrigins);
        verify(corsRegistration, times(1)).allowedHeaders("*");
        verify(corsRegistration, times(1)).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        verify(corsRegistration, times(1)).allowCredentials(true);
    }
}
