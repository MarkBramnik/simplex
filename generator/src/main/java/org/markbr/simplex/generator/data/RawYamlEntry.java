package org.markbr.simplex.generator.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawYamlEntry {
    private String id;
    private int errorCode;
    private int httpStatus;
    private String message;
}
