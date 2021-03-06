package at.fhv.sportsclub.entity.person;

import at.fhv.sportsclub.entity.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
public @Data class ContactEntity implements CommonEntity {

    @Id
    private String id;

    private String phoneNumber;
    @Indexed
    private String emailAddress;

    public ContactEntity() {}
}
