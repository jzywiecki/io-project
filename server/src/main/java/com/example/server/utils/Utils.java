package com.example.server.utils;

import java.time.DayOfWeek;

public class Utils {

    public static String getDayInPolish(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> "Poniedziałek";
            case TUESDAY -> "Wtorek";
            case WEDNESDAY -> "Środa";
            case THURSDAY -> "Czwartek";
            case FRIDAY -> "Piątek";
            case SATURDAY -> "Sobota";
            case SUNDAY -> "Niedziela";
        };
    }
}
