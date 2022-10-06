package com.lab3.info.service.save;

import com.lab3.info.dto.ReportCardDto;
import com.lab3.info.exception.ReportException;

public interface Savable {
    String save(ReportCardDto report, String saveDir)
            throws ReportException;

}
