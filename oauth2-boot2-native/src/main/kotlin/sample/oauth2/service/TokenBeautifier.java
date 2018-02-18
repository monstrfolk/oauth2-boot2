package sample.oauth2.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TokenBeautifier {

    @Autowired
    private ObjectMapper objectMapper;

    public String formatJwtToken(String token) {
        try {
            return toPrettyJsonString(parseToken(token));
        } catch (Exception e) {
            return "";
        }
    }

    private Map<String, ?> parseToken(String base64Token) throws IOException {
        String token = base64Token.split("\\.")[1];
        return this.objectMapper.readValue(new Base64(token).decodeToString(),
                new TypeReference<Map<String, ?>>() {});
    }

    private String toPrettyJsonString(Object object) throws Exception {
        return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}