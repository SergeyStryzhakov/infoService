package com.lab3.info.dto;

import com.lab3.info.entity.Report;

import java.util.List;

public class ReportCardDto {
    private String studentName;
    private String studentGroup;
    private List<Report> reports;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public static class Builder {
        private final ReportCardDto reportCardDto;

        public Builder() {
            this.reportCardDto = new ReportCardDto();
        }

       public Builder studentName(String name) {
            reportCardDto.setStudentName(name);
            return this;
       }

        public Builder studentGroup(String group) {
            reportCardDto.setStudentGroup(group);
            return this;
        }

        public Builder reports(List<Report> reports) {
            reportCardDto.setReports(reports);
            return this;
        }

        public ReportCardDto build() {
            return reportCardDto;
        }

    }
}
