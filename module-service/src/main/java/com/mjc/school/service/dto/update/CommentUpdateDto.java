package com.mjc.school.service.dto.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {

    @NotNull(message = "Comment id should be present")
    private Long id;

    @Size(min = 5, max = 255, message = "Content length must be between 5 and 255")
    private String content;

    @Null(message = "News id shouldn't be present")
    private Long newsId;
}
