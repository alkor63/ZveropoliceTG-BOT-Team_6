package com.ward_n6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Photo {
    private Long id;
    private String fileId;
    private String caption;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo photo)) return false;
        return Objects.equals(getId(), photo.getId()) && Objects.equals(getFileId(), photo.getFileId()) && Objects.equals(getCaption(), photo.getCaption());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFileId(), getCaption());
    }
}
