package ru.mixail_akulov.a23_mvvm_navigation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import ru.mixail_akulov.a23_mvvm_navigation.navigator.MainNavigator
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseFragment
import ru.mixail_akulov.a23_mvvm_navigation.screens.hello.HelloFragment

class MainActivity : AppCompatActivity() {

    // т.к. навигатор это вьюмодель, то можно получить доступ к нему из майн активити.
    // Т.к. MainNavigator наследуется от AndroidViewModel, то надо передать фабрику
    private val navigator by viewModels<MainNavigator> { AndroidViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) { // Если запускаем в первый раз
            navigator.launchFragment(this, HelloFragment.Screen(), addToBackStack = false)
        }
        // регистрируем прослушивание в фрагменте
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    // отписываемся при удалении фрагмента
    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    // возврат по стрелке на предудущий экран
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // указываем навигатору, что активи стала доступной
    override fun onResume() {
        super.onResume()
        navigator.whenActivityActive.mainActivity = this
    }

    // указываем навигатору, что активити недоступна
    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.mainActivity = null
    }

    // прослушивание фрагментов
    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            if (supportFragmentManager.backStackEntryCount > 0) { // если есть фрагменты в backStack
                // более 1 экрана -> показать кнопку «Назад» на панели инструментов
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }

            val result = navigator.result.value?.getValue() ?: return // из LiveData вытягиваем результат
            if (f is BaseFragment) {
                // имеет результат, который может быть доставлен в view-model
                f.viewModel.onResult(result)
            }
        }
    }
}