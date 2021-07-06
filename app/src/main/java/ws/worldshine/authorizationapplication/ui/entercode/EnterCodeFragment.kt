package ws.worldshine.authorizationapplication.ui.entercode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import ws.worldshine.authorizationapplication.R
import ws.worldshine.authorizationapplication.databinding.EnterCodeFragmentBinding
import ws.worldshine.authorizationapplication.utils.hideKeyboard

class EnterCodeFragment : Fragment() {

    private val viewModel: EnterCodeFragmentViewModel by viewModels()
    private var _binding: EnterCodeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var editTextOne: EditText
    private lateinit var editTextTwo: EditText
    private lateinit var editTextThree: EditText
    private lateinit var editTextFour: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EnterCodeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.background = AppCompatResources.getDrawable(requireContext(),R.drawable.ic_background)
        initViews()
        binding.root.setOnClickListener { hideKeyboard() }
        initEditText()
        setTimer()
        val number = arguments?.getString("phone")
        val numberDescriptionText = getString(R.string.textView_sms_description)
        binding.textViewSmsDescription.text = String.format(numberDescriptionText, number)
        KeyboardVisibilityEvent.setEventListener(
            requireActivity(),
            viewLifecycleOwner,
            {

                if (it) {
                    view.background =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_background_without_bottom)
                } else {
                    view.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_background)
                }

            })
    }

    private fun initViews() {
        editTextOne = binding.editTextOne
        editTextTwo = binding.editTextTwo
        editTextThree = binding.editTextThree
        editTextFour = binding.editTextFour
    }

    private fun initEditText() {
        editTextOne.background = null
        editTextTwo.background = null
        editTextThree.background = null
        editTextFour.background = null

        editTextOne.addTextChangedListener() {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setTimer() {
        val text = getString(R.string.textView_new_code_description)
        viewModel.timer(text) { value -> binding.textViewNewCodeDescription.text = value }
    }

}