package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CitymasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Citymaster getCitymasterSample1() {
        return new Citymaster().id(1L).name("name1").cityCode("cityCode1");
    }

    public static Citymaster getCitymasterSample2() {
        return new Citymaster().id(2L).name("name2").cityCode("cityCode2");
    }

    public static Citymaster getCitymasterRandomSampleGenerator() {
        return new Citymaster().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).cityCode(UUID.randomUUID().toString());
    }
}
