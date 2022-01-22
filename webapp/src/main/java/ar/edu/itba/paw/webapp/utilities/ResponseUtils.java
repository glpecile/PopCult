package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.models.PageContainer;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class ResponseUtils {

    private ResponseUtils() {
        throw new AssertionError();
    }

    public static <T> void setPaginationLinks(Response.ResponseBuilder response, PageContainer<T> pageContainer, UriInfo uriInfo){
        if(pageContainer.hasNextPage()){
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", pageContainer.getCurrentPage() + 1).build().toString(), "next");
        }
        if(pageContainer.hasPrevPage()){
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", pageContainer.getCurrentPage() - 1).build().toString(), "prev");
        }
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", pageContainer.getLastPage()).build().toString(), "last");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page",  pageContainer.getFirstPage()).build().toString(), "first");
    }

}
