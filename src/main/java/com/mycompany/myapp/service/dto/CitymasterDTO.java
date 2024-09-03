package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Citymaster} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CitymasterDTO implements Serializable {

    private Long id;

    private String name;

    private String cityCode;

    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitymasterDTO)) {
            return false;
        }

        CitymasterDTO citymasterDTO = (CitymasterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, citymasterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitymasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cityCode='" + getCityCode() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
