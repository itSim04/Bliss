package com.csc498g.bliss;

@FunctionalInterface
public interface ERROR {

    // An interface to conduit errors through the Linking

    void DEBUG(String api, Exception e);

}
