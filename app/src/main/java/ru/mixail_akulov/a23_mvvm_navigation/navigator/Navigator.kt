package ru.mixail_akulov.a23_mvvm_navigation.navigator

import androidx.annotation.StringRes
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseScreen

interface Navigator {

    fun launch(screen: BaseScreen)

    fun goBack(result: Any? = null)

    fun toast(@StringRes messageRes: Int)

    fun getString(@StringRes messageRes: Int): String

}