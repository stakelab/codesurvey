package it.unimol.codesurvey.logic.assessmentManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unimol.codesurvey.bean.Assessment;
import it.unimol.codesurvey.bean.Participant;
import it.unimol.codesurvey.bean.Snippet;
import it.unimol.codesurvey.exceptions.PermissionException;
import it.unimol.codesurvey.storage.AssessmentManagement;
import it.unimol.codesurvey.storage.SnippetManagement;
import it.unimol.codesurvey.utilities.Utilities;

@WebServlet("/getNextSnippetToAnalyze")
public class GetNextSnippetToAnalyze extends HttpServlet {

	private static final long serialVersionUID = 7958742118801385261L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	String gotoPage = "./pages/showSnippetToComprehend.jsp";
		String errorMessage = "";
		HttpSession session = request.getSession();
		Participant loggedParticipant = (Participant) session.getAttribute("loggedParticipant");
		
		try {
			if(loggedParticipant == null)
				throw new PermissionException();
			
			Integer participantId = Integer.valueOf(request.getParameter("participantId"));
			ArrayList<Assessment> assessmentsByTheParticipant = (ArrayList<Assessment>) AssessmentManagement.getAssessmentsByParticipantId(participantId);
			if(assessmentsByTheParticipant.size() >= Utilities.numberOfSnippetsToUnderstand){
				gotoPage = "./pages/completed.jsp";
			} else {
				ArrayList<Snippet> snippets = (ArrayList<Snippet>) SnippetManagement.getSnippets();
				Collections.sort(snippets);
				boolean found = false;
				for(Snippet snippet:snippets){
					if(!AssessmentManagement.hasParticipantAlreadyEvaluatedAsnippet(participantId, snippet.getId())){
						session.setAttribute("snippetToComprehend", snippets.get(0));
						found = true;
						break;
					}
				}
				if(!found)
					gotoPage = "./pages/completed.jsp";
			}
			
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
