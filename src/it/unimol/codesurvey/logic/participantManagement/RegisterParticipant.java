package it.unimol.codesurvey.logic.participantManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unimol.codesurvey.bean.Participant;
import it.unimol.codesurvey.exceptions.InvalidValueException;
import it.unimol.codesurvey.storage.ParticipantManagement;
import it.unimol.codesurvey.utilities.Utilities;

@WebServlet("/registerParticipant")
public class RegisterParticipant extends HttpServlet {

private static final long serialVersionUID = -7990803217197709443L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String gotoPage = "./pages/additionalDetails.jsp";
		String errorMessage = "";
		
		HttpSession session = request.getSession();
		
		try {
		Participant participant = new Participant();
		participant.setName(request.getParameter("name"));
		participant.setEmail(request.getParameter("email"));
		participant.setPosition(request.getParameter("position"));
		participant.setJavaExperience(Integer.valueOf(request.getParameter("javaExperience")));
		participant.setProgrammingExperience(Integer.valueOf(request.getParameter("programmingExperience")));
		participant.setUsername(request.getParameter("username"));
		participant.setPassword(request.getParameter("password"));
		
		if(ParticipantManagement.existsParticipant(participant.getUsername()))
			throw new InvalidValueException("This username already exists, please select a different one");
		
		ParticipantManagement.insert(participant);
			
		} catch (InvalidValueException invalidValueException) {
			errorMessage =  Utilities.defaultErrorMessage + invalidValueException.getMessage();
			gotoPage = "./error.jsp";
			invalidValueException.printStackTrace();
		} catch (IOException ioException) {
			errorMessage =  Utilities.defaultErrorMessage + ioException.getMessage();
			gotoPage = "./error.jsp";
			ioException.printStackTrace();
		} catch (PropertyVetoException propertyVetoException) {
			errorMessage =  Utilities.defaultErrorMessage + propertyVetoException.getMessage();
			gotoPage = "./error.jsp";
			propertyVetoException.printStackTrace();
		} 
			
		session.setAttribute("errorMessage", errorMessage);
		
		try {
			response.sendRedirect(gotoPage);
		} catch (IOException ioException) {
			errorMessage = Utilities.defaultErrorMessage + ioException.getMessage();
			gotoPage = "./error.jsp";
			ioException.printStackTrace();
		}
	}
	
}
