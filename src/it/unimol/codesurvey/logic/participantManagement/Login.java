package it.unimol.codesurvey.logic.participantManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unimol.codesurvey.bean.Participant;
import it.unimol.codesurvey.storage.ParticipantManagement;
import it.unimol.codesurvey.storage.SkillManagement;
import it.unimol.codesurvey.utilities.Utilities;


@WebServlet("/login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String gotoPage = "./index.html";
		String errorMessage = "";
		Participant loggedParticipant = null;

		HttpSession session = request.getSession();

		try {
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			
			loggedParticipant = ParticipantManagement.login(login, password);
			
			if (loggedParticipant != null) {
				boolean hasAddedSkills = SkillManagement.hasAddedSkills(loggedParticipant);
				if (!hasAddedSkills) {
					gotoPage = "./pages/additionalDetails.jsp";
				} else {
					gotoPage = "./getNextSnippetToAnalyze?participantId=" + loggedParticipant.getId();
				}
			} else {
				throw new LoginException();
			}

		} catch (LoginException loginException) {
			errorMessage = Utilities.defaultErrorMessage
					+ loginException.getMessage();
			gotoPage = "./error.jsp";
			loginException.printStackTrace();
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
		}

		session.setAttribute("errorMessage", errorMessage);
		session.setAttribute("loggedParticipant", loggedParticipant);

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
