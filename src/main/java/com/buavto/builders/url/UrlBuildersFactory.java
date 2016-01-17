package com.buavto.builders.url;

public class UrlBuildersFactory {

    public UrlBuilder create(int id) {
        switch (id) {
            case 1:
                return createAvtoRiaBuilder();
            default:
                throw new UnsupportedOperationException("not implemented yet");
        }
    }

    public AvtoRiaBuilder createAvtoRiaBuilder() {
        return new AvtoRiaBuilder();
    }
}
