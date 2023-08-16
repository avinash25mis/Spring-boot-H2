package avi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlDetails {
    int num;
    String url;
    boolean status;
}
