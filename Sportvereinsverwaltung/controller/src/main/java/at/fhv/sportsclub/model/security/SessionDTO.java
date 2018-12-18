package at.fhv.sportsclub.model.security;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
@NoArgsConstructor
public @Data class SessionDTO<T> implements Serializable {

    private static final long serialVersionUID = 1111111092267757692L;

    private T sessionId;
    private Long expires;
    private List<RoleDTO> roles;
    private String myUserId;

    private ResponseMessageDTO responseMessage;
}
