package ru.mixail_akulov.a23_mvvm_navigation.screens.base

import androidx.lifecycle.ViewModel

/**
 * Базовый класс для всех моделей просмотра.
 */
open class BaseViewModel : ViewModel() {

    /**
     * Переопределите этот метод в дочерних классах, если хотите прослушивать результаты
     * с других экранов
     */
    open fun onResult(result: Any) {

    }

}