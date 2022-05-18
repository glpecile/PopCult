package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.models.PageContainer;

import javax.ws.rs.core.*;

public class ResponseUtils {

    private ResponseUtils() {
        throw new AssertionError();
    }

    public static final int MAX_AGE = 31536000;

    public static <T> void setPaginationLinks(Response.ResponseBuilder response, PageContainer<T> pageContainer, UriInfo uriInfo) {
        if (pageContainer.hasNextPage()) {
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", pageContainer.getCurrentPage() + 1).build().toString(), "next");
        }
        if (pageContainer.hasPrevPage()) {
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", pageContainer.getCurrentPage() - 1).build().toString(), "prev");
        }
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", pageContainer.getLastPage()).build().toString(), "last");
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", pageContainer.getFirstPage()).build().toString(), "first");
        response.header("Total-Elements", pageContainer.getTotalCount());
    }

    public static Response.ResponseBuilder getConditionalCacheResponse(Request request, EntityTag eTag) {
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        if (response != null) {
            final CacheControl cacheControl = new CacheControl();
            cacheControl.setNoCache(true);
        }
        return response;
    }

    public static void setUnconditionalCache(Response.ResponseBuilder responseBuilder) {
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(MAX_AGE);
        responseBuilder.cacheControl(cacheControl);
    }
}
