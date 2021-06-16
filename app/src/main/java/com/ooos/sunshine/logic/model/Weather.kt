package com.ooos.sunshine.logic.model

import com.ooos.sunshine.logic.model.RealtimeResponse.Realtime
import com.ooos.sunshine.logic.model.DailyResponse.Daily

data class Weather(val realtime: Realtime, val daily: Daily)