package net.kencochrane.raven;

import net.kencochrane.raven.connection.Connection;
import net.kencochrane.raven.event.EventBuilder;
import net.kencochrane.raven.event.LoggedEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RavenTest {
    @Mock
    private Connection mockConnection;
    private Raven raven;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        raven = new Raven(mockConnection);
    }

    @Test
    public void testSendEvent() {
        LoggedEvent loggedEvent = mock(LoggedEvent.class);
        raven.sendEvent(loggedEvent);

        verify(mockConnection).send(loggedEvent);
    }

    @Test
    public void testSendEventBuilder() {
        EventBuilder eventBuilder = mock(EventBuilder.class);
        LoggedEvent loggedEvent = mock(LoggedEvent.class);
        UUID mockUuid = UUID.randomUUID();
        when(eventBuilder.build()).thenReturn(loggedEvent);
        when(loggedEvent.getId()).thenReturn(mockUuid);
        UUID uuid = raven.sendEvent(eventBuilder);

        verify(eventBuilder).build();
        verify(mockConnection).send(any(LoggedEvent.class));
        assertEquals(mockUuid, uuid);
    }
}