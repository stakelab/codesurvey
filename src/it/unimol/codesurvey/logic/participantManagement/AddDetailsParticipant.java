package it.unimol.codesurvey.logic.participantManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unimol.codesurvey.bean.Participant;
import it.unimol.codesurvey.exceptions.PermissionException;
import it.unimol.codesurvey.storage.SkillManagement;
import it.unimol.codesurvey.utilities.Utilities;

@WebServlet("/addDetailsParticipant")
public class AddDetailsParticipant extends HttpServlet {

private static final long serialVersionUID = -7990803217197709443L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String gotoPage = "./index.html";
		String errorMessage = "";
		HttpSession session = request.getSession();
		Participant loggedParticipant = (Participant) session.getAttribute("loggedParticipant");
		
		try {
			if(loggedParticipant == null)
				throw new PermissionException();
			
			Map<String, String[]> parameters = request.getParameterMap();
			for (Map.Entry<String, String[]> parameter : parameters.entrySet()) {
				if (parameter.getKey().startsWith("skill")) {
					String skill = parameter.getKey().replaceAll("^skill", "");
					String experience = parameter.getValue()[0];
					SkillManagement.upsert(loggedParticipant, skill, experience);
				}
			}
			
			SkillManagement.retrieveExperience(loggedParticipant);
		} catch (IOException ioException) {
			errorMessage = Utilities.defaultErrorMessage
					+ ioException.getMessage();
			gotoPage = "./error.jsp";
			ioException.printStackTrace();
		} catch (PropertyVetoException propertyVetoException) {
			errorMessage = Utilities.defaultErrorMessage
					+ propertyVetoException.getMessage();
			gotoPage = "./error.jsp";
			propertyVetoException.printStackTrace();
		} catch (PermissionException permissionException) {
			errorMessage = Utilities.defaultErrorMessage
					+ permissionException.getMessage();
			gotoPage = "./error.jsp";
			permissionException.printStackTrace();
		}
		
		session.setAttribute("errorMessage", errorMessage);
		session.setAttribute("startingTime", new Date());
		
		try {
			response.sendRedirect(gotoPage);
		} catch (IOException ioException) {
			errorMessage = Utilities.defaultErrorMessage
					+ ioException.getMessage();
			gotoPage = "./error.jsp";
			ioException.printStackTrace();
		}
	}
}
