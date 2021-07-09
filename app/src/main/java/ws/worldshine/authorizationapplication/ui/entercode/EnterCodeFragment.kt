package ws.worldshine.authorizationapplication.ui.entercode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
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
        binding.root.setOnClickListener {
            view.findFocus()?.clearFocus()
            hideKeyboard()
        }
        setupEditText()
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

    private fun setupEditText() {
        addWatcher(editTextOne, editTextTwo)
        addWatcher(editTextTwo, editTextThree)
        addWatcher(editTextThree, editTextFour)
        addWatcher(editTextFour, null)
        addDelKeyListener(editTextOne, null)
        addDelKeyListener(editTextTwo, editTextOne)
        addDelKeyListener(editTextThree, editTextTwo)
        addDelKeyListener(editTextFour, editTextThree)
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

    private fun addDelKeyListener(editText: EditText, prevEditText: EditText?) {
        editText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (editText.text.isEmpty()) {
                    prevEditText?.requestFocus()
                    prevEditText?.text = null
                }
            }
            false
        }
    }

    private fun addWatcher(editText: EditText, editTextToFocus: EditText? = null) {
        var beforeText: CharSequence = ""
        val textWatched = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (editText.text.isNotEmpty()) editText.setSelection(editText.text.length)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length >= 2) {
                    val beforeChars = beforeText.toList()
                    val newChar = s.toList().firstOrNull {
                        !beforeChars.contains(it)
                    } ?: s.toString().substring(s.length - 1, s.length)
                    editText.setText(newChar.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {
                beforeText = s.toString()
                if (beforeText.isNotEmpty()) {
                    editText.setSelection(editText.text.length)
                    editTextToFocus?.requestFocus()
                }
            }
        }
        editText.addTextChangedListener(textWatched)
    }

}