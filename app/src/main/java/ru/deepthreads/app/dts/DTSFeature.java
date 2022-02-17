package ru.deepthreads.app.dts;

import android.util.Base64;
import androidx.annotation.NonNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Request;

public class DTSFeature {

    public static class ContextSignature {
        @NonNull
        public static String generateNew(@NonNull Request request, @NonNull String nonce) throws GeneralSecurityException, IOException {
            String path = relativePath(request.url().toString());
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(Objects.requireNonNull(nonce).getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
            mac.update(path.getBytes(StandardCharsets.UTF_8));
            byte[] result = mac.doFinal();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(Repository.prefix.getBytes(StandardCharsets.UTF_8));
            stream.write(result);
            return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
        }
        @NonNull
        private static String relativePath(@NonNull String absolutePath) {
            StringBuilder path = new StringBuilder();
            String[] segments = absolutePath.split("/");
            path.append("/");
            for (int i = 3; i < segments.length; i++) {
                path.append(segments[i]);
                if (i != segments.length - 1) {
                    path.append("/");
                }
            }
            return path.toString().split(Pattern.quote("?"))[0];
        }

    }

    public static class DeviceSignature {

    }

    public static class ExtensionSignature {
        @NonNull
        public static String generateNew(@NonNull byte[] byteArray) throws GeneralSecurityException {
            Signature signature = Signature.getInstance("SHA1withRSA");
            PKCS8EncodedKeySpec encodedSpec = new PKCS8EncodedKeySpec(Base64.decode(Repository.extPrivate, Base64.NO_WRAP));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey key = factory.generatePrivate(encodedSpec);
            signature.initSign(key);
            signature.update(byteArray);
            byte[] result = signature.sign();
            return Base64.encodeToString(result, Base64.NO_WRAP);
        }
    }

    public static class Repository {
        public static String[] securedEndpoints = {
                "/auth/login",
                "/auth/register",
                "/wall/post"
        };
        public static String prefix = "09";
        public static String extPrivate = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC8i59JNxKYEXH74Lilq3EAdSRCtXKmvRHAxVDR9UJ/P9q7UMPjJOrUdEBql+qeIcpvzVh3uotxi9M6CyNby7E2MCfZXKbJha+oRpSq2weeuQbLlFJiBmnknFIG0bT17ooVG9Q++sPLJvn2Z+meJSCBAviKPfiV+kSDNuCHdAl3SnbdbtSIoYgxQF4JJ1YmmucxogZlcoa5IT4wJ9/PK+L3asvgBefBDNVZ4gnpAygVSe4r2p4G4wo4akdqzfk656edvDz1NYYCpbQzZf/Rl53BRYRyk1LAyO6kIezinFwIlbvVuQEfjLDleykFbLRG0ZcbLoUh3CnCdSEjk1vTsA9hAgMBAAECggEADyQrS4bnsFyeSbr3OR62eXHu0oidx3Qhi9iMr//BMlTfbPGEeaZKUXtwfN7sUXynNClKdHr/ncO718pzMXj/JzngyVzebAqXW60nXT0vtHhpaknj/8lCEcDX+YI3xRQ99IoClngu5w6fPkGEClYy8QlbCkcFwo2RmPP8PgR8ih7GdhWWF/RMQHlFz8+zcbNHwsogTSkD3/ESBemY1EeqvT8HD1Y5L4XHQxNAStVaNJdwz9DokEfzsIpLehQnxC6lR/FxRc9D4IMFGf4anCt0JxJCEfPv5KOHXahth+ip2yZk21u5x/em+QcVxIXy237WtDAUFchGyArSJuHp/EPRQQKBgQDVuS3+6CAoRhZWglMYHDlflvzELd0j5AVIsU1gSTDpduJrq3RexcpBG+AEYmgB5BbS2ahHYQvs1jNWH70mP0aQ4VzHiKaex+cz6W5UJZqSZ0ZNvAxPE1VULQjPgdJFK0fSU7MGkakYfCnkVMH+M90YUMLzb5ftdrV+7jQwOjfZ+QKBgQDh13IyaJjoORxlWhpGC//iRLOafMjxvkAZ7cZGnmxCd+8800WUkgzjrkvK9RaM+WzTE3Z3eLl+WcznQUIOjWM+ND0n7Qr0RrcHdoNW7NMNE70aZLZNJZNRQDEia6CDJkEefZGSq8hPdvue4/Lga01/3An7KGYo68xeRVKtBpT6qQKBgAv2NVDTBRm78nwdBzGOQtfKx5LjTkE3/eO3qLD+57HpaXrFOdokD9HdEYitSUzxyyU58WUtVVGIG49yb0+4suZg9qRkTY8NeS86nWYwRJp1FUY8hCPY3Sm864VRqsccSQTIkniHpoMsT9vrsn/bv0AhJQgi8snjnvzfsCQS3hVhAoGAeF2sZoNq43H02WvBTw38LisxkifprTYZ0ffxUhgDWRW08zUjA4Enz31Itf4UK8SQJJtd62TzcA0KcUJNo5Xli/Spl4r16KV7zUVz0LNd9L1NbzC4HLvnHUnaJh84qE2OLWL9YC+gDgI7Lz8MZmGqJ3gt9addc3fhL2lBHCjC5CkCgYArTKIiUQirGlr9gRrCOE8ojE7+WhNDF32S8bo7oxNLYdUZS0bmI3fJZF6hREdfZloHoFCVo5qoheue9v1Z1qanl/4BCI90b36hH94WbksLKnQuA+vYsQGFGYZhN4dyw3jxRGe0xNDp+SWDPQQtg4rXuwnLUO1l+1Z1OszBdkI2UQ==";
    }

}
