package com.security;

import java.io.IOException;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class GrantedAuthoritySerializer extends JsonSerializer<GrantedAuthority> {
    @Override
    public void serialize(GrantedAuthority grantedAuthority, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(grantedAuthority.getAuthority());
    }
}
