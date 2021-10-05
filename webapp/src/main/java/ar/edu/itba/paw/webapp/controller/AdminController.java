package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ModeratorService;
import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyIsModException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class AdminController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModeratorService moderatorService;
    @Autowired
    private MessageSource messageSource;

    private static final int itemsPerPage = 12;
    private static final int adminPanelItemsPerPage = 3;


    @RequestMapping("/admin")
    public ModelAndView adminPanel() {
        ModelAndView mav = new ModelAndView("admin/adminPanel");

        return mav;
    }

    @RequestMapping({"/admin/reports", "/admin/reports/lists"})
    public ModelAndView listReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("admin/listReports");
        PageContainer<ListReport> listReportPageContainer = reportService.getListReports(page - 1, itemsPerPage);
        int listCommentsReports = reportService.getListCommentReports(0, 1).getTotalCount();
        int mediaCommentsReports = reportService.getMediaCommentReports(0, 1).getTotalCount();
        mav.addObject("listReportPageContainer", listReportPageContainer);
        mav.addObject("listCommentsReports", listCommentsReports);
        mav.addObject("mediaCommentsReports", mediaCommentsReports);
        return mav;
    }

    @RequestMapping(value = "/admin/reports/lists/{reportId}", method = {RequestMethod.POST}, params = "approveReport")
    public ModelAndView approveListReport(HttpServletRequest request,
                                          @PathVariable(value = "reportId") final int reportId) {
        reportService.approveListReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/lists/{reportId}", method = {RequestMethod.POST}, params = "rejectReport")
    public ModelAndView rejectListReport(HttpServletRequest request,
                                         @PathVariable(value = "reportId") final int reportId) {
        reportService.deleteListReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping("/admin/reports/lists/comments")
    public ModelAndView listCommentReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("admin/listCommentReports");
        PageContainer<ListCommentReport> listCommentReportPageContainer = reportService.getListCommentReports(page - 1, itemsPerPage);
        int listReports = reportService.getListReports(0, 1).getTotalCount();
        int mediaCommentsReports = reportService.getMediaCommentReports(0, 1).getTotalCount();
        mav.addObject("listCommentReportPageContainer", listCommentReportPageContainer);
        mav.addObject("listReports", listReports);
        mav.addObject("mediaCommentsReports", mediaCommentsReports);
        return mav;
    }

    @RequestMapping(value = "/admin/reports/lists/comments/{reportId}", method = {RequestMethod.POST}, params = "approveReport")
    public ModelAndView approveListCommentReport(HttpServletRequest request,
                                                 @PathVariable(value = "reportId") final int reportId) {
        reportService.approveListCommentReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/lists/comments/{reportId}", method = {RequestMethod.POST}, params = "rejectReport")
    public ModelAndView rejectListCommentReport(HttpServletRequest request,
                                                @PathVariable(value = "reportId") final int reportId) {
        reportService.deleteListCommentReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping("/admin/reports/media/comments")
    public ModelAndView mediaCommentReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("admin/mediaCommentReports");
        PageContainer<MediaCommentReport> mediaCommentReportPageContainer = reportService.getMediaCommentReports(page - 1, itemsPerPage);
        int listReports = reportService.getListReports(0, 1).getTotalCount();
        int listCommentsReports = reportService.getListCommentReports(0, 1).getTotalCount();
        mav.addObject("mediaCommentReportPageContainer", mediaCommentReportPageContainer);
        mav.addObject("listReports", listReports);
        mav.addObject("listCommentsReports", listCommentsReports);
        return mav;
    }

    @RequestMapping(value = "/admin/reports/media/comments/{reportId}", method = {RequestMethod.POST}, params = "approveReport")
    public ModelAndView approveMediaCommentReport(HttpServletRequest request,
                                                  @PathVariable(value = "reportId") final int reportId) {
        reportService.approveMediaCommentReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/media/comments/{reportId}", method = {RequestMethod.POST}, params = "rejectReport")
    public ModelAndView rejectMediaCommentReport(HttpServletRequest request,
                                                 @PathVariable(value = "reportId") final int reportId) {
        reportService.deleteMediaCommentReport(reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = {"/admin/mods"}, method = {RequestMethod.GET})
    public ModelAndView modsPanel(@RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("admin/modsPanel");
        PageContainer<User> moderatorsContainer = moderatorService.getModerators(page - 1, itemsPerPage);
        int modRequests = moderatorService.getModRequesters(0, 1).getTotalCount();
        mav.addObject("moderatorsContainer", moderatorsContainer);
        mav.addObject("modRequests", modRequests);
        return mav;
    }

    @RequestMapping(value = "/admin/mods/{userId}", method = {RequestMethod.POST, RequestMethod.DELETE}, params = "removeMod")
    public ModelAndView removeMod(HttpServletRequest request,
                                  @PathVariable("userId") final int userId) {
        moderatorService.removeMod(userId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/mods/requests", method = {RequestMethod.GET})
    public ModelAndView getModRequests(@RequestParam(value = "page", defaultValue = "1") final int page) {
        ModelAndView mav = new ModelAndView("admin/modsRequests");
        PageContainer<User> requestersContainer = moderatorService.getModRequesters(page - 1, itemsPerPage);
        int moderators = moderatorService.getModerators(0, 1).getTotalCount();
        mav.addObject("requestersContainer", requestersContainer);
        mav.addObject("moderators", moderators);
        return mav;
    }

    @RequestMapping(value = "/admin/mods/requests/{userId}", method = {RequestMethod.POST}, params = "addMod")
    public ModelAndView promoteToMod(HttpServletRequest request,
                                     @PathVariable("userId") final int userId) {
        moderatorService.promoteToMod(userId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/mods/requests/{userId}", method = {RequestMethod.POST, RequestMethod.DELETE}, params = "rejectRequest")
    public ModelAndView rejectModRequest(HttpServletRequest request,
                                         @PathVariable("userId") final int userId) {
        moderatorService.removeRequest(userId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/requestMod")
    ModelAndView requestMod() {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        try {
            moderatorService.addModRequest(user.getUserId());
        } catch (UserAlreadyIsModException e) {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
            mav.addObject("description", messageSource.getMessage("exception.userAlreadyIsMod", null, Locale.getDefault()));
            return mav;
        } catch (ModRequestAlreadyExistsException e) {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
            mav.addObject("description", messageSource.getMessage("exception.modRequestAlreadyExists", null, Locale.getDefault()));
            return mav;
        }
        return new ModelAndView("modRequest/modRequestSent");
    }
}

