package ru.mixail_akulov.a23_mvvm_navigation.navigator

import ru.mixail_akulov.a23_mvvm_navigation.MainActivity

typealias MainActivityAction = (MainActivity) -> Unit

/**
 * Этот класс выполняет действия на определенной активити только тогда, когда действие назначено
 * [mainActivity] field, т.е. когда эта активити доступна. Если активити не доступна, то действия
 * откладываются и будут выполняться, когда активити снова станет доступна.
 * Для определения таких действий создается typealias MainActivityAction.
 * См. логику настройки и пример использования в [MainNavigator] и [MainActivity]
 */
class MainActivityActions {

    /**
     * Назначить активность в [MainActivity.onResume] и назначить NULL в [MainActivity.onPause]
     * Ссылка на активити
     */
    var mainActivity: MainActivity? = null
        set(activity) {
            field = activity
            if (activity != null) {              // если активити не null
                actions.forEach { it(activity) } // то все действия в actions надо выполнить
                actions.clear()                  // очистка активти после выпонения действий
            }
        }

    // список действий, которые всегда будут выполняться с актуальной активити из-за сетера выше
    private val actions = mutableListOf<MainActivityAction>()

    /**
     * Оператор Invoke позволяет использовать этот класс следующим образом:
     *
     * ```
     * val runActionSafely = MainActivityActions()
     * fun doSomeActivityDependentLogic() = runActionSafely { activity ->
     *   // делать навигацию здесь
     * }
     * ```
     * Добавляем действия в actions. Для этого используем перегрузку операторов
     * в инвок передает action, который мы хотим сделать. Внутри определяем логику выполнения действий
     */
    operator fun invoke(action: MainActivityAction) {
        val activity = this.mainActivity
        if (activity == null) {  // если активити нет
            actions += action    // то в список действий добавляем действие
        } else {                 // иначе
            action(activity)     // выполняем действие, не добавляя его в очередь
        }
    }

    /**
     * Вызвать этот метод в навигаторе [MainNavigator.onCleared]
     */
    fun clear() {
        actions.clear()
    }

}