package kr.easw.lesson02.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DownloadFileDto {
    private final String fileName;
}
