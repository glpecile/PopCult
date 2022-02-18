package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.webapp.dto.output.ReportListCommentDto;
import ar.edu.itba.paw.webapp.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("lists-comments-reports")
@Component
public class ListCommentReportController {

    @Autowired
    private ReportService reportService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListCommentReportController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListCommentReports(@QueryParam("page") @DefaultValue(defaultPage) int page,
                                          @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final  PageContainer<ListCommentReport> listCommentReports = reportService.getListCommentReports(page, pageSize);

        if (listCommentReports.getElements().isEmpty()) {
            LOGGER.info("GET /lists-comments-reports: Returning empty list");
            return Response.noContent().build();
        }

        final List<ReportListCommentDto> reportListCommentDtoList = ReportListCommentDto.fromListCommentReportList(uriInfo, listCommentReports.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ReportListCommentDto>>(reportListCommentDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, listCommentReports, uriInfo);

        LOGGER.info("GET /lists-comments-reports: Returning page {} with {} results", listCommentReports.getCurrentPage(), listCommentReports.getElements().size());
        return response.build();
    }

    @GET
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListReport(@PathParam("id") int listCommentReportId) {
        final  ListCommentReport listCommentReport = reportService.getListCommentReportById(listCommentReportId).orElseThrow(ReportNotFoundException::new);

        LOGGER.info("GET /lists-comments-reports({}: Returning list comment report {}", listCommentReportId, listCommentReportId);
        return Response.ok(ReportListCommentDto.fromListCommentReport(uriInfo, listCommentReport)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response approveListReport(@PathParam("id") int listCommentReportId) {
        final ListCommentReport listCommentReport = reportService.getListCommentReportById(listCommentReportId).orElseThrow(ReportNotFoundException::new);

        reportService.approveListCommentReport(listCommentReport);

        LOGGER.info("PUT /lists-comments-requests/{}: List comment report {} approved. Comment {} deleted", listCommentReportId, listCommentReportId, listCommentReport.getComment().getCommentId());
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteListReport(@PathParam("id") int listCommentReportId) {
        final ListCommentReport listCommentReport = reportService.getListCommentReportById(listCommentReportId).orElseThrow(ReportNotFoundException::new);

        reportService.deleteListCommentReport(listCommentReport);

        LOGGER.info("DELETE /lists-comments-requests/{}: List comment report {} rejected", listCommentReportId, listCommentReportId);
        return Response.noContent().build();
    }
}
