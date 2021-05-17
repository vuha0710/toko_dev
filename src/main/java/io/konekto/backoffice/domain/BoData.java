package io.konekto.backoffice.domain;

import io.konekto.backoffice.domain.enumration.DataKeyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "bo_data")
public class BoData extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DataKeyType dataKey;

    @Lob
    private String dataValue;

}
