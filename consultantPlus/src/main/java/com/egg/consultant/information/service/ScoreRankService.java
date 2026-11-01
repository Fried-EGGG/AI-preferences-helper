package com.egg.consultant.information.service;

import com.egg.consultant.information.mapper.ScoreRankMapper;
import com.egg.consultant.information.pojo.ScoreRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-19:16
 * @Modified By:
 */
@Service
public class ScoreRankService
{
    @Autowired
    private ScoreRankMapper scoreRankMapper;

    public void insertScoreRank(ScoreRank scoreRank)
    {
        scoreRankMapper.insertScoreRank(scoreRank);
    }

    public List<ScoreRank> selectByProvince(String province)
    {
        return scoreRankMapper.selectByProvince(province);
    }

}
