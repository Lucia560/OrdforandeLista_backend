package com.example.OrdforandeLista.dto;

import com.example.OrdforandeLista.entities.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long tagId;

    @NotBlank(message = "Tag name is required")
    @Size(max = 100, message = "Tag name cannot exceed 100 characters")
    private String name;


    public Tag toEntity() {
        return Tag.builder()
                .tagId(this.tagId)
                .name(this.name)
                .build();
    }

}
