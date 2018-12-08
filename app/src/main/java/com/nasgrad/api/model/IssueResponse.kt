package com.nasgrad.api.model

import android.support.annotation.Keep

@Keep
data class IssueResponse(val issues: List<Issue>)