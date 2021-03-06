package at.fhv.sportsclub.repository.tournament;

import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import at.fhv.sportsclub.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends CommonRepository<TournamentEntity, String>, CustomTournamentRepository  {
}
