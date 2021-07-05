package ws.worldshine.authorizationapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ws.worldshine.authorizationapplication.R

class MainAuthorizationFragment : Fragment() {

    private val viewModel: MainAuthorizationFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_authorization_fragment, container, false)
    }

}