package com.scaleup.flames.utils;

import com.google.common.collect.ImmutableMap;

public class RegionUtils {
	public final static String REGION_PARAM_NAME = "region";
	public final static String DEFAULT_REGION_NAME = "UK";
    public static final ImmutableMap<String, String> REGION_2_LANGUAGE_CODE = ImmutableMap.<String, String>builder()
            .put("DK", "da")
            .put("UK", "en")
            .put("NO", "no")
            .put("SE", "sv")
            .put("FI", "fi")
            .put("NL", "nl")
            .put("IND","ind")
            .build();
    public static String defaultLanguage(String region){
        return REGION_2_LANGUAGE_CODE.get(region);
    }

}
