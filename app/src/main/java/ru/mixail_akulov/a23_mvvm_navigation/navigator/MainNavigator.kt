package ru.mixail_akulov.a23_mvvm_navigation.navigator

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.mixail_akulov.a23_mvvm_navigation.Event
import ru.mixail_akulov.a23_mvvm_navigation.MainActivity
import ru.mixail_akulov.a23_mvvm_navigation.R
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseScreen

const val ARG_SCREEN = "SCREEN"

/**
 * Реализация навигатора. Он расширяет [AndroidViewModel] потому что
 * 1) нам нужна зависимость от Android (контекст приложения);
 * 2) он должен сохраняться после поворота экрана.
 * [AndroidViewModel] - это особый класс для данных целей навигации.
 * [Application] и есть [Context]
 */
class MainNavigator(
    application: Application
) : AndroidViewModel(application), Navigator {

    // ссылка на действия
    val whenActivityActive = MainActivityActions()

    // для передачи результата в методе goBack()
    private val _result = MutableLiveData<Event<Any>>()
    val result: LiveData<Event<Any>> = _result

    // для реализации launch() и goBack() мы не можем ссылаться на активити. т.к. находимся во вьюмодели.
    // поэтому нам нужен класс, который позволит косвенным образом обращаться к активити MainActivityAction
    override fun launch(screen: BaseScreen) = whenActivityActive {
        // делаем через метод, чтобы первый экран из сложенных в стек не удалялся при возврате по бекстеку
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(getApplication(), messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int): String {
        return getApplication<Application>().getString(messageRes)
    }

    // т.к. все скрины находятся в фрагментах, то можно получить сам фрагмент с помощью enclosingClass
    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        // скрин необходимо присоединить к фрагменту, сделаем это через бандл, т.к. скрин наследуется от сериазейбл
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}