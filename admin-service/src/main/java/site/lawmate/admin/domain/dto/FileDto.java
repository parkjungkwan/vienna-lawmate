package site.lawmate.admin.domain.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private String fileName;
    private byte[] fileData;
    private String fileType;
}
