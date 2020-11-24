package com.lzn.mapper.my;

import com.lzn.dto.SortDto;
import org.apache.ibatis.annotations.Param;

public interface MyCourseMapper {
    int updateTime(@Param("courseId") String courseId);

    int updateSort(SortDto sortDto);

    int moveSortsForward(SortDto sortDto);

    int moveSortsBackward(SortDto sortDto);
}
