package ws.worldshine.authorizationapplication.ui.authorization

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ws.worldshine.authorizationapplication.R

class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.authorization_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val singInBtn = view.findViewById<Button>(R.id.btn_sing_in)
        val t = SpannableStringBuilder()
        t.append("Зарегестрирован? ")
            .bold { append("Войти") }
        singInBtn.text = t
    }


}