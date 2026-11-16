package com.zbkj.common.utils;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URI;
import java.util.Locale;

/** URL checks for server-side fetches that accept externally supplied links. */
public final class PublicHttpUrl {
    private PublicHttpUrl() { }

    public static URI validate(String value) throws IOException {
        try {
            return validate(URI.create(value).normalize());
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid remote URL", e);
        }
    }

    public static URI validate(URI uri) throws IOException {
        URI normalized = uri.normalize();
        String scheme = normalized.getScheme();
        String host = normalized.getHost();
        if (host == null || normalized.getUserInfo() != null ||
                !("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) ||
                (normalized.getPort() != -1 && normalized.getPort() != 80 && normalized.getPort() != 443)) {
            throw new IOException("Invalid remote URL");
        }
        String lowerHost = host.toLowerCase(Locale.ROOT);
        if (lowerHost.equals("localhost") || lowerHost.endsWith(".localhost") ||
                lowerHost.endsWith(".local") || lowerHost.endsWith(".internal")) {
            throw new IOException("Private remote hosts are not allowed");
        }
        for (InetAddress address : InetAddress.getAllByName(host)) {
            if (isNonPublicAddress(address)) {
                throw new IOException("Private remote addresses are not allowed");
            }
        }
        return normalized;
    }

    private static boolean isNonPublicAddress(InetAddress address) {
        if (address.isAnyLocalAddress() || address.isLoopbackAddress() || address.isLinkLocalAddress() ||
                address.isSiteLocalAddress() || address.isMulticastAddress()) return true;
        byte[] bytes = address.getAddress();
        if (address instanceof Inet6Address) return (bytes[0] & 0xfe) == 0xfc;
        int first = bytes[0] & 0xff;
        int second = bytes[1] & 0xff;
        int third = bytes[2] & 0xff;
        return first == 0 || first == 100 && second >= 64 && second <= 127 ||
                first == 192 && (second == 0 && (third == 0 || third == 2) || second == 168) ||
                first == 198 && (second == 18 || second == 19 || second == 51 && third == 100) ||
                first == 203 && second == 0 && third == 113 || first >= 224;
    }
}
