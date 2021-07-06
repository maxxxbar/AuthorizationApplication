package ws.worldshine.authorizationapplication.ui.entercode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EnterCodeFragmentViewModel : ViewModel() {

    fun timer(text: String, f: (value: String) -> Unit) {
        viewModelScope.launch {
            var time = 60
            while (time >= 0) {
                val s = String.format(text, "$time")
                f(s)
                delay(1000)
                time--
            }
        }
    }
}