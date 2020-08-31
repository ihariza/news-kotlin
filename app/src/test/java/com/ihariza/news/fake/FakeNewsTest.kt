package com.ihariza.news.fake

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ihariza.news.data.api.dto.ReportDto
import com.ihariza.news.data.api.dto.toEntity
import com.ihariza.news.data.entity.toBo
import com.ihariza.news.domain.model.ReportBo
import com.ihariza.news.presentation.model.Report

object FakeNewsTest {

    const val REPORT_ID = "1"

    const val PAGE_ONE = 1
    const val PAGE_TWO = 2

    private const val JSON_RESPONSE_REPORT = "{\n" +
            "         author: \"straitstimes\",\n" +
            "         category: [\n" +
            "            \"lifestyle\"\n" +
            "         ],\n" +
            "         description: \"LOS ANGELES - Nerdy, dark-skinned Indian girls do not usually get to be the heroine in Hollywood romantic comedies.. Read more at straitstimes.com....\",\n" +
            "         id: \"43ae9d5a-fac0-4f3e-8dbf-93b4781d1a16\",\n" +
            "         image: \"https://www.straitstimes.com/sites/default/files/styles/x_large/public/articles/2020/04/27/ym-nhie-270420.jpg?itok=vaqSUEBr\",\n" +
            "         language: \"en\",\n" +
            "         published: \"2020-04-20 07:11:40 +0000\",\n" +
            "         title: \"Mindy Kaling's Netflix romcom Never Have I Ever puts a Tamil Indian family front and centre\",\n" +
            "         url: \"http://www.straitstimes.com/lifestyle/entertainment/mindy-kalings-netflix-romcom-never-have-i-ever-puts-a-tamil-indian-family\"\n" +
            "      }"

    private const val JSON_RESPONSE_NEWS = "[{\n" +
            "         author: \"nikkei\",\n" +
            "         category: [\n" +
            "            \"economy\"\n" +
            "         ],\n" +
            "         description: \"BEIJING (Reuters) -- Profits at China's industrial firms fell in March although at a slower pace than in the first two months, with many sectors seeing significant declines, suggesting the economy is ...\",\n" +
            "         id: \"d6f5f18b-c3d9-4803-be0d-01ed3908eada\",\n" +
            "         image: \"None\",\n" +
            "         language: \"en\",\n" +
            "         published: \"2020-04-24 10:12:10 +0000\",\n" +
            "         title: \"China's industrial profits contract in March but at slower pace\",\n" +
            "         url: \"https://asia.nikkei.com/Economy/China-s-industrial-profits-contract-in-March-but-at-slower-pace\"\n" +
            "      },\n" +
            "      {\n" +
            "         author: \"SeekingAlpha.com\",\n" +
            "         category: [\n" +
            "            \"business\",\n" +
            "            \"finance\"\n" +
            "         ],\n" +
            "         description: \"Blank\",\n" +
            "         id: \"ac4303d4-60b6-4453-a4da-0c8abf8aeafa\",\n" +
            "         image: \"None\",\n" +
            "         language: \"en\",\n" +
            "         published: \"2020-04-24 07:11:53 +0000\",\n" +
            "         title: \"Old Republic International's P/B Ratio Is Definitely Out Of Balance\",\n" +
            "         url: \"https://seekingalpha.com/article/4340109-old-republic-internationals-p-b-ratio-is-definitely-out-of-balance?source=feed_sector_financial\"\n" +
            "      }]"


    fun getReportDto(): ReportDto {
        val typeReportDto = object : TypeToken<ReportDto>() {}.type
        return Gson().fromJson(JSON_RESPONSE_REPORT, typeReportDto)
    }

    fun getNewsDto(): List<ReportDto> {
        val typeReportDto = object : TypeToken<ArrayList<ReportDto>>() {}.type
        return Gson().fromJson(JSON_RESPONSE_NEWS, typeReportDto)
    }

    fun getReportBo() = getReportDto().toEntity().toBo()

    fun getNewsBo(): List<ReportBo> = getNewsDto().toEntity().toBo()

    fun getReportVm(): Report {
        val reportDto = getReportDto()
        return Report(
                reportDto.id,
                reportDto.title,
                reportDto.description,
                reportDto.url,
                reportDto.author,
                reportDto.image,
                "5 hour ago"
        )
    }

}