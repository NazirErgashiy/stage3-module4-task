package com.mjc.school.service.dto.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagUpdateDto {

    @NotNull(message = "Tag id should be present")
    private Long id;

    @Size(min = 3, max = 15, message = "Name length must be between 3 and 15")
    private String name;
}
