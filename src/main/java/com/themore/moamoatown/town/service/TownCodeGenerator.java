package com.themore.moamoatown.town.service;

import java.security.SecureRandom;
import java.util.Random;

public class TownCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 8;
    private static final Random RANDOM = new SecureRandom();

    public static String generateTownCode() {
        StringBuilder townCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            townCode.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return townCode.toString();
    }
}
