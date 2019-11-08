package it.unimol.codesurvey.logic.assessmentManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unimol.codesurvey.bean.Assessment;
import it.unimol.codesurvey.bean.Participant;
import it.unimol.codesurvey.bean.Question;
import it.unimol.codesurvey.bean.Snippet;
import it.unimol.codesurvey.exceptions.PermissionException;
import it.unimol.codesurvey.storage.AssessmentManagement;
import it.unimol.codesurvey.storage.QuestionManagement;
import it.unimol.codesurvey.utilities.Utilities;

@WebServlet("/storeComprehensionResult")
public class StoreComprehensionResult extends HttpServlet {

	private static final long serialVersionUID = 7958742118801385261L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Date endTime = new Date();
    	String gotoPage = "./pages/showVerificationQuestions.jsp";
		String errorMessage = "";
		HttpSession session = request.getSession();
		Participant loggedParticipant = (Participant) session.getAttribute("loggedParticipant");
		Snippet toUnderstand = (Snippet) session.getAttribute("snippetToComprehend");
		Date startingTime = (Date) session.getAttribute("startingTime");
		
		try {
			
			if(loggedParticipant == null)
				throw new PermissionException();
			
			boolean isUnderstood = Utilities.intToBoolean(Integer.valueOf(request.getParameter("isUnderstood")));
			int participantId = loggedParticipant.getId();
			int snippetId = toUnderstand.getId();
			long secondsNeeded = (endTime.getTime()-startingTime.getTime())/1000;
			
			Assessment assessment = new Assessment();
			assessment.setParticipantId(participantId);
			assessment.setSnippetId(snippetId);
			assessment.setSecondsNeeded(secondsNeeded);
			assessment.setUnderstood(isUnderstood);
			
			if(!isUnderstood){
				gotoPage = "./getNextSnippetToAnalyze?participantId=" + loggedParticipant.getId();
				assessment.setCorrectnessOfCheckingQuestions(0.0);
				AssessmentManagement.insert(assessment);
			} else {
				ArrayList<Question> questions = (ArrayList<Question>) QuestionManagement.getQuestionsBySnippetId(snippetId);
				session.setAttribute("assessment", assessment);
				session.setAttribute("questions", questions);
			}
						
		} catch (PermissionException permissionException) {
			errorMessage = Utilities.defaultErrorMessage
					+ permissionException.getMessage();
			gotoPage = "./error.jsp";
			permissionException.printStackTrace();
		} catch (PropertyVetoException propertyVetoException) {
			errorMessage = Utilities.defaultErrorMessage
					+ propertyVetoException.getMessage();
			gotoPage = "./error.jsp";
			propertyVetoException.printStackTrace();;
		} 

		session.setAttribute("errorMessage", errorMessage);
		
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
