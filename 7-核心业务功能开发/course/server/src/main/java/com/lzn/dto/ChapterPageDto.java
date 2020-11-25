package com.lzn.dto;

public class ChapterPageDto extends PageDto {
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "ChapterPateDto{" +
                "courseId='" + courseId + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
