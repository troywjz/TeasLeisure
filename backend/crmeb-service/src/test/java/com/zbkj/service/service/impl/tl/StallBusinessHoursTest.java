package com.zbkj.service.service.impl.tl;

import com.zbkj.common.model.tl.StallDailyStatus;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class StallBusinessHoursTest {

    @Test
    public void overnightHoursRemainOpenAfterMidnightWhenTodayAlsoHasHours() {
        StallDailyStatus yesterday = status(true, "22:00-02:00");
        StallDailyStatus today = status(true, "10:00-18:00");

        Assert.assertEquals(Boolean.TRUE,
                StallBusinessHours.currentOpen(today, yesterday, LocalTime.of(1, 0)));
    }

    @Test
    public void overnightHoursCloseAtTheirEndTime() {
        StallDailyStatus yesterday = status(true, "22:00-02:00");
        StallDailyStatus today = status(true, "10:00-18:00");

        Assert.assertEquals(Boolean.FALSE,
                StallBusinessHours.currentOpen(today, yesterday, LocalTime.of(2, 0)));
    }

    @Test
    public void yesterdayNonOvernightHoursDoNotCarryIntoToday() {
        StallDailyStatus yesterday = status(true, "10:00-18:00");

        Assert.assertNull(StallBusinessHours.currentOpen(null, yesterday, LocalTime.of(1, 0)));
    }

    private StallDailyStatus status(boolean open, String hours) {
        return new StallDailyStatus().setIsOpen(open).setHours(hours);
    }
}
