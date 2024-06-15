package ru.mixail_akulov.a23_mvvm_navigation.screens.base

import androidx.fragment.app.Fragment

/**
 * Базовый класс для всех фрагментов
 */
abstract class BaseFragment : Fragment() {

    /**
     * View-model, который управляет этим фрагментом
     */
    abstract val viewModel: BaseViewModel

}