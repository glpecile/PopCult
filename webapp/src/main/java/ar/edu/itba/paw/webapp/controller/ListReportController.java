package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.webapp.dto.output.ReportListDto;
import ar.edu.itba.paw.webapp.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.mediaType.VndType;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("lists-reports")
@Component
public class ListReportController {

    @Autowired
    private ReportService reportService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListReportController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Produces(value = {VndType.APPLICATION_LISTS_REPORTS})
    public Response getListReports(@QueryParam("page") @DefaultValue(defaultPage) int page,
                                   @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final PageContainer<ListReport> listReports = reportService.getListReports(page, pageSize);

        if (listReports.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<ReportListDto> reportListDtoList = ReportListDto.fromListReportList(uriInfo, listReports.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ReportListDto>>(reportListDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, listReports, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results", uriInfo.getPath(), listReports.getCurrentPage(), listReports.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {VndType.APPLICATION_LISTS_REPORTS})
    public Response getListReport(@PathParam("id") int listReportId) {
        final ListReport listReport = reportService.getListReportById(listReportId).orElseThrow(ReportNotFoundException::new);

        LOGGER.info("GET /{}: Returning list report {}", uriInfo.getPath(), listReportId);
        return Response.ok(ReportListDto.fromListReport(uriInfo, listReport)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response approveListReport(@PathParam("id") int listReportId) {
        final ListReport listReport = reportService.getListReportById(listReportId).orElseThrow(ReportNotFoundException::new);

        reportService.approveListReport(listReport);

        LOGGER.info("PUT /{}: List report {} approved. List {} deleted", uriInfo.getPath(), listReportId, listReport.getMediaList().getMediaListId());
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteListReport(@PathParam("id") int listReportId) {
        final ListReport listReport = reportService.getListReportById(listReportId).orElseThrow(ReportNotFoundException::new);

        reportService.deleteListReport(listReport);

        LOGGER.info("DELETE /{}: List report {} rejected", uriInfo.getPath(), listReportId);
        return Response.noContent().build();
    }
}
