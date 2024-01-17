package app;

import app.Green1backendApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class Green1backendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    Green1backendApplication application = null;

    @Test
    void springBootApplicationContextLoadsOk() {
        assertNotNull(application);
        System.out.println("Application auto-configuration has succeeded.");
    }

    @Test
    public void jUnit5HasBeenConfiguredForTesting() {
        assertThrows(RuntimeException.class, () -> { int a = 0; int b = 1 / a; });
    }

}
