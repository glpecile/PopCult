package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private ListsService listsService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/report/lists/{listId}", method = RequestMethod.GET)
    public ModelAndView reportList(@PathVariable("listId") final int listId,
                                   @ModelAttribute("reportForm") final ReportForm reportFom) {
        ModelAndView mav = new ModelAndView("reportList");
        mav.addObject("list", listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new));
        return mav;
    }

    @RequestMapping(value = "/report/lists/{listId}", method = RequestMethod.POST, params = "addReport")
    public ModelAndView postReportList(@PathVariable("listId") final int listId,
                                       @Valid @ModelAttribute("reportForm") final ReportForm reportForm,
                                       final BindingResult errors,
                                       HttpServletRequest request) {
        if (errors.hasErrors()) {
            return reportList(listId, reportForm);
        }
        reportService.reportList(listId, reportForm.getReport());
        //TODO preguntar si esta bien
        return listsService.getMediaListById(listId).isPresent() ?
                new ModelAndView("redirect:/lists/" + listId) :
                new ModelAndView("redirect:/lists");
    }

    @RequestMapping(value = "/report/lists/{listId}/comment/{commentId}", method = RequestMethod.GET)
    public ModelAndView reportListComment(@PathVariable("listId") final int listId,
                                          @PathVariable("commentId") final int commentId,
                                          @ModelAttribute("reportForm") final ReportForm reportForm) {
        ModelAndView mav = new ModelAndView("reportComment");
        mav.addObject("comment", commentService.getListCommentById(commentId).orElseThrow(CommentNotFoundException::new));
        mav.addObject("id", listId);
        mav.addObject("type", "lists");
        return mav;
    }

    @RequestMapping(value = "/report/lists/{listId}/comment/{commentId}", method = RequestMethod.POST, params = "addReport")
    public ModelAndView postReportListComment(@PathVariable("listId") final int listId,
                                              @PathVariable("commentId") final int commentId,
                                              @Valid @ModelAttribute("reportForm") final ReportForm reportForm,
                                              final BindingResult errors,
                                              HttpServletRequest request) {
        if (errors.hasErrors()) {
            return reportList(listId, reportForm);
        }
        reportService.reportListComment(listId, commentId, reportForm.getReport());
        return new ModelAndView("redirect:/lists/" + listId);
    }

    @RequestMapping(value = "/report/media/{mediaId}/comment/{commentId}", method = RequestMethod.GET)
    public ModelAndView reportMediaComment(@PathVariable("mediaId") final int mediaId,
                                           @PathVariable("commentId") final int commentId,
                                           @ModelAttribute("reportForm") final ReportForm reportForm) {
        ModelAndView mav = new ModelAndView("reportComment");
        mav.addObject("comment", commentService.getMediaCommentById(commentId).orElseThrow(CommentNotFoundException::new));
        mav.addObject("id", mediaId);
        mav.addObject("type", "media");
        return mav;
    }

    @RequestMapping(value = "/report/media/{mediaId}/comment/{commentId}", method = RequestMethod.POST, params = "addReport")
    public ModelAndView postReportMediaComment(@PathVariable("mediaId") final int mediaId,
                                               @PathVariable("commentId") final int commentId,
                                               @Valid @ModelAttribute("reportForm") final ReportForm reportForm,
                                               final BindingResult errors,
                                               HttpServletRequest request) {
        if (errors.hasErrors()) {
            return reportMediaComment(mediaId, commentId, reportForm);
        }
        reportService.reportMediaComment(mediaId, commentId, reportForm.getReport());
        return new ModelAndView("redirect:/media/" + mediaId);
    }

}
