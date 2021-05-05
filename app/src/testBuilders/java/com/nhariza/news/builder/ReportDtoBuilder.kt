package com.nhariza.news.builder

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhariza.news.repository.datasource.model.ReportDto

object ReportDtoBuilder {

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
            "      },\n" +
            "      {\n" +
            "         author: \"gAlpha.com\",\n" +
            "         category: [\n" +
            "            \"business\",\n" +
            "            \"finance\"\n" +
            "         ],\n" +
            "         description: \"Blank\",\n" +
            "         id: \"bb4303d4-60b6-4453-a4da-0c8abf8aeabb\",\n" +
            "         image: \"None\",\n" +
            "         language: \"en\",\n" +
            "         published: \"2020-04-25 07:11:53 +0000\",\n" +
            "         title: \"New International's P/B Ratio Is Definitely Out Of Balance\",\n" +
            "         url: \"https://seekingalpha.com/article/4340109-old-republic-internationals-p-b-ratio-is-definitely-out-of-balance?source=feed_sector_financial\"\n" +
            "      }]"

    fun build(): ReportDto {
        val typeReportDto = object : TypeToken<ReportDto>() {}.type
        return Gson().fromJson(JSON_RESPONSE_REPORT, typeReportDto)
    }

    fun buildDtoList(): List<ReportDto> {
        val typeReportDto = object : TypeToken<ArrayList<ReportDto>>() {}.type
        return Gson().fromJson(JSON_RESPONSE_NEWS, typeReportDto)
    }
}