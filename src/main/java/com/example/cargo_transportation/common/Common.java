package com.example.cargo_transportation.common;

import java.time.LocalDateTime;
import java.time.Month;

public class Common {
    public static final LocalDateTime DEFAULT_START_DATE = LocalDateTime
            .of(2000, Month.JANUARY, 1, 0,0,0);
    public static final LocalDateTime DEFAULT_END_DATE = LocalDateTime
            .of(2100, Month.JANUARY, 1, 0,0,0);
}
