package com.salt.core.base

data class BaseState<T>(
    val isLoading: Boolean = false,
    val list: List<T> = emptyList(),
    val error: String = "",
)
