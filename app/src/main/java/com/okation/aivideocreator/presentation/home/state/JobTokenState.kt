package com.okation.aivideocreator.presentation.home.state

import com.okation.aivideocreator.data.remote.jobtokendb.JobTokenDB

data class JobTokenState(
    val isLoading : Boolean = false,
    val jobTokenDB: JobTokenDB? = null,
    val error : String = "",
)