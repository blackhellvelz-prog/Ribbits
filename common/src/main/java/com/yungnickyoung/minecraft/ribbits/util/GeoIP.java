package com.yungnickyoung.minecraft.ribbits.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class GeoIP {
    private static Boolean inChina = null;
    private static boolean checked = false;

    /**
     * дум port: no network request. Upstream asks https://ip-api.com for the player's country on every client start
     * (sending the player's IP to a third party) only to show one China-specific config toggle; ip-api's free plan
     * has no HTTPS and answers 403, so upstream always ends with isInChina() == false and an ERROR in the log.
     * Same observable result here, without the request.
     */
    public static void init() {
        if (checked) return;
        checked = true;
        inChina = false;
    }

    public static boolean isInChina() {
        return Boolean.TRUE.equals(inChina);
    }

    private static boolean checkChina() {
        try {
            URL url = URI.create("https://ip-api.com/json/?fields=status,countryCode").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String jsonText = br.lines().collect(Collectors.joining());
                RibbitsCommon.LOGGER.info("GeoIP Raw Response: {}", jsonText);

                JsonObject root = JsonParser.parseString(jsonText).getAsJsonObject();
                if ("success".equals(root.get("status").getAsString())) {
                    String countryCode = root.get("countryCode").getAsString();
                    return "CN".equals(countryCode);
                }
                return false;
            }
        } catch (Exception e) {
            RibbitsCommon.LOGGER.error("GeoIP check failed", e);
            return false;
        }
    }
}