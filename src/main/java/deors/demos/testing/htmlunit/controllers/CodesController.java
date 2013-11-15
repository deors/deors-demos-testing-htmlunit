package deors.demos.testing.htmlunit.controllers;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import deors.demos.testing.htmlunit.entities.Codes;
import deors.demos.testing.htmlunit.services.CodesService;

/**
 * Controller for Codes table views.
 *
 * @author jorge.hidalgo
 * @version 1.0
 */
@Controller
public class CodesController
    implements ApplicationContextAware {

    /**
     * The logger.
     */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * The application context.
     */
    private ApplicationContext applicationContext;

    /**
     * The Codes service instance.
     */
    private CodesService codesService;

    /**
     * Default constructor.
     */
    public CodesController() {

        super();
    }

    /**
     * Controller method for view action.
     *
     * @param model the model object
     * @return the view to redirect to
     */
    @RequestMapping("/CodesView.do")
    public String view(ModelMap model) {

        try {
            Collection<? extends Codes> list = codesService.selectAll();
            model.addAttribute("list", list);
        } catch (org.springframework.dao.DataAccessException dae) {
            model.addAttribute("message", "ERROR reading Codes table: " + dae.getMessage());
        }

        return "CodesView.jsp";
    }

    /**
     * Controller method for add action.
     *
     * @param model the model object
     * @param code the 'code' field value
     * @param value the 'value' field value
     * @return the view to redirect to
     */
    @RequestMapping("/CodesAdd.do")
    public String add(ModelMap model, @RequestParam("code") String code, @RequestParam("value") String value) {

        Codes newCode = applicationContext.getBean("codes", Codes.class);
        newCode.setCode(code);
        newCode.setValue(value);

        try {
            codesService.insert(newCode);
        } catch (org.springframework.dao.DataAccessException dae) {
            model.addAttribute("message", "ERROR adding new records to Codes table: " + dae.getMessage());
        }

        return "CodesView.do";
    }

    /**
     * Controller method for update action.
     *
     * @param model the model object
     * @param code the 'code' field value
     * @param value the 'value' field value
     * @return the view to redirect to
     */
    @RequestMapping("/CodesUpdate.do")
    public String update(ModelMap model, @RequestParam("code") String code, @RequestParam("value") String value) {

        Codes updateCode = applicationContext.getBean("codes", Codes.class);
        updateCode.setCode(code);
        updateCode.setValue(value);

        try {
            codesService.update(updateCode);
        } catch (org.springframework.dao.DataAccessException dae) {
            model.addAttribute("message", "ERROR updating Codes table: " + dae.getMessage());
        }

        return "CodesView.do";
    }

    /**
     * Controller method for delete action.
     *
     * @param model the model object
     * @param code the 'code' field value
     * @return the view to redirect to
     */
    @RequestMapping("/CodesDelete.do")
    public String delete(ModelMap model, @RequestParam("code") String code) {

        Codes deleteCode = applicationContext.getBean("codes", Codes.class);
        deleteCode.setCode(code);

        try {
            codesService.delete(deleteCode);
        } catch (org.springframework.dao.DataAccessException dae) {
            model.addAttribute("message", "ERROR deleting records from Codes table: " + dae.getMessage());
        }

        return "CodesView.do";
    }

    /**
     * Sets the application context.
     *
     * @param applicationContext the application context
     */
    public void setApplicationContext(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    /**
     * Sets the Codes service instance.
     *
     * @param codesService the service instance
     */
    public void setCodesService(CodesService codesService) {

        this.codesService = codesService;
    }
}
