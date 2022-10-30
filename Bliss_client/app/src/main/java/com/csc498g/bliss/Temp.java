package com.csc498g.bliss;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class Temp {

    public static final TreeMap<Integer, Gem> TEMP_GEMS = new TreeMap<>(Comparator.reverseOrder());

    public static final HashMap<Integer, User> TEMP_USERS = new HashMap<>();
}
