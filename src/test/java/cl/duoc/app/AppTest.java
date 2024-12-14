package cl.duoc.app;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class AppTest {

    @Test
    void testMainWithArgs() {
        String[] args = {"--spring.profiles.active=test"};

        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            App.main(args);
            mocked.verify(() -> SpringApplication.run(App.class, "--spring.profiles.active=test"));
        }
    }

    @Test
    void testMainNoArgs() {
        String[] args = {};

        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            App.main(args);
            mocked.verify(() -> SpringApplication.run(App.class));
        }
    }
}
