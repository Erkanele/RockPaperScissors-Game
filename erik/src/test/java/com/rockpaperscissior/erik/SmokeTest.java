package com.rockpaperscissior.erik;

import com.rockpaperscissior.erik.Controller.GameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Verifying if the GameController class is working as intended
 */
@SpringBootTest
public class SmokeTest {

    @Autowired
    private GameController gameController;
    @Test
    public void contextLoads() throws Exception {
        assertThat(gameController).isNotNull();
    }
}
