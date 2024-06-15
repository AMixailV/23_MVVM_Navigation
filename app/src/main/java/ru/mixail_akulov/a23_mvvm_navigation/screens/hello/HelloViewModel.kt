package ru.mixail_akulov.a23_mvvm_navigation.screens.hello

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.mixail_akulov.a23_mvvm_navigation.R
import ru.mixail_akulov.a23_mvvm_navigation.navigator.Navigator
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseViewModel
import ru.mixail_akulov.a23_mvvm_navigation.screens.edit.EditFragment

class HelloViewModel(
    private val navigator: Navigator,
    screen: HelloFragment.Screen
) : BaseViewModel() {

    // Т.к. на экране есть TextView - некоторое сообщение,то создадим LiveData для его отслеживания
    private val _currentMessageLiveData = MutableLiveData<String>()
    val currentMessageLiveData: LiveData<String> = _currentMessageLiveData

    // начальное значение при первом запуске экрана
    init {
        _currentMessageLiveData.value = navigator.getString(R.string.hello_world)
    }

    // Мы должны слушать результат из этого экрана, т.к.после нажатия на экране кнопки save мы должны
    // надпись обновить
    override fun onResult(result: Any) {
        if (result is String) {                     // если результат типа стринг
            _currentMessageLiveData.value = result  // то обновляем текущую надпись через LiveData
        }
    }

    // вызывается, когда пользователь нажимает на кнопку редактировать. Он берет текущее значение из
    // LiveData и запускает экран с этим сообщением
    fun onEditPressed() {
        navigator.launch(EditFragment.Screen(initialValue = currentMessageLiveData.value!!))
    }
}