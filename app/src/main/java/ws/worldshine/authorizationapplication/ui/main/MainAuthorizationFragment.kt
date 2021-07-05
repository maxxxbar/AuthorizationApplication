package ws.worldshine.authorizationapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ws.worldshine.authorizationapplication.R

class MainAuthorizationFragment : Fragment() {

    private val viewModel: MainAuthorizationFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_authorization_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_registration).setOnClickListener {
            findNavController().navigate(R.id.action_mainAuthorizationFragment_to_authorizationFragment)
        }
    }

}