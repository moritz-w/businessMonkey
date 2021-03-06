package at.fhv.sportsclub.factory;

import at.fhv.sportsclub.controller.impl.*;
import at.fhv.sportsclub.controller.interfaces.*;
import at.fhv.sportsclub.security.authentication.AuthenticationController;
import at.fhv.sportsclub.security.authentication.IAuthenticationController;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alex on 06.11.2018.
 */

@Component("rmiControllerFactory")
public class ControllerFactoryImpl implements IControllerFactory {

    @Lookup
    public PersonController createPersonController() {
        return null;
    }
    @Lookup
    public DepartmentController createDepartmentController() {
        return null;
    }
    @Lookup
    public TeamController createTeamController() {
        return null;
    }
    @Lookup
    public TournamentController createTournamentController(){
        return null;
    }
    @Lookup
    public MessageController createMessageController() {
        return null;
    }

    @Override
    public IPersonController getPersonController() throws RemoteException {
        return (IPersonController) UnicastRemoteObject.exportObject(createPersonController(), 0);
    }

    @Override
    public IDepartmentController getDepartmentController() throws RemoteException {
        return (IDepartmentController) UnicastRemoteObject.exportObject(createDepartmentController(), 0);
    }

    @Override
    public ITeamController getTeamController() throws RemoteException {
        return (ITeamController) UnicastRemoteObject.exportObject(createTeamController(), 0);
    }

    @Override
    public ITournamentController getTournamentController() throws RemoteException {
        return (ITournamentController) UnicastRemoteObject.exportObject(createTournamentController(), 0);
    }

    @Override
    public IMessageController getMessageController() throws RemoteException {
        return (IMessageController) UnicastRemoteObject.exportObject(createMessageController(), 0);
    }

}
