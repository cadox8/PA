package es.projectalpha.pa.core.utils;

import java.util.Base64;

public class ProtectPass {

    public static String encodePass(String pass) {
        return Base64.getEncoder().encodeToString(pass.getBytes());
    }

    public static String decodePass(String encryptPass) {
        return new String(Base64.getDecoder().decode(encryptPass));
    }
}
