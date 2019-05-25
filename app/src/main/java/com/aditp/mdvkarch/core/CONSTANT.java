package com.aditp.mdvkarch.core;

public class CONSTANT {

    // ------------------------------------------------------------------------
    /**
     * set as <b>false</b> if you want to <i>release</i> this apps .
     */
    // ------------------------------------------------------------------------
    public static final boolean IS_DEV_MODE = true;

    // ------------------------------------------------------------------------
    // AREA STRING
    // ------------------------------------------------------------------------

    // SHARED PREF
    public static final String KEY_USERNAME = "KEY_USERNAME";

    // ------------------------------------------------------------------------
    // AREA INTEGER
    // ------------------------------------------------------------------------
    public static final int CODE_SERVER_400 = 400;
    public static final int CODE_SERVER_415 = 415;
    public static final int CODE_SERVER_500 = 500;

    // ------------------------------------------------------------------------
    // AREA ENUM
    // ------------------------------------------------------------------------
    public enum NETWORK_TYPE {
        NETWORK_ETHERNET,
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }


}
