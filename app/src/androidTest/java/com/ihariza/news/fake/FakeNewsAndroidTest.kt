package com.ihariza.news.fake

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ihariza.news.data.api.dto.ReportDto
import com.ihariza.news.data.api.dto.toEntity
import com.ihariza.news.data.entity.ReportEntity

object FakeNewsAndroidTest {

    const val PAGE_ONE = 1

    private const val JSON_RESPONSE_REPORT = "{\n" +
            "         author: \"straitstimes\",\n" +
            "         category: [\n" +
            "            \"lifestyle\"\n" +
            "         ],\n" +
            "         description: \"LOS ANGELES - Nerdy, dark-skinned Indian girls do not usually get to be the heroine in Hollywood romantic comedies.. Read more at straitstimes.com....\",\n" +
            "         id: \"43ae9d5a-fac0-4f3e-8dbf-93b4781d1a16\",\n" +
            "         image: \"https://www.straitstimes.com/sites/default/files/styles/x_large/public/articles/2020/04/27/ym-nhie-270420.jpg?itok=vaqSUEBr\",\n" +
            "         language: \"en\",\n" +
            "         published: \"2020-04-27 07:11:40 +0000\",\n" +
            "         title: \"Mindy Kaling's Netflix romcom Never Have I Ever puts a Tamil Indian family front and centre\",\n" +
            "         url: \"http://www.straitstimes.com/lifestyle/entertainment/mindy-kalings-netflix-romcom-never-have-i-ever-puts-a-tamil-indian-family\"\n" +
            "      }"

    fun getReportEntity(): ReportEntity {
        val typeReportDto = object : TypeToken<ReportDto>() {}.type
        val reportDto: ReportDto =  Gson().fromJson(JSON_RESPONSE_REPORT, typeReportDto)
        return reportDto.toEntity().copy(page = PAGE_ONE)
    }

}