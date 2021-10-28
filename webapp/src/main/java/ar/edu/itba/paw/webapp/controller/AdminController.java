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
import ar.edu.itba.paw.webapp.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

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
        LOGGER.info("Administrator panel accessed");
        return new ModelAndView("admin/adminPanel");
    }

    @RequestMapping({"/admin/reports", "/admin/reports/lists"})
    public ModelAndView listReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("List reports accessed");
        ModelAndView mav = new ModelAndView("admin/listReports");
        PageContainer<ListReport> listReportPageContainer = reportService.getListReports(page - 1, itemsPerPage);
        long listCommentsReports = reportService.getListCommentReports(0, 1).getTotalCount();
        long mediaCommentsReports = reportService.getMediaCommentReports(0, 1).getTotalCount();
        mav.addObject("listReportPageContainer", listReportPageContainer);
        mav.addObject("listCommentsReports", listCommentsReports);
        mav.addObject("mediaCommentsReports", mediaCommentsReports);
        return mav;
    }

    @RequestMapping(value = "/admin/reports/lists/{reportId}", method = {RequestMethod.POST}, params = "approveReport")
    public ModelAndView approveListReport(HttpServletRequest request,
                                          @PathVariable(value = "reportId") final int reportId) {
        LOGGER.debug("Trying to approve list report {}", reportId);
        ListReport listReport = reportService.getListReportById(reportId).orElseThrow(ReportNotFoundException::new);
        reportService.approveListReport(listReport);
        LOGGER.info("List report {} approved", reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/lists/{reportId}", method = {RequestMethod.POST}, params = "rejectReport")
    public ModelAndView rejectListReport(HttpServletRequest request,
                                         @PathVariable(value = "reportId") final int reportId) {
        LOGGER.debug("Trying to reject list report {}", reportId);
        ListReport listReport = reportService.getListReportById(reportId).orElseThrow(ReportNotFoundException::new);
        reportService.deleteListReport(listReport);
        LOGGER.info("List report {} rejected", reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping("/admin/reports/lists/comments")
    public ModelAndView listCommentReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("List comments reports accessed");
        ModelAndView mav = new ModelAndView("admin/listCommentReports");
        PageContainer<ListCommentReport> listCommentReportPageContainer = reportService.getListCommentReports(page - 1, itemsPerPage);
        long listReports = reportService.getListReports(0, 1).getTotalCount();
        long mediaCommentsReports = reportService.getMediaCommentReports(0, 1).getTotalCount();
        mav.addObject("listCommentReportPageContainer", listCommentReportPageContainer);
        mav.addObject("listReports", listReports);
        mav.addObject("mediaCommentsReports", mediaCommentsReports);
        return mav;
    }

    @RequestMapping(value = "/admin/reports/lists/comments/{reportId}", method = {RequestMethod.POST}, params = "approveReport")
    public ModelAndView approveListCommentReport(HttpServletRequest request,
                                                 @PathVariable(value = "reportId") final int reportId) {
        LOGGER.debug("Trying to approve list comment report {}", reportId);
        ListCommentReport listCommentReport = reportService.getListCommentReportById(reportId).orElseThrow(ReportNotFoundException::new);
        reportService.approveListCommentReport(listCommentReport);
        LOGGER.info("List comment report {} approved", reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/lists/comments/{reportId}", method = {RequestMethod.POST}, params = "rejectReport")
    public ModelAndView rejectListCommentReport(HttpServletRequest request,
                                                @PathVariable(value = "reportId") final int reportId) {
        LOGGER.debug("Trying to reject list comment report {}", reportId);
        ListCommentReport listCommentReport = reportService.getListCommentReportById(reportId).orElseThrow(ReportNotFoundException::new);
        reportService.deleteListCommentReport(listCommentReport);
        LOGGER.info("List comment report {} rejected", reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping("/admin/reports/media/comments")
    public ModelAndView mediaCommentReports(@RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Media comments reports accessed");
        ModelAndView mav = new ModelAndView("admin/mediaCommentReports");
        PageContainer<MediaCommentReport> mediaCommentReportPageContainer = reportService.getMediaCommentReports(page - 1, itemsPerPage);
        long listReports = reportService.getListReports(0, 1).getTotalCount();
        long listCommentsReports = reportService.getListCommentReports(0, 1).getTotalCount();
        mav.addObject("mediaCommentReportPageContainer", mediaCommentReportPageContainer);
        mav.addObject("listReports", listReports);
        mav.addObject("listCommentsReports", listCommentsReports);
        return mav;
    }

    @RequestMapping(value = "/admin/reports/media/comments/{reportId}", method = {RequestMethod.POST}, params = "approveReport")
    public ModelAndView approveMediaCommentReport(HttpServletRequest request,
                                                  @PathVariable(value = "reportId") final int reportId) {
        LOGGER.debug("Trying to approve media comment report {}", reportId);
        MediaCommentReport mediaCommentReport = reportService.getMediaCommentReportById(reportId).orElseThrow(ReportNotFoundException::new);
        reportService.approveMediaCommentReport(mediaCommentReport);
        LOGGER.info("Media comment report {} approved", reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/reports/media/comments/{reportId}", method = {RequestMethod.POST}, params = "rejectReport")
    public ModelAndView rejectMediaCommentReport(HttpServletRequest request,
                                                 @PathVariable(value = "reportId") final int reportId) {
        LOGGER.debug("Trying to reject media comment report {}", reportId);
        MediaCommentReport mediaCommentReport = reportService.getMediaCommentReportById(reportId).orElseThrow(ReportNotFoundException::new);
        reportService.deleteMediaCommentReport(mediaCommentReport);
        LOGGER.info("Media comment report {} rejected", reportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = {"/admin/mods"}, method = {RequestMethod.GET})
    public ModelAndView modsPanel(@RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Moderators panel accessed");
        ModelAndView mav = new ModelAndView("admin/modsPanel");
        PageContainer<User> moderatorsContainer = moderatorService.getModerators(page - 1, itemsPerPage);
        long modRequests = moderatorService.getModRequesters(0, 1).getTotalCount();
        mav.addObject("moderatorsContainer", moderatorsContainer);
        mav.addObject("modRequests", modRequests);
        return mav;
    }

    @RequestMapping(value = "/admin/mods/{userId}", method = {RequestMethod.POST, RequestMethod.DELETE}, params = "removeMod")
    public ModelAndView removeMod(HttpServletRequest request,
                                  @PathVariable("userId") final int userId) {
        User user = userService.getById(userId).orElseThrow(UserNotFoundException::new);
        LOGGER.debug("Trying to remove moderator {}", userId);
        moderatorService.removeMod(user);
        LOGGER.info("Moderator {} removed", userId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/mods/requests", method = {RequestMethod.GET})
    public ModelAndView getModRequests(@RequestParam(value = "page", defaultValue = "1") final int page) {

        ModelAndView mav = new ModelAndView("admin/modsRequests");
        PageContainer<User> requestersContainer = moderatorService.getModRequesters(page - 1, itemsPerPage);
        long moderators = moderatorService.getModerators(0, 1).getTotalCount();
        mav.addObject("requestersContainer", requestersContainer);
        mav.addObject("moderators", moderators);
        LOGGER.info("Moderators requests panel accessed");

        return mav;
    }

    @RequestMapping(value = "/admin/mods/requests/{userId}", method = {RequestMethod.POST}, params = "addMod")
    public ModelAndView promoteToMod(HttpServletRequest request,
                                     @PathVariable("userId") final int userId) {
        User user = userService.getById(userId).orElseThrow(UserNotFoundException::new);
        LOGGER.debug("Trying to promote moderator {}", userId);
        moderatorService.promoteToMod(user);
        LOGGER.info("Moderator {} promoted", userId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/admin/mods/requests/{userId}", method = {RequestMethod.POST, RequestMethod.DELETE}, params = "rejectRequest")
    public ModelAndView rejectModRequest(HttpServletRequest request,
                                         @PathVariable("userId") final int userId) {
        User user = userService.getById(userId).orElseThrow(UserNotFoundException::new);
        LOGGER.debug("Trying to reject moderator {} request", userId);
        moderatorService.removeRequest(user);
        LOGGER.info("Moderator {} request rejected", userId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/requestMod")
    ModelAndView requestMod() {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        LOGGER.debug("Trying to generate a new moderator {} request", user.getUserId());

        try {
            moderatorService.addModRequest(user);
        } catch (UserAlreadyIsModException e) {
            LOGGER.info("User {} is already a moderator", user.getUserId());
            ModelAndView mav = new ModelAndView("errors/error");
            mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
            mav.addObject("description", messageSource.getMessage("exception.userAlreadyIsMod", null, Locale.getDefault()));
            return mav;
        } catch (ModRequestAlreadyExistsException e) {
            LOGGER.info("User {} has already a pending request to be a moderator", user.getUserId());
            ModelAndView mav = new ModelAndView("errors/error");
            mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
            mav.addObject("description", messageSource.getMessage("exception.modRequestAlreadyExists", null, Locale.getDefault()));
            return mav;
        }
        LOGGER.info("User {} moderator request sent", user.getUserId());
        return new ModelAndView("modRequest/modRequestSent");
    }
}

