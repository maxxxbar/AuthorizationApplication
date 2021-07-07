package ws.worldshine.authorizationapplication.ui.authorization

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.core.text.bold
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.coroutines.launch
import ws.worldshine.authorizationapplication.R
import ws.worldshine.authorizationapplication.databinding.AuthorizationFragmentBinding
import ws.worldshine.authorizationapplication.utils.hideKeyboard
import ws.worldshine.authorizationapplication.utils.makeLinks
import ws.worldshine.authorizationapplication.utils.setKeyboardEventListener

class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationFragmentViewModel by viewModels()
    private var _binding: AuthorizationFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var singInBtn: Button
    private lateinit var getCodeBtn: Button
    private lateinit var editText: EditText
    private lateinit var termOfUseTitle: TextView
    private lateinit var spinner: Spinner
    private val valueListener = object : MaskedTextChangedListener.ValueListener {
        override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
            lifecycleScope.launch {
                viewModel.setDisabledButton(maskFilled)
                if (maskFilled) {
                    val number = "${spinner.selectedItem} $formattedValue"
                    viewModel.setPhoneNumber(number)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AuthorizationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_background)
        initViews()
        setSingInButton()
        initCodeButton()
        openKeyboardHandler()
        view.setOnClickListener {
            hideKeyboard()
            view.findFocus()?.clearFocus()
        }
        val listener =
            MaskedTextChangedListener.installOn(editText, getString(R.string.russian_phone_template), valueListener)
        editText.hint = listener.placeholder()
        termOfUseTitle.makeLinks(
            createLink(getString(R.string.term_of_use)),
            createLink(getString(R.string.privacy_policy))
        )
    }

    private fun openKeyboardHandler() {
        requireActivity().setKeyboardEventListener(viewLifecycleOwner) { isVisible ->
            termOfUseTitle.isVisible = !isVisible
            switchBackground(isVisible)
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

    private fun initCodeButton() {
        disableCodeButton()
        getCodeBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_authorizationFragment_to_enterCodeFragment,
                bundleOf("phone" to viewModel.getPhoneNumber())
            )
        }
    }

    private fun setSingInButton() {
        val t = SpannableStringBuilder().append("Зарегестрирован? ")
            .bold { append("Войти") }
        singInBtn.text = t
    }

    private fun disableCodeButton() {
        lifecycleScope.launch {
            viewModel.getDisabledButton { value ->
                getCodeBtn.isEnabled = value
            }
        }
    }

    private fun initViews() {
        singInBtn = binding.btnSingIn
        getCodeBtn = binding.btnGetCode
        editText = binding.editTextNumber
        termOfUseTitle = binding.textViewTermsOfUse
        spinner = binding.spinnerNumberPrefix
    }

    private fun createLink(text: String) = Pair(text, View.OnClickListener {
        findNavController().navigate(R.id.action_authorizationFragment_to_termsOfUseFragment)
    })

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        editText.hint = null
    }

}