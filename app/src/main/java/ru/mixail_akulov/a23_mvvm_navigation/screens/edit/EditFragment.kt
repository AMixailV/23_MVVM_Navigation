package ru.mixail_akulov.a23_mvvm_navigation.screens.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.mixail_akulov.a23_mvvm_navigation.databinding.FragmentEditBinding
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseFragment
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseScreen
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.screenViewModel

class EditFragment : BaseFragment() {

    // этот экран принимает строковое значение из HelloFragment
    class Screen(val initialValue: String) : BaseScreen

    // переопределяем viewModel из BaseFragment и нициализируем ее EditViewModel
    override val viewModel by screenViewModel<EditViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentEditBinding.inflate(inflater, container, false)

        // прослушивание начального значения, которое будет присвоено в valueEditText
        viewModel.initialMessageEvent.observe(viewLifecycleOwner) {
            it.getValue()?.let { message -> binding.valueEditText.setText(message) }
        }

        // прослушиватели кликов кнопок
        binding.saveButton.setOnClickListener {
            viewModel.onSavePressed(binding.valueEditText.text.toString())
        }
        binding.cancelButton.setOnClickListener {
            viewModel.onCancelPressed()
        }

        return binding.root
    }
}