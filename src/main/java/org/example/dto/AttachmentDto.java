package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.enums.ContentType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDto {

    private String fileName;

    private ContentType contentType;

    private String base64Content;

}
