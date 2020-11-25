package com.lzn.dto;

public class SectionPageDto extends PageDto {
    private String courseId;

    private String chapterId;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return "SectionPageDto{" +
                "courseId='" + courseId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

}
