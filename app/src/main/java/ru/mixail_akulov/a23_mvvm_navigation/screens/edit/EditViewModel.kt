package ru.mixail_akulov.a23_mvvm_navigation.screens.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.mixail_akulov.a23_mvvm_navigation.Event
import ru.mixail_akulov.a23_mvvm_navigation.R
import ru.mixail_akulov.a23_mvvm_navigation.navigator.Navigator
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseViewModel

class EditViewModel(
    private val navigator: Navigator,
    screen: EditFragment.Screen
) : BaseViewModel() {

    // передаем аргумент из модели в фрагмент один раз через Event<String>
    private val _initialMessageEvent = MutableLiveData<Event<String>>()
    val initialMessageEvent: LiveData<Event<String>> = _initialMessageEvent

    init {
        // отправка начального значения из аргумента экрана во фрагмент
        _initialMessageEvent.value = Event(screen.initialValue)
    }

    // кнопка сохранения текста
    fun onSavePressed(message: String) {
        if (message.isBlank()) { // запрещает пустое сообщение
            navigator.toast(R.string.empty_message)
            return
        }
        navigator.goBack(message)
    }

    // кнопка выхода с экрана
    fun onCancelPressed() {
        navigator.goBack()
    }
}