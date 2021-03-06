package at.fhv.sportsclub.security.session;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.RoleDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.security.UserDetails;
import at.fhv.sportsclub.security.exception.SessionInvalidException;
import org.apache.log4j.Logger;

import java.beans.Encoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public abstract class SessionManager<T> {

    private static final Long sessionTime = 360000L;
    private static final Logger logger = Logger.getRootLogger();

    private SessionIdService<T> sessionIdService;
    private volatile ConcurrentHashMap<T, Session> sessionStore;

    public SessionManager(SessionIdService<T> sessionIdService){
        this.sessionIdService = sessionIdService;
        this.sessionStore = new ConcurrentHashMap<>();
    }

    public SessionDTO<T> createNewSession(UserDetails userDetails){
        if(userDetails == null){
            return rejectRequest("No user was found for the given ID");
        }
        userDetails.setNonce(getNonce());
        T generatedSessionId = sessionIdService.generateSessionId(userDetails);

        Long expirationTime = System.currentTimeMillis() / 1000L + sessionTime;
        sessionStore.put(generatedSessionId, new Session(userDetails, expirationTime));
        return new SessionDTO<>(
                generatedSessionId, expirationTime, userDetails.getRoles(), userDetails.getUserId(),null
        );
    }

    /**
     * Not yet implemented, but should work like a OAuth like token refresh
     * with the old session that can be used to obtain a new session ID.
     */
    public SessionDTO<T> refreshSession(SessionDTO<T> session){
        return null;
    }

    public void invalidate(SessionDTO<T> session){
        sessionStore.remove(session.getSessionId());
    }

    public List<RoleDTO> getAccessRolesBySession(SessionDTO<T> session) throws SessionInvalidException {
        String message;
        if(!(message = validateSession(session)).isEmpty()){
            throw new SessionInvalidException(message);
        }
        return sessionStore.get(session.getSessionId()).getUserDetails().getRoles();
    }

    private String validateSession(SessionDTO<T> session){
        Session validSession = sessionStore.get(session.getSessionId());
        if (validSession == null){
            logger.info("Session ID not found " + session.getSessionId());
            return "Session ID invalid";
        }
        UserDetails userDetails = validSession.getUserDetails();
        if (!sessionIdService.validateSessionId(session.getSessionId(), userDetails)){
            logger.info("Session ID validation with user details failed for " + session.getSessionId());
            return "Session ID invalid";
        }
        if(isExpired(validSession.getExpires())){
            sessionStore.remove(session.getSessionId());
            return "Session expired";
        }
        return "";
    }

    private boolean isExpired(Long timestamp){
        return (System.currentTimeMillis()/1000L > timestamp);
    }

    private String getNonce(){
        SecureRandom sec = new SecureRandom();
        byte[] values = new byte[20];
        sec.nextBytes(values);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(values);
    }

    private SessionDTO<T> rejectRequest(String message){
        ResponseMessageDTO responseMessage = new ResponseMessageDTO(new LinkedList<>(), false);
        responseMessage.setSuccess(false);
        responseMessage.setInfoMessage(message);
        return new SessionDTO<>(null, 0L, null, null, responseMessage);
    }
}
