package com.csc498g.bliss;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class Temp {

    // Holds temporary values
    public static final TreeMap<Integer, Gem> TEMP_GEMS = new TreeMap<>(Comparator.reverseOrder()); // Retrieved gems
    public static int TEMP_LATEST_GEM = -1; // Latest gem added
    public static final TreeMap<Integer, Gem> TEMP_COMMENTS = new TreeMap<>(Comparator.reverseOrder()); // Retrieved comments
    public static int TEMP_LATEST_COMMENT = -1; // Latest comment added
    public static final HashMap<Integer, User> TEMP_USERS = new HashMap<>(); // Retrieved users
}
