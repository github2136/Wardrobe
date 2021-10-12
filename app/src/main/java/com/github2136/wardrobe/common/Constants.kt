package com.github2136.wardrobe.common

/**
 * Created by YB on 2019/9/26
 */
object Constants {
    ///////////////////////////////////////////////////////////////////////////
    // SP
    ///////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////
    // 杂项
    ///////////////////////////////////////////////////////////////////////////
    //数据库文件名
    const val SQLITE_NAME = "Wardrobe.db"

    //时间分组格式化
    const val DATE_PATTERN_DATE = "yyyy-MM-dd HH:mm:ss"
    const val DATE_PATTERN_YM = "yyyy-MM"
    const val DATE_PATTERN_YYYY = "yyyy"
    const val DATE_PATTERN_MDNHM = "MM-dd\nHH:mm"
    const val DATE_PATTERN_MDHMS = "MM-dd HH:mm:ss"
    const val DATE_PATTERN_YMDHM = "yyyy-MM-dd HH:mm"
    const val DATE_PATTERN_YMD = "yyyyMMdd"
    const val DATE_PATTERN_HMS = "HH:mm:ss"
    const val DATE_PATTERN_HM = "HH:mm"

    //文件目录
    const val DIR_VIDEO = "Video"//视频
    const val DIR_VOICE = "Voice"//语音
    const val DIR_IMAGE = "Image"//图片
    const val DIR_DOWNLOAD = "Download"//通用下载地址

    //设置高度高比为16:9
    const val SIZE_16_9 = 0.5625

    //设置高度高比为4:3
    const val SIZE_4_3 = 0.75

    //log日志友盟
    const val TAG_UMENG = "UMENG"
}