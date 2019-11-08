package it.unimol.codesurvey.logic.assessmentManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unimol.codesurvey.bean.Answer;
import it.unimol.codesurvey.bean.Assessment;
import it.unimol.codesurvey.bean.Participant;
import it.unimol.codesurvey.bean.Question;
import it.unimol.codesurvey.storage.AnswerManagement;
import it.unimol.codesurvey.storage.AssessmentManagement;
import it.unimol.codesurvey.utilities.Utilities;

@WebServlet("/storeAnswers")
public class StoreAnswers extends HttpServlet {

	private static final long serialVersionUID = 7958742118801385261L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	String errorMessage = "";
		HttpSession session = request.getSession();
		Assessment assessment = (Assessment) session.getAttribute("assessment");
		@SuppressWarnings("unchecked")
		ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");
		Participant loggedParticipant = (Participant) session.getAttribute("loggedParticipant");
		String gotoPage = "./getNextSnippetToAnalyze?participantId=" + loggedParticipant.getId();
		
		try {
			
			double correctness = 0.0;
			
			for(Question question: questions){
				int providedAnswer = Integer.valueOf(request.getParameter("question" + question.getId()));
				Answer givenAnswer = AnswerManagement.getAnswerById(providedAnswer);
				if(givenAnswer.isCorrect())
					correctness++;
			}
			
			correctness = correctness/questions.size();
			assessment.setCorrectnessOfCheckingQuestions(correctness);
			int assessmentId = AssessmentManagement.insert(assessment);
			
			for(Question question: questions){
				int providedAnswer = Integer.valueOf(request.getParameter("question" + question.getId()));
				Answer givenAnswer = AnswerManagement.getAnswerById(providedAnswer);
				AnswerManagement.insertProvidedAnswer(givenAnswer, assessmentId);
			}
			
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
