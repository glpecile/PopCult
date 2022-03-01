package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.webapp.dto.output.ReportMediaCommentDto;
import ar.edu.itba.paw.webapp.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("media-comments-reports")
@Component
public class MediaCommentReportController {

    @Autowired
    private ReportService reportService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaCommentReportController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListCommentReports(@QueryParam("page") @DefaultValue(defaultPage) int page,
                                          @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final PageContainer<MediaCommentReport> mediaCommentReports = reportService.getMediaCommentReports(page, pageSize);

        if (mediaCommentReports.getElements().isEmpty()) {
            LOGGER.info("GET /media-comments-reports: Returning empty list");
            return Response.noContent().build();
        }

        final List<ReportMediaCommentDto> reportMediaCommentDtoList = ReportMediaCommentDto.fromMediaCommentReportList(uriInfo, mediaCommentReports.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ReportMediaCommentDto>>(reportMediaCommentDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, mediaCommentReports, uriInfo);

        LOGGER.info("GET /media-comments-reports: Returning page {} with {} results", mediaCommentReports.getCurrentPage(), mediaCommentReports.getElements().size());
        return response.build();
    }

    @GET
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListReport(@PathParam("id") int mediaCommentReportId) {
        final MediaCommentReport mediaCommentReport = reportService.getMediaCommentReportById(mediaCommentReportId).orElseThrow(ReportNotFoundException::new);

        LOGGER.info("GET /media-comments-reports({}: Returning list comment report {}", mediaCommentReportId, mediaCommentReportId);
        return Response.ok(ReportMediaCommentDto.fromMediaCommentReport(uriInfo, mediaCommentReport)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response approveListReport(@PathParam("id") int mediaCommentReportId) {
        final MediaCommentReport mediaCommentReport = reportService.getMediaCommentReportById(mediaCommentReportId).orElseThrow(ReportNotFoundException::new);

        reportService.approveMediaCommentReport(mediaCommentReport);

        LOGGER.info("PUT /media-comments-requests/{}: Media comment report {} approved. Comment {} deleted", mediaCommentReportId, mediaCommentReportId, mediaCommentReport.getComment().getCommentId());
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteListReport(@PathParam("id") int mediaCommentReportId) {
        final MediaCommentReport mediaCommentReport = reportService.getMediaCommentReportById(mediaCommentReportId).orElseThrow(ReportNotFoundException::new);

        reportService.deleteMediaCommentReport(mediaCommentReport);

        LOGGER.info("DELETE /media-comments-requests/{}: Media comment report {} rejected", mediaCommentReportId, mediaCommentReportId);
        return Response.noContent().build();
    }
}
