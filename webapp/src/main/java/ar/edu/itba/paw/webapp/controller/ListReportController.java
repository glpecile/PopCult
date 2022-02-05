package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.webapp.dto.output.ReportListDto;
import ar.edu.itba.paw.webapp.exceptions.ReportNotFoundException;
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
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListReports(@QueryParam("page") @DefaultValue(defaultPage) int page,
                                   @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        PageContainer<ListReport> listReports = reportService.getListReports(page, pageSize);

        if(listReports.getElements().isEmpty()) {
            LOGGER.info("GET /lists-reports: Returning empty list");
            return Response.noContent().build();
        }
        final List<ReportListDto> reportListDtoList = ReportListDto.fromListReportList(uriInfo, listReports.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ReportListDto>>(reportListDtoList){
        });
        ResponseUtils.setPaginationLinks(response, listReports, uriInfo);
        LOGGER.info("GET /lists-reports: Returning page {} with {} results", listReports.getCurrentPage(), listReports.getElements().size());
        return response.build();
    }

    @GET
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListReport(@PathParam("id") int listReportId) {
        ListReport listReport = reportService.getListReportById(listReportId).orElseThrow(ReportNotFoundException::new);

        LOGGER.info("GET /lists-reports({}: Returning list report {}", listReportId, listReportId);
        return Response.ok(ReportListDto.fromListReport(uriInfo, listReport)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response approveListReport(@PathParam("id") int listReportId) {
        ListReport listReport = reportService.getListReportById(listReportId).orElseThrow(ReportNotFoundException::new);

        reportService.approveListReport(listReport);

        LOGGER.info("PUT /lists-requests/{}: List report {} approved. List {} deleted", listReportId, listReportId, listReport.getMediaList().getMediaListId());
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteListReport(@PathParam("id") int listReportId) {
        ListReport listReport = reportService.getListReportById(listReportId).orElseThrow(ReportNotFoundException::new);

        reportService.deleteListReport(listReport);

        LOGGER.info("DELETE /lists-requests/{}: List report {} rejected", listReportId, listReportId);
        return Response.noContent().build();
    }

//    @RequestMapping(value = "/report/lists/{listId}", method = RequestMethod.GET)
//    public ModelAndView reportList(@PathVariable("listId") final int listId,
//                                   @ModelAttribute("reportForm") final ReportForm reportFom) {
//        ModelAndView mav = new ModelAndView("reports/reportList");
//        mav.addObject("list", listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new));
//        LOGGER.info("List: {} was reported.", listId);
//        return mav;
//    }
//
//    @RequestMapping(value = "/report/lists/{listId}", method = RequestMethod.POST, params = "addReport")
//    public ModelAndView postReportList(@PathVariable("listId") final int listId,
//                                       @Valid @ModelAttribute("reportForm") final ReportForm reportForm,
//                                       final BindingResult errors) {
//        if (errors.hasErrors()) {
//            LOGGER.warn("post /report/lists/{} : List report has errors.", listId);
//            return reportList(listId, reportForm);
//        }
//        MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
//        reportService.reportList(mediaList, reportForm.getReport());
//        return listsService.getMediaListById(listId).isPresent() ?
//                new ModelAndView("redirect:/lists/" + listId) :
//                new ModelAndView("lists/lists");
//    }
//
//    @RequestMapping(value = "/report/lists/{listId}/comment/{commentId}", method = RequestMethod.GET)
//    public ModelAndView reportListComment(@PathVariable("listId") final int listId,
//                                          @PathVariable("commentId") final int commentId,
//                                          @ModelAttribute("reportForm") final ReportForm reportForm) {
//        ModelAndView mav = new ModelAndView("reports/reportComment");
//        mav.addObject("comment", commentService.getListCommentById(commentId).orElseThrow(CommentNotFoundException::new));
//        mav.addObject("id", listId);
//        mav.addObject("type", "lists");
//        return mav;
//    }
//
//    @RequestMapping(value = "/report/lists/{listId}/comment/{commentId}", method = RequestMethod.POST, params = "addReport")
//    public ModelAndView postReportListComment(@PathVariable("listId") final int listId,
//                                              @PathVariable("commentId") final int commentId,
//                                              @Valid @ModelAttribute("reportForm") final ReportForm reportForm,
//                                              final BindingResult errors) {
//        if (errors.hasErrors()) {
//            LOGGER.warn("post /report/lists/{}/comment/{} : Comment report has errors.", listId, commentId);
//            return reportList(listId, reportForm);
//        }
//        ListComment listComment = commentService.getListCommentById(commentId).orElseThrow(CommentNotFoundException::new);
//        reportService.reportListComment(listComment, reportForm.getReport());
//        return new ModelAndView("redirect:/lists/" + listId);
//    }
//
//    @RequestMapping(value = "/report/media/{mediaId}/comment/{commentId}", method = RequestMethod.GET)
//    public ModelAndView reportMediaComment(@PathVariable("mediaId") final int mediaId,
//                                           @PathVariable("commentId") final int commentId,
//                                           @ModelAttribute("reportForm") final ReportForm reportForm) {
//        ModelAndView mav = new ModelAndView("reports/reportComment");
//        mav.addObject("comment", commentService.getMediaCommentById(commentId).orElseThrow(CommentNotFoundException::new));
//        mav.addObject("id", mediaId);
//        mav.addObject("type", "media");
//        LOGGER.info("Comment {} was reported.", commentId);
//        return mav;
//    }
//
//    @RequestMapping(value = "/report/media/{mediaId}/comment/{commentId}", method = RequestMethod.POST, params = "addReport")
//    public ModelAndView postReportMediaComment(@PathVariable("mediaId") final int mediaId,
//                                               @PathVariable("commentId") final int commentId,
//                                               @Valid @ModelAttribute("reportForm") final ReportForm reportForm,
//                                               final BindingResult errors) {
//        if (errors.hasErrors()) {
//            LOGGER.warn("/report/media/{}/comment/{} : Comment report has errors.", mediaId, commentId);
//            return reportMediaComment(mediaId, commentId, reportForm);
//        }
//        MediaComment mediaComment = commentService.getMediaCommentById(commentId).orElseThrow(CommentNotFoundException::new);
//        reportService.reportMediaComment(mediaComment, reportForm.getReport());
//        return new ModelAndView("redirect:/media/" + mediaId);
//    }

}
