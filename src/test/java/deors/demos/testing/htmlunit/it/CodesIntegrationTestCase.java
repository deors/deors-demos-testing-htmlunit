package deors.demos.testing.htmlunit.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class CodesIntegrationTestCase {

    private static final Logger logger = LoggerFactory.getLogger(CodesIntegrationTestCase.class);

    private static String TARGET_SERVER_URL;

    @BeforeClass
    public static void initEnvironment() {

        TARGET_SERVER_URL = getConfigurationProperty(
            "TARGET_SERVER_URL",
            "test.target.server.url",
            "http://localhost:56080/deors-demos-testing-htmlunit");

        logger.info("using target server at: " + TARGET_SERVER_URL);
    }

    private static String getConfigurationProperty(String envKey, String sysKey, String defValue) {

        String retValue = defValue;
        String envValue = System.getenv(envKey);
        String sysValue = System.getProperty(sysKey);
        // system property prevails over environment variable
        if (sysValue != null) {
            retValue = sysValue;
        } else if (envValue != null) {
            retValue = envValue;
        }
        return retValue;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCodesCrud()
        throws MalformedURLException, IOException {

        WebClient client = new WebClient();

        // first entry to view page
        HtmlPage viewPage = client.getPage(TARGET_SERVER_URL + "/CodesView.do");

        assertEquals("Codes View Page", viewPage.getTitleText());

        // confirm initial data list
        List<DomAttr> codeAttrNodes = (List<DomAttr>) viewPage.getByXPath("//input[@name='code']/@value");
        List<String> codes = new ArrayList<String>();
        for (DomAttr attr : codeAttrNodes) {
            codes.add(attr.getTextContent());
        }

        assertEquals(3, codes.size());
        assertTrue(codes.contains("A"));
        assertTrue(codes.contains("C"));
        assertTrue(codes.contains("D"));

        // get add button
        HtmlForm addForm = viewPage.getFormByName("add");
        HtmlSubmitInput addButton = addForm.getInputByName("add");

        // submit to add a new record
        HtmlPage detailPage = addButton.click();

        assertEquals("Codes Detail Page", detailPage.getTitleText());

        HtmlForm detailForm = detailPage.getFormByName("detail");
        HtmlTextInput codeField = detailForm.getInputByName("code");
        HtmlTextInput valueField = detailForm.getInputByName("value");
        HtmlSubmitInput okButton = detailForm.getInputByName("ok");

        codeField.setValueAttribute("P");
        valueField.setValueAttribute("postponed");

        // confirm add and return to view page
        viewPage = okButton.click();

        assertEquals("Codes View Page", viewPage.getTitleText());

        // confirm the new record has been added
        codeAttrNodes = (List<DomAttr>) viewPage.getByXPath("//input[@name='code']/@value");
        codes = new ArrayList<String>();
        for (DomAttr attr : codeAttrNodes) {
            codes.add(attr.getTextContent());
        }

        assertEquals(4, codes.size());
        assertTrue(codes.contains("P"));

        List<DomAttr> valueAttrNodes = (List<DomAttr>) viewPage.getByXPath("//input[@name='value']/@value");
        List<String> values = new ArrayList<String>();
        for (DomAttr attr : valueAttrNodes) {
            values.add(attr.getTextContent());
        }

        assertTrue(values.contains("postponed"));

        HtmlForm pForm = viewPage.getFormByName("form_P");
        HtmlSubmitInput updateButton = pForm.getInputByName("update");

        // go to detail page to update record
        detailPage = updateButton.click();

        assertEquals("Codes Detail Page", detailPage.getTitleText());

        detailForm = detailPage.getFormByName("detail");
        codeField = detailForm.getInputByName("code");
        valueField = detailForm.getInputByName("value");
        okButton = detailForm.getInputByName("ok");

        valueField.setValueAttribute("updated value");

        // confirm update and return to view page
        viewPage = okButton.click();

        assertEquals("Codes View Page", viewPage.getTitleText());

        // confirm the record has been updated
        valueAttrNodes = (List<DomAttr>) viewPage.getByXPath("//input[@name='value']/@value");
        values = new ArrayList<String>();
        for (DomAttr attr : valueAttrNodes) {
            values.add(attr.getTextContent());
        }

        assertTrue(values.contains("updated value"));

        pForm = viewPage.getFormByName("form_P");
        HtmlSubmitInput deleteButton = pForm.getInputByName("delete");

        // go to detail page to delete record
        detailPage = deleteButton.click();

        assertEquals("Codes Detail Page", detailPage.getTitleText());

        detailForm = detailPage.getFormByName("detail");
        okButton = detailForm.getInputByName("ok");

        // confirm delete and return to view page
        viewPage = okButton.click();

        assertEquals("Codes View Page", viewPage.getTitleText());

        // confirm final data list
        codeAttrNodes = (List<DomAttr>) viewPage.getByXPath("//input[@name='code']/@value");
        codes = new ArrayList<String>();
        for (DomAttr attr : codeAttrNodes) {
            codes.add(attr.getTextContent());
        }

        assertEquals(3, codes.size());
        assertTrue(codes.contains("A"));
        assertTrue(codes.contains("C"));
        assertTrue(codes.contains("D"));

        client.close();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCodesError500DuplicateKey()
        throws MalformedURLException, IOException {

        WebClient client = new WebClient();

        // first entry to view page
        HtmlPage viewPage = client.getPage(TARGET_SERVER_URL + "/CodesView.do");

        // get add button
        HtmlForm addForm = viewPage.getFormByName("add");
        HtmlSubmitInput addButton = addForm.getInputByName("add");

        // submit to add a new record
        HtmlPage detailPage = addButton.click();

        assertEquals("Codes Detail Page", detailPage.getTitleText());

        HtmlForm detailForm = detailPage.getFormByName("detail");
        HtmlTextInput codeField = detailForm.getInputByName("code");
        HtmlTextInput valueField = detailForm.getInputByName("value");
        HtmlSubmitInput okButton = detailForm.getInputByName("ok");

        codeField.setValueAttribute("A");
        valueField.setValueAttribute("active again");

        // confirm add and return to view page
        viewPage = okButton.click();

        assertEquals("Codes View Page", viewPage.getTitleText());

        // check for error returned
        String viewText = viewPage.asText();

        assertTrue(viewText.contains("ERROR adding new records to Codes table"));
        assertTrue(viewText.contains("could not insert"));
        assertTrue(viewText.contains("entities.CodesImpl"));

        client.close();
    }
}
