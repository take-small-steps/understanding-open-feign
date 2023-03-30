package com.example.feigndemo.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.*;

@Slf4j
public final class Encoders implements Encoder {

    private final List<TypeEncoder> encoders = new ArrayList<>();
    private final Encoder defaultEncoder;

    public Encoders(Collection<TypeEncoder> encoders, Encoder defaultEncoder) {
        if (Objects.nonNull(encoders)) {
            this.encoders.addAll(encoders);
        }
        this.defaultEncoder = defaultEncoder;
    }

    public List<TypeEncoder> getEncoders() {
        return encoders;
    }

    public Encoder getDefaultEncoder() {
        return defaultEncoder;
    }

    @Override
    public void encode(Object body, Type bodyType, RequestTemplate template) throws EncodeException {
        Encoder encoder = resolve(bodyType, template);
        if (Objects.isNull(encoder)) {
            throw new EncodeException("no encoder is available");
        }

        log.debug("resolve encode: {}", encoder.getClass().getName());
        encoder.encode(body, bodyType, template);
    }

    private Encoder resolve(Type bodyType, RequestTemplate template) {
        Optional<TypeEncoder> encoder = encoders.stream().filter(it -> it.supportsType(bodyType, template)).findFirst();
        return encoder.isPresent() ? encoder.get() : defaultEncoder;
    }

}
