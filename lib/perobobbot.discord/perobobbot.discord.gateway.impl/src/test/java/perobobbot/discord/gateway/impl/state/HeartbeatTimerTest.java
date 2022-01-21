package perobobbot.discord.gateway.impl.state;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.lang.ManualNanoClock;

public class HeartbeatTimerTest {


    private HeartbeatTimer heartbeatTimer;
    private ManualNanoClock nanoClock = new ManualNanoClock();

    @BeforeEach
    void setUp() {
        this.heartbeatTimer = new HeartbeatTimer(10_000, 1,nanoClock);
    }

    @Test
    public void shouldNotBeTimeToSendAfter9Seconds() {
        this.nanoClock.setCurrentTime(9_000_000_000L);
        Assertions.assertFalse(this.heartbeatTimer.timeToSendHeartbeat());
    }

    @Test
    public void timeRemainingShouldBe1Sec() {
        this.nanoClock.setCurrentTime(9_000_000_000L);
        Assertions.assertEquals(1_000_000_000, this.heartbeatTimer.delayBeforeNextHeartbeat());
    }

    @Test
    public void timeRemainingShouldBe9Sec() {
        this.nanoClock.setCurrentTime(11_000_000_000L);
        Assertions.assertTrue(this.heartbeatTimer.timeToSendHeartbeat());
        Assertions.assertEquals(9_000_000_000L, this.heartbeatTimer.delayBeforeNextHeartbeat());
    }

    @Test
    public void shouldBeTimeToSendAfter11Seconds() {
        this.nanoClock.setCurrentTime(11_000_000_000L);
        Assertions.assertTrue(this.heartbeatTimer.timeToSendHeartbeat());
    }


    @Test
    public void shouldBeTimeToSendAfter11SecondsAndThenNotAt12Seconds() {
        this.nanoClock.setCurrentTime(11_000_000_000L);
        Assertions.assertTrue(this.heartbeatTimer.timeToSendHeartbeat());
        this.nanoClock.setCurrentTime(12_000_000_000L);
        Assertions.assertFalse(this.heartbeatTimer.timeToSendHeartbeat());
    }

    @Test
    public void shouldBeTimeToSendAfter11SecondsAndThenAt21Seconds() {
        this.nanoClock.setCurrentTime(11_000_000_000L);
        Assertions.assertTrue(this.heartbeatTimer.timeToSendHeartbeat());
        this.nanoClock.setCurrentTime(12_000_000_000L);
        Assertions.assertFalse(this.heartbeatTimer.timeToSendHeartbeat());
        this.nanoClock.setCurrentTime(21_000_000_000L);
        Assertions.assertTrue(this.heartbeatTimer.timeToSendHeartbeat());
    }







}
