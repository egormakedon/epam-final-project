package by.makedon.selectioncommittee.app.configuration.controller;

import java.util.Optional;

public final class Router {
    private final RouteType routeType;
    private String pagePath;

    private Router(RouteType routeType) {
        this.routeType = routeType;
    }

    public static Router redirectRouter() {
        return new Router(RouteType.REDIRECT);
    }

    public static Router forwardRouter() {
        return new Router(RouteType.FORWARD);
    }

    public boolean isRedirectRouter() {
        return routeType == RouteType.REDIRECT;
    }

    public boolean isForwardRouter() {
        return routeType == RouteType.FORWARD;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public Optional<String> getPagePath() {
        return Optional.ofNullable(pagePath);
    }

    private enum RouteType {
        FORWARD, REDIRECT
    }
}
