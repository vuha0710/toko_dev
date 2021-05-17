package io.konekto.backoffice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "village")
public class Village {

    @Id
    private String id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private String districtId;
    private String status;
}
