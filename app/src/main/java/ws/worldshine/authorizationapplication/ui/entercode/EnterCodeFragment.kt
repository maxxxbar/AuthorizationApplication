package ws.worldshine.authorizationapplication.ui.entercode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ws.worldshine.authorizationapplication.R
import ws.worldshine.authorizationapplication.databinding.EnterCodeFragmentBinding
import ws.worldshine.authorizationapplication.utils.hideKeyboard
import ws.worldshine.authorizationapplication.utils.setKeyboardEventListener

class EnterCodeFragment : Fragment() {

    private val viewModel: EnterCodeFragmentViewModel by viewModels()
    private var _binding: EnterCodeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var editTextOne: EditText
    private lateinit var editTextTwo: EditText
    private lateinit var editTextThree: EditText
    private lateinit var editTextFour: EditText
    private lateinit var numberText: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EnterCodeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_background)
        initViews()
        binding.root.setOnClickListener { hideKeyboard() }
        initEditText()
        setTimer()
        setNumber()
        requireActivity().setKeyboardEventListener(viewLifecycleOwner, this::switchBackground)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViews() {
        editTextOne = binding.editTextOne
        editTextTwo = binding.editTextTwo
        editTextThree = binding.editTextThree
        editTextFour = binding.editTextFour
        numberText = binding.textViewSmsDescription
    }

    private fun setNumber() {
        val number = arguments?.getString("phone")
        val numberDescriptionText = getString(R.string.textView_sms_description)
        numberText.text = String.format(numberDescriptionText, number)
    }

    private fun initEditText() {
        editTextOne.addTextChangedListener {
            it?.toString()?.let { s ->
                if (s.isNotEmpty()) {
                    editTextTwo.requestFocus()
                    editTextTwo.isCursorVisible = true
                }
            }
        }

        editTextTwo.addTextChangedListener() {
            it?.toString()?.let { s ->
                if (s.isNotEmpty()) {
                    editTextThree.requestFocus()
                    editTextThree.isCursorVisible = true
                } else {
                    editTextOne.requestFocus()
                    editTextOne.isCursorVisible = true
                }
            }
        }

        editTextThree.addTextChangedListener() {
            it?.toString()?.let { s ->
                if (s.isNotEmpty()) {
                    editTextFour.requestFocus()
                    editTextFour.isCursorVisible = true
                } else {
                    editTextTwo.requestFocus()
                    editTextTwo.isCursorVisible = true
                }
            }
        }

        editTextFour.addTextChangedListener() {
            it?.toString()?.let { s ->
                if (s.isEmpty()) {
                    editTextThree.requestFocus()
                    editTextThree.isCursorVisible = true
                }
            }
        }

    }

    private fun switchBackground(value: Boolean) {
        if (value) {
            view?.background =
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_background_without_bottom)
        } else {
            view?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_background)
        }
    }

    private fun setTimer() {
        val text = getString(R.string.textView_new_code_description)
        viewModel.timer(text) { value -> binding.textViewNewCodeDescription.text = value }
    }

}