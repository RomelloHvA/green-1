package app.rest;

import app.models.Sector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for SectorController
 * @author Romello ten Broeke
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SectorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Sector sector;

    @BeforeEach
    void setup() {
        sector = new Sector((long) 1, "The new sector", "The new sector description");
    }

    @Test
    public void allSectorsCanBeRetrieved() {
        ResponseEntity<Sector[]> response =
                this.restTemplate.getForEntity("/sector/all", Sector[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
    }

    @Test
    public void sectorCanBeAdded() {
        ResponseEntity<Sector> response =
                this.restTemplate.postForEntity("/sector/0", sector, Sector.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Status code should be 201 CREATED");
    }

    @Test
    public void sectorCanBeEdited() {
        sector.setName("The edited sector");
        ResponseEntity<Sector> response =
                this.restTemplate.postForEntity("/sector/1", sector, Sector.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
        assertEquals("The edited sector", Objects.requireNonNull(response.getBody()).getName(),
                "Name should be 'The edited sector'");
    }

    @Test
    public void sectorCantBeDeleted() {
        ResponseEntity<Void> response =
                this.restTemplate.exchange("/sector/1", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode(), "Status code should be 409 CONFLICT");
    }

    @Test
    public void sectorCanBeRetrievedById() {
        ResponseEntity<Sector> response =
                this.restTemplate.getForEntity("/sector/1", Sector.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
    }

    @Test public void sectorCantBeFound() {
        ResponseEntity<?> response =
                this.restTemplate.getForEntity("/sector/7", Void.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Status code should be 404 NOT FOUND");
    }

}
