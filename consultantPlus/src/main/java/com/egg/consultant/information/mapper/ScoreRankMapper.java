package com.egg.consultant.information.mapper;

import com.egg.consultant.information.pojo.ScoreRank;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-17:06
 * @Modified By:
 */
/**
 * public class ScoreRank
 * {
 *     private Long id;
 *     private String province;
 *     private Integer year;
 *     private String subject;
 *     private Integer score;
 *     private Integer rankNum;
 * }
 * */
@Mapper
public interface ScoreRankMapper
{
    @Insert("insert into score_rank(id,province,year,subject,score,rank_no) " +
            "values (#{id},#{province},#{year},#{subject},#{score},#{rankNo})")
    void insertScoreRank(ScoreRank scoreRank);

    @Select("select * from score_rank where province=#{province}")
    List<ScoreRank> selectByProvince(String province);
}
