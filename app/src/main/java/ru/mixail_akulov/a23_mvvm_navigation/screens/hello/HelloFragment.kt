package ru.mixail_akulov.a23_mvvm_navigation.screens.hello

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.mixail_akulov.a23_mvvm_navigation.databinding.FragmentHelloBinding
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseFragment
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.BaseScreen
import ru.mixail_akulov.a23_mvvm_navigation.screens.base.screenViewModel

class HelloFragment : BaseFragment() {

    class Screen : BaseScreen

    // переопределяем viewModel из BaseFragment и нициализируем ее HelloViewModel
    override val viewModel by screenViewModel<HelloViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentHelloBinding.inflate(inflater, container, false)

        // на кнопке вызываем соответствующий метод viewModel
        binding.editButton.setOnClickListener { viewModel.onEditPressed() }

        // слушатель текущего сообщения для вывода в TextView
        viewModel.currentMessageLiveData.observe(viewLifecycleOwner) {
            binding.valueTextView.text = it
        }

        return binding.root
    }
}