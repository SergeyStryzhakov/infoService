package com.lab3.info.dto;

import com.lab3.info.entity.Report;

import java.util.List;

public class ReportCardDTO {
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
        private final ReportCardDTO reportCardDTO;

        public Builder() {
            this.reportCardDTO = new ReportCardDTO();
        }

       public Builder studentName(String name) {
            reportCardDTO.setStudentName(name);
            return this;
       }

        public Builder studentGroup(String group) {
            reportCardDTO.setStudentGroup(group);
            return this;
        }

        public Builder reports(List<Report> reports) {
            reportCardDTO.setReports(reports);
            return this;
        }

        public ReportCardDTO build() {
            return reportCardDTO;
        }

    }
}
