package com.salt.core.ext

enum class DateTimeCalendarFormat(val format: String) {
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
    YYYY_MM_DD_HH_MM_SS_SS("yyyy-MM-dd HH:mm:ss+SS"),
    DD_MMM_YYY___HH_MM("dd MMM yyy - HH:mm"),
    YYYY_MM_DD_T_HH_MM_SS_SSS_Z_("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
    YYYY_MM_DD_T_HH_MM_SS_Z_("yyyy-MM-dd'T'HH:mm:ss'Z'"),
    D_MMMM_YYYY("d MMMM yyyy"),
    DD_MMM_YYYY("dd MMM yyyy"),
}