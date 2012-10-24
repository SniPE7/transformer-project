/********************************************************************
*  Licensed Materials - Property of IBM
*  
*  (c) Copyright IBM Corp.  2005, 2009 All Rights Reserved
*  
*  US Government Users Restricted Rights - Use, duplication or
*  disclosure restricted by GSA ADP Schedule Contract with
*  IBM Corp.
********************************************************************/

package examples.selfregistration;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.SchemaViolationException;
import com.ibm.itim.apps.identity.SelfRegistrationManager;
import com.ibm.itim.common.AttributeValue;
import com.ibm.itim.common.AttributeValues;
import com.ibm.itim.dataservices.model.domain.Person;

/**
 * Checks the submittion from selfReg.jsp to ensure that the last name and full
 * name attributes have been entered. If they have not, the user is redirected
 * back to selfReg.jsp and told to do so. If the user has entered those two
 * required fields, then a Request is made to SelfRegistrationManager to create
 * a new Person.
 */
public class ValidateData extends HttpServlet implements Servlet {
	private static final long serialVersionUID = -8614839300626093113L;

	/**
	 * Profile name of the person.
	 */
	private static final String PERSON_PROFILE = "Person";

	/**
	 * The attribute names for this self registration example.
	 */
	private static final String[] attributeName = { "sn", "cn", "givenname",
			"initials", "homepostaladdress", "ersharedsecret", "l",
			"roomnumber", "employeenumber", "title", "postaladdress", "mail",
			"telephonenumber", "mobile", "pager", "homephone" };

	/**
	 * Max number of Attributes for this jsp example
	 */
	private static final int numofAttrs = 16;

	private HttpServletRequest request;

	private HttpServletResponse response;

	/**
	 * Main function where I call for a check on the attributes, I call for the
	 * packaging of the attributes, and lastly call for the creation of a new
	 * person. I check for status and forward the status to status.jsp for
	 * display.
	 * 
	 * @param req
	 *            Source of attributes posted from the selfReg.jsp
	 * @param resp
	 *            Source of response I use to set flags.
	 * 
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		request = req;
		response = resp;
		
		// check if last name and full name fields were completed
		requiredFieldsCheck();

		PlatformContext platform = getPlatform();

		SelfRegistrationManager selfRegister = new SelfRegistrationManager(
				platform);

		// get attributes from the users input, and create a Person Object
		Person person = getAllPersonData();
		try {
			// create the new person
			selfRegister.createPerson(person);

			// if we make it here then the submission was successful
			String statusValue = "Your submission has been made.";

			forwardHelper("statusValue", statusValue, "/status.jsp");
		} catch (RemoteException e) {
			String errStatusValue = "createPerson RemoteException error because: \n"
					+ e;
			e.printStackTrace();
			forwardHelper("statusValue", errStatusValue, "/status.jsp");
		} catch (SchemaViolationException e) {
			String errStatusValue = "createPerson SchemaViolationException error because: \n"
					+ e;
			e.printStackTrace();
			forwardHelper("statusValue", errStatusValue, "/status.jsp");
		} catch (ApplicationException e) {
			String errStatusValue = "createPerson ApplicationException error because: \n"
					+ e;
			e.printStackTrace();
			forwardHelper("statusValue", errStatusValue, "/status.jsp");
		}
	}

	/**
	 * Forwards request to either selfReg.jsp or status.jsp. Embedded is a
	 * string value used by those jsp's.
	 * 
	 * @param attrName
	 *            The attribute name used in the jsp files. Either missingValue
	 *            or statusValue
	 * @param attrValue
	 *            The String value of missingValue or statusValue.
	 * @param jspName
	 *            selfReg.jsp or status.jsp
	 */
	private void forwardHelper(String attrName, String attrValue, String jspName) {
		request.setAttribute(attrName, attrValue);
		RequestDispatcher rd = request.getRequestDispatcher(jspName);
		try {
			rd.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks to make sure that the user entered there last name and full name.
	 * If the user failed to do this, then selfReg.jsp is called again and the
	 * user is prompted to fill out all astrik fields.
	 */
	private void requiredFieldsCheck() {
		String sn = request.getParameter(attributeName[0]);
		String cn = request.getParameter(attributeName[1]);
		String l = request.getParameter(attributeName[6]);
		if (sn.equals("") || cn.equals("") || l.equals("")) {
			String missingValue = "Please specify a value for all asterisked fields.";
			forwardHelper("missingValue", missingValue, "/selfReg.jsp");
		}
	}

	/**
	 * Gets the attributes set in selfReg.jsp. Creates a AttributeValues object
	 * with the attributes, and calls the Person object to create a new Person.
	 * 
	 * @return Newly created Person Object.
	 */
	private Person getAllPersonData() {

		AttributeValues attrValues = new AttributeValues();

		for (int x = 0; x < numofAttrs; x++) {
			String attributeValue = request.getParameter(attributeName[x]);
			if (attributeValue.equals(""))
				continue;
			AttributeValue av = new AttributeValue(attributeName[x],
					attributeValue);
			attrValues.put(av);
		}
		Person thisPerson = new Person(PERSON_PROFILE);
		thisPerson.setAttributes(attrValues);
		return thisPerson;
	}

	/**
	 * Gets the current platform from a properties file. The user must uncomment
	 * the app server values in context.properties.
	 * 
	 * @return PlatformContext
	 */
	private PlatformContext getPlatform() {
		String platformContextFactory = null;
		String ejbUser = null;
		String appServerURL = null;
		String ejbPwd = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle("context");
			appServerURL = rb.getString("appServerURL");
			ejbUser = rb.getString("ejbUser");
			platformContextFactory = rb.getString("platformContextFactory");
			ejbPwd = rb.getString("ejbPwd");
		} catch (MissingResourceException e) {
			String statusValue = "Failed to find the context because of a MissingResourceException : \n"
					+ e;
			e.printStackTrace();
			forwardHelper("statusValue", statusValue, "/status.jsp");
		}

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(InitialPlatformContext.CONTEXT_FACTORY, platformContextFactory);
		env.put(PlatformContext.PLATFORM_URL, appServerURL);
		env.put(PlatformContext.PLATFORM_PRINCIPAL, ejbUser);
		env.put(PlatformContext.PLATFORM_CREDENTIALS, ejbPwd);
		PlatformContext platform = null;
		
		try {
			platform = new InitialPlatformContext(env);
		} catch (RemoteException e) {
			String statusValue = "Failed to create a new platform because of a RemoteException : \n"
					+ e;
			e.printStackTrace();
			forwardHelper("statusValue", statusValue, "/status.jsp");

		} catch (ApplicationException e) {
			String statusValue = "Failed to create a new platform because of an ApplicationException : \n"
					+ e;
			e.printStackTrace();
			forwardHelper("statusValue", statusValue, "/status.jsp");

		}
		return platform;

	}
}
