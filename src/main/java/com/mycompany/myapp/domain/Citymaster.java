package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Citymaster.
 */
@Entity
@Table(name = "citymaster")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Citymaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "status")
    private Boolean status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Citymaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Citymaster name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public Citymaster cityCode(String cityCode) {
        this.setCityCode(cityCode);
        return this;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Citymaster status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Citymaster)) {
            return false;
        }
        return getId() != null && getId().equals(((Citymaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Citymaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cityCode='" + getCityCode() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
