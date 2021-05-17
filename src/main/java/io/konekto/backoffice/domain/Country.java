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
@Table(name = "country")
public class Country {

    @Id
    private String id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private String code;
    private String currency;
    private String countryCode;
    private String languageCode;
    private boolean isPublished;
    private String flag;
}
