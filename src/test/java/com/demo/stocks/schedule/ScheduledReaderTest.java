package com.demo.stocks.schedule;

import com.demo.stocks.config.ScheduledTestConfig;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(ScheduledTestConfig.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ScheduledReaderTest {

    @SpyBean
    private ScheduledReader scheduledReader;

    @Test
    public void whenWait15Second_thenScheduledIsCalledAtLeast2Times() {
        await()
                .atMost(Duration.FIVE_SECONDS.multiply(3))
                .untilAsserted(() -> verify(scheduledReader, atLeast(2)).readData());
    }
}
