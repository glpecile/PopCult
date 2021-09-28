package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
    @Autowired
    private ReportService reportService;

    private static final int itemsPerPage = 1; //TODO swap to 12. 1 is for pagination tests
    private static final int adminPanelItemsPerPage = 3;


    @RequestMapping("/admin")
    public ModelAndView adminPanel() {
        ModelAndView mav = new ModelAndView("adminPanel");
        PageContainer<ListReport> listReportPageContainer = reportService.getListReports(0, adminPanelItemsPerPage);
        PageContainer<ListCommentReport> listCommentReportPageContainer = reportService.getListCommentReports(0, adminPanelItemsPerPage);
        PageContainer<MediaCommentReport> mediaCommentReportPageContainer = reportService.getMediaCommentReports(0, adminPanelItemsPerPage);

        mav.addObject("listReportPageContainer", listReportPageContainer);
        mav.addObject("listCommentReportPageContainer", listCommentReportPageContainer);
        mav.addObject("mediaCommentReportPageContainer", mediaCommentReportPageContainer);

        return mav;
    }

    @RequestMapping("/admin/reports/lists")
    public ModelAndView listReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("listReports");
        PageContainer<ListReport> listReportPageContainer = reportService.getListReports(page - 1, itemsPerPage);
        mav.addObject("listReportPageContainer", listReportPageContainer);
        return mav;
    }

    @RequestMapping(value = "/admin/reports/lists/{reportId}/approve", method = {RequestMethod.POST})
    public ModelAndView approveListReport(HttpServletRequest request,
                                             @PathVariable(value = "reportId") final int reportId) {
        reportService.approveListReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/lists/{reportId}/reject", method = {RequestMethod.POST})
    public ModelAndView rejectListReport(HttpServletRequest request,
                                            @PathVariable(value = "reportId") final int reportId) {
        reportService.deleteListReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping("/admin/reports/lists/comments")
    public ModelAndView listCommentReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("listCommentReports");
        PageContainer<ListCommentReport> listCommentReportPageContainer = reportService.getListCommentReports(page - 1, itemsPerPage);
        mav.addObject("listCommentReportPageContainer", listCommentReportPageContainer);

        return mav;
    }

    @RequestMapping(value = "/admin/reports/lists/comments/{reportId}/approve", method = {RequestMethod.POST})
    public ModelAndView approveListCommentReport(HttpServletRequest request,
                                             @PathVariable(value = "reportId") final int reportId) {
        reportService.approveListCommentReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/lists/comments/{reportId}/reject", method = {RequestMethod.POST})
    public ModelAndView rejectListCommentReport(HttpServletRequest request,
                                            @PathVariable(value = "reportId") final int reportId) {
        reportService.deleteListCommentReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping("/admin/reports/media/comments")
    public ModelAndView mediaCommentReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("mediaCommentReports");
        PageContainer<MediaCommentReport> mediaCommentReportPageContainer = reportService.getMediaCommentReports(page - 1, itemsPerPage);
        mav.addObject("mediaCommentReportPageContainer", mediaCommentReportPageContainer);

        return mav;
    }

    @RequestMapping(value = "/admin/reports/media/comments/{reportId}/approve", method = {RequestMethod.POST})
    public ModelAndView approveMediaCommentReport(HttpServletRequest request,
                                             @PathVariable(value = "reportId") final int reportId) {
        reportService.approveMediaCommentReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/media/comments/{reportId}/reject", method = {RequestMethod.POST})
    public ModelAndView rejectMediaCommentReport(HttpServletRequest request,
                                            @PathVariable(value = "reportId") final int reportId) {
        reportService.deleteMediaCommentReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

}

