package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
public @Data class EncounterDTO implements Serializable, IDTO {

    public EncounterDTO() { }

    private static final long serialVersionUID = 1111111098267757692L; // changed 26.11

    private String id;

    private LocalDate date;
    private int time;
    @NotNull
    private String homeTeam;
    @NotNull
    private String guestTeam;
    private int homePoints;
    private int guestPoints;
    private ResponseMessageDTO response;

    private ModificationType modificationType;
}
