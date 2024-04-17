package by.makedon.selectioncommittee.app.configuration.controller;

public class Router {
    private String pagePath;
    private RouteType route;

    public enum RouteType {
        FORWARD, REDIRECT
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
    public void setRoute(RouteType route) {
        this.route = route;
    }
    public String getPagePath() {
        return pagePath;
    }
    public RouteType getRoute() {
        return route;
    }
}
