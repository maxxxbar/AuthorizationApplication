package ws.worldshine.authorizationapplication.ui.authorization

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

class AuthorizationFragmentViewModel : ViewModel() {
    private val disabledButton = MutableStateFlow(false)
    private val phoneNumber = MutableStateFlow("")

    suspend fun setDisabledButton(value: Boolean) = disabledButton.emit(value)
    suspend fun getDisabledButton(f: (value: Boolean) -> Unit) = disabledButton.collect { value -> f(value) }

    suspend fun setPhoneNumber(number: String) = phoneNumber.emit(number)
    fun getPhoneNumber() = phoneNumber.value
}