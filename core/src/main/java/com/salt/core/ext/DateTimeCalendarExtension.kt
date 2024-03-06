package com.salt.core.ext

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun String.getMillisecond(
    inputFormat: String? = "yyyy-MM-dd'T'HH:mm:ss",
): Long {
    val inputDateFormatter = SimpleDateFormat(inputFormat, Locale.getDefault()).apply {
//        timeZone = TimeZone.getTimeZone("$UTC")
    }
    return try {
        val parsedDate = inputDateFormatter.parse(this)
        return parsedDate?.time ?: 0
    } catch (e: ParseException) {
        e.printStackTrace()
        0
    }
}

fun Date.getDiffMillNowToTarget(hour: Int = 0, minute: Int = 0, second: Int = 0): Long {
    val cal = Calendar.getInstance()
    cal.time = this
    if (hour != 0) cal.set(Calendar.HOUR_OF_DAY, hour)
    if (minute != 0) cal.set(Calendar.MINUTE, minute)
    if (second != 0) cal.set(Calendar.SECOND, second)
    return cal.timeInMillis - Date().time
}

fun Date.setCountDownTimerToNow(textView: TextView? = null, title: String = "", separator: String = ", ", withSecond: Boolean = true, isIndo: Boolean = false, onFinish: () -> Unit = {}): CountDownTimer {
    return getDiffMillNowToTarget().setCountDownTimer(textView, title, separator, withSecond, isIndo, onFinish)
}

fun Date.getDiffMillisNowToTarget(): Long {
    return Date().time - this.time
}

fun Date.setCountDownTimerNowToTarget(textView: TextView? = null, title: String = "", separator: String = ", ", withSecond: Boolean = true, isIndo: Boolean = false, onFinish: () -> Unit = {}): CountDownTimer {
    val diffMillisNowToTarget = getDiffMillisNowToTarget()
    val countDownTimer = diffMillisNowToTarget.setCountDownTimer(textView, title, separator, withSecond, isIndo, onFinish)
    return countDownTimer
}

/**
 * set start and cancel CountDownTimer after get this*/
fun Long.setCountDownTimer(textView: TextView? = null, title: String = "", separator: String = ", ", withSecond: Boolean = true, isIndo: Boolean = false, onFinish: () -> Unit = {}): CountDownTimer {
    return object : CountDownTimer(this, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            /*var seconds = (millisUntilFinished / 1000).toInt()
            val hours = seconds / (60 * 60)
            val tempMint = seconds - hours * 60 * 60
            val minutes = tempMint / 60
            seconds = tempMint - minutes * 60
            if (textView != null) textView.text = "" + "${checkHourMinuteOverTenDateTimepicker(hours)}:" + "${checkHourMinuteOverTenDateTimepicker(minutes)}:" + "${checkHourMinuteOverTenDateTimepicker(seconds)}"*/
            textView?.text = millisUntilFinished.stringCountDownTimer2(title, isIndo, separator, withSecond)
        }

        override fun onFinish() {
            onFinish()
        }
    }
}

private fun Long.stringCountDownTimer2(title: String = "", isIndo: Boolean = false, separator: String = ", ", withSecond: Boolean = true, hideDayIfZero: Boolean = true): String {
    val days = this / (24 * 60 * 60 * 1000)
    val hours = this / (1000 * 60 * 60) % 24
    val minutes = this / (1000 * 60) % 60
    val seconds = (this / 1000) % 60

    // Display Countdown
    return if (!isIndo) {
        if (title.isNotEmpty()) "$title ${if (days != 0L || !hideDayIfZero) "$days day${separator}" else ""}$hours hour${separator}$minutes min${if (withSecond) "${separator}$seconds sec" else ""}"
        else "${if (days != 0L || !hideDayIfZero) "$days day${separator}" else ""}$hours hour${separator}$minutes min$${if (withSecond) "${separator}$seconds sec" else ""}"
    } else {
        if (title.isNotEmpty()) "$title ${if (days != 0L || !hideDayIfZero) "$days hari${separator}" else ""}$hours jam${separator}$minutes menit${if (withSecond) "${separator}$seconds detik" else ""}"
        else "${if (days != 0L || !hideDayIfZero) "$days hari${separator}" else ""}$hours jam${separator}$minutes menit${if (withSecond) "${separator}$seconds detik" else ""}"
    }
}

fun Date.toStringFormat(format: String, locale: Locale? = null, timezone: TimeZone? = null): String {
    return try {
        val sdf: SimpleDateFormat = if (locale != null) SimpleDateFormat(format, Locale.getDefault())
        else SimpleDateFormat(format, Locale.getDefault())
        if (timezone != null) {
            sdf.timeZone = timezone
        }
        sdf.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun String.toDateFormat(format: String, locale: Locale? = null, timezone: TimeZone? = null): Date? {
    return try {
        val sdf: SimpleDateFormat = if (locale != null) SimpleDateFormat(format, Locale.getDefault())
        else SimpleDateFormat(format, Locale.getDefault())
        if (timezone != null) {
            sdf.timeZone = timezone
        }
        sdf.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

/**
 * @return string or empty*/
fun String.toNewFormat(currentFormat: String, newFormat: String): String {
    return try {
        val currentDateFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
        val newDateFormat = SimpleDateFormat(newFormat, Locale.getDefault())
        val currentDate = currentDateFormat.parse(this)
        newDateFormat.format(currentDate)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

/**
 * @return date or null*/
fun Date.toNewFormat(currentFormat: String, newFormat: String): Date? {
    return try {
        val currentDateFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
        val newDateFormat = SimpleDateFormat(newFormat, Locale.getDefault())
        val newDateString = currentDateFormat.format(this)
        newDateFormat.parse(newDateString)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun Date.sameMonthYear(compareDate: Date): Boolean {
    return try {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = this
        cal2.time = compareDate
        cal1[Calendar.MONTH] == cal2[Calendar.MONTH] && cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
    } catch (e: Exception) {
        false
    }
}

fun Date.sameDayThisMonth(compareDate: Date, date: Int): Boolean {
    return try {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = this
        cal2.time = compareDate
        cal1[Calendar.DAY_OF_MONTH] == date && cal1[Calendar.MONTH] == cal2[Calendar.MONTH] && cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
    } catch (e: Exception) {
        false
    }
}

fun Date.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Date.getLastDateSelectMonth(selectMonth: Int? = null, selectYear: Int? = null): Date {
    val calendar = toCalendar()
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE))
    selectMonth?.let { calendar.set(Calendar.MONTH, it) }
    selectYear?.let { calendar.set(Calendar.YEAR, it) }
    return calendar.time
}

/**
 * @param this@addYear
 * @param year minus number would decrement the months
 * @return
 */
fun Date.addYear(year: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.YEAR, year) //minus number would decrement the days
    return cal.time
}

/**
 * @param this@addMonth
 * @param month minus number would decrement the months
 * @return
 */
fun Date.addMonth(month: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.MONTH, month) //minus number would decrement the days
    return cal.time
}

/**
 * @param this@addWeek
 * @param week minus number would decrement the days
 * @return
 */
fun Date.addWeek(week: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.WEEK_OF_MONTH, week) //minus number would decrement the days
    return cal.time
}

/**
 * @param this@addDays
 * @param days minus number would decrement the days
 * @return
 */
fun Date.addDays(days: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DATE, days) //minus number would decrement the days
    return cal.time
}

fun Date?.isSameDay(date: Date?): Boolean {
    return try {
        if (this != null && date != null) {
            val cal1 = Calendar.getInstance()
            cal1.time = this
            val cal2 = Calendar.getInstance()
            cal2.time = date
            cal1.isSameDay(cal2)
        } else {
            false
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        false
    }
}

fun Calendar?.isSameDay(cal: Calendar?): Boolean {
    return try {
        if (this != null && cal != null) {
            get(Calendar.ERA) == cal.get(Calendar.ERA) &&
                    get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                    get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
        } else {
            return false
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        return false
    }
}

fun Date.isToday(): Boolean = isSameDay(Calendar.getInstance().time)

fun Date.getElapsedDaysAndHour(): String {
    var millisUntilFinished: Long = time
    val elapsedDays = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
    millisUntilFinished -= TimeUnit.DAYS.toMillis(elapsedDays)
    val elapsedHours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
    millisUntilFinished -= TimeUnit.HOURS.toMillis(elapsedHours)
    return "$elapsedDays Hari $elapsedHours Jam"
}

fun Calendar.isToday(): Boolean = isSameDay(Calendar.getInstance())

fun Int.makeDateDay() = if (this > 0) {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, this)
    Date(calendar.timeInMillis)
} else {
    Date()
}