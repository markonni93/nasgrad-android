package com.nasgrad.api.model

import android.support.annotation.Keep

@Keep
data class CityCervice(val region: String, val name: String, val description: String, val email: String, val id: String)