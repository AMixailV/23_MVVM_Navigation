package ru.mixail_akulov.a23_mvvm_navigation.screens.base

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import ru.mixail_akulov.a23_mvvm_navigation.navigator.ARG_SCREEN
import ru.mixail_akulov.a23_mvvm_navigation.navigator.MainNavigator
import ru.mixail_akulov.a23_mvvm_navigation.navigator.Navigator

/**
 * Эта фабрика моделей просмотра позволяет создавать модели просмотра, которые имеют конструктор с 2
 * аргументами: [Navigator] и некоторый подкласс [BaseScreen].
 * Для создания вьюмодели надо передать скрин и навигатор.
 * Навигатор можно получить из активити, значит надо передать ссылку на активити или на фрагмент,
 * т.к. из фрагмента можно тоже получить ссылку на активити
 */
class ViewModelFactory(
    private val screen: BaseScreen,
    private val fragment: BaseFragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity() // получаем активити
        val application = hostActivity.application // получаем application
        // создаем провайдер, с помощью которого мы можем получить доступ к навигатору
        val navigatorProvider = ViewModelProvider(hostActivity, AndroidViewModelFactory(application))
        val navigator = navigatorProvider[MainNavigator::class.java] // получаем навигатор

        // если вам нужно создать модель представления с некоторыми другими аргументами -> вы можете
        // используйте поле "конструкторы" для поиска нужного конструктора
        // вытягиваем конструктор
        val constructor = modelClass.getConstructor(Navigator::class.java, screen::class.java)
        return constructor.newInstance(navigator, screen) // и возвращаем с нашими параметрами
    }
}

/**
 * Используйте этот метод для получения вью-моделей из ваших фрагментов.
 */
inline fun <reified VM : ViewModel> BaseFragment.screenViewModel() = viewModels<VM> {
    // получаем скрин из аргументов фрагмента, положенный в MainNavigator по ключу
    val screen = requireArguments().getSerializable(ARG_SCREEN) as BaseScreen
    ViewModelFactory(screen, this)
}