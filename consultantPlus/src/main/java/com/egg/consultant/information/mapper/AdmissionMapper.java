package com.egg.consultant.information.mapper;

import com.egg.consultant.information.pojo.Admission;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-17:05
 * @Modified By:
 */
/**
 *     id          BIGINT AUTO_INCREMENT PRIMARY KEY,
 *     major       varchar(32) NULL,
 *     school      varchar(32) NULL,
 *     school_id   BIGINT      NULL,
 *     province    VARCHAR(16) NULL,
 *     year        SMALLINT    NULL,
 *     subject     VARCHAR(16) NULL,
 *     batch       VARCHAR(32) DEFAULT '本科一批',
 *     min_score   SMALLINT    NULL,
 *     min_rank    INT         NULL,
 *     avg_score   SMALLINT    NULL,
 *     plan_count  INT         NULL,
 *     source_doc  VARCHAR(128) NULL,
 *     created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
 * */
@Mapper
public interface AdmissionMapper
{
    @Insert("insert into admission(major,school,school_id,province,year,subject,batch,min_score,min_rank,avg_score,plan_count,source_doc,created_at)"
            + "values (#{major},#{school},#{schoolId},#{province},#{year},#{subject},COALESCE(#{batch}, '本科一批'),#{minScore},#{minRank},#{avgScore},#{planCount},#{sourceDoc},#{createdAt})")
    void insertAdmission(Admission admission);

    @Delete("delete from admission where source_doc=#{sourceDoc}")
    void deleteByDoc(String sourceDoc);

    @Select("""
        select count(*)
        from admission
        where school_id <=> #{schoolId}
          and major     <=> #{major}
          and province  <=> #{province}
          and year      <=> #{year}
          and subject   <=> #{subject}
          and batch     <=> #{batch}
        """)
    int countByUniqueKey(Admission admission);

    @Select("""
    <script>
    select school, major, province, year, subject, batch,
           min_score, min_rank, avg_score, plan_count, source_doc
    from admission
    where school = #{schoolName}
    <if test="majorKeyword != null and majorKeyword != ''">
        and major like concat('%', #{majorKeyword}, '%')
    </if>
    <if test="year != null">
        and year = #{year}
    </if>
    order by year desc, min_score desc
    limit 50
    </script>
    """)
    List<Admission> queryAdmission(@Param("schoolName") String schoolName,
                                   @Param("majorKeyword") String majorKeyword,
                                   @Param("year") Integer year);

    @Select("""
    <script>
    select school, major, province, year, subject, batch,
           min_score, min_rank, avg_score, plan_count, source_doc
    from admission
    where major like concat('%', #{majorKeyword}, '%')
    <if test="province != null and province != ''">
        and province = #{province}
    </if>
    order by year desc, min_score desc
    limit 50
    </script>
    """)
    List<Admission> queryMajorAdmissions(@Param("majorKeyword") String majorKeyword,
                                         @Param("province") String province);

    @Select("""
    <script>
    select school, major, province, year, subject, batch,
           min_score, min_rank, avg_score, plan_count, source_doc
    from admission
    where province = #{province}
      and year = (select max(year) from admission where province = #{province})
      and min_score is not null
      and min_score between #{score} - #{range} and #{score} + #{range}
    <if test="majorKeyword != null and majorKeyword != ''">
        and major like concat('%', #{majorKeyword}, '%')
    </if>
    order by min_score desc
    limit 50
    </script>
    """)
    List<Admission> recommendByScore(@Param("province") String province,
                                     @Param("score") Integer score,
                                     @Param("range") Integer range,
                                     @Param("majorKeyword") String majorKeyword);
}
