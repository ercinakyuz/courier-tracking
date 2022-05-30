package com.migros.couriertracking.infrastructure.exception.guard;

public final class Guard {

    private Guard(){}

    public static void that(final boolean failCondition, final RuntimeException exception){
        if (failCondition)
            throw exception;
    }

}
