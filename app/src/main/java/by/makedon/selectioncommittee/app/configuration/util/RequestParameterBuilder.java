package by.makedon.selectioncommittee.app.configuration.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yahor Makedon
 */
public final class RequestParameterBuilder {
    private final Map<String, String> parameterToValueMap = new LinkedHashMap<>();

    private RequestParameterBuilder() {
    }

    public static RequestParameterBuilder builder() {
        return new RequestParameterBuilder();
    }

    public RequestParameterBuilder put(String parameter, String parameterValue) {
        parameterToValueMap.put(parameter, parameterValue);
        return this;
    }

    public String build() {
        return parameterToValueMap
            .entrySet()
            .stream()
            .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
            .collect(Collectors.joining("&"));
    }
}
