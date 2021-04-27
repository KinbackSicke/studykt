package com.studykt.mapper;

import com.studykt.entity.Course;
import com.studykt.entity.CourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    long countByExample(CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int deleteByExample(CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int insert(Course record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int insertSelective(Course record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    List<Course> selectByExampleWithBLOBs(CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    List<Course> selectByExample(CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    Course selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int updateByExampleWithBLOBs(@Param("record") Course record, @Param("example") CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int updateByPrimaryKeySelective(Course record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int updateByPrimaryKeyWithBLOBs(Course record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course
     *
     * @mbg.generated Wed Apr 14 21:56:20 CST 2021
     */
    int updateByPrimaryKey(Course record);

    /**
     * select courses order by createTime
     * @return
     */
    List<Course> selectByCreateDateDESC(int numOfRecords);
}