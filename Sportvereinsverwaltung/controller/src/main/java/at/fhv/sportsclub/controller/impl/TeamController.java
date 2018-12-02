package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.repository.team.TeamRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * businessMonkey
 * at.fhv.sportsclub.controller
 * TeamController
 * 07.11.2018 sge
 */
@Service
@Scope("prototype")
public class TeamController extends CommonController<TeamDTO, TeamEntity, TeamRepository> implements ITeamController {

    private TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamRepository repository) {
        super(repository, TeamDTO.class, TeamEntity.class);
        this.teamRepository = repository;
    }

    //region RMI wrapper methods
    @Override
    public ArrayList<TeamDTO> getAllEntries(SessionDTO session) {
        return new ArrayList<>(this.getAll());
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, TeamDTO teamDTO) {
        return this.saveOrUpdate(teamDTO);
    }

    @Override
    public TeamDTO getEntryDetails(SessionDTO session, String id){
        return this.getDetails(id, true);
    }

    @Override
    public TeamDTO getById(SessionDTO session, String id){
        return this.getDetails(id, false);
    }

    @Override
    public ListWrapper<TeamDTO> getByLeague(SessionDTO session, String leagueId) {
        List<TeamDTO> teamDTOS = mapAnyCollection(
                teamRepository.getAllByLeagueEquals(new ObjectId(leagueId)), TeamDTO.class, "TeamDTOMappingLight"
        );
        ListWrapper<TeamDTO> teamWrapper = new ListWrapper<>();
        if (teamDTOS.isEmpty()) {
            teamWrapper.setResponse(createErrorMessage("No results could be obtained for the given league"));
        } else {
            teamWrapper.setContents(new ArrayList<>(teamDTOS));
        }
        return teamWrapper;
    }

    @Override
    public ListWrapper<TeamDTO> getBySport(SessionDTO session, String sportId) {
        return null;
    }


    //endregion

}
