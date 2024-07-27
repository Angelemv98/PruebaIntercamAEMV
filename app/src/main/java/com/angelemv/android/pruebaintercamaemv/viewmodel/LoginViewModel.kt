import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelemv.android.pruebaintercamaemv.models.data.AppDatabase
import com.angelemv.android.pruebaintercamaemv.models.data.Usuario
import com.angelemv.android.pruebaintercamaemv.models.interfaces.UsuarioDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val usuarioDao: UsuarioDao) : ViewModel() {

    private var _user: String? = null
    private var _password: String? = null

    fun setUser(user: String) {
        _user = user
    }

    fun setPassword(password: String) {
        _password = password
    }

    fun createDefaultUserIfNotExists() {
        viewModelScope.launch(Dispatchers.IO) {
            val defaultUser = Usuario(username = "Admin", password = encriptToBase64("admin123"))
            val existingUser = usuarioDao.getUsuario(defaultUser.username, defaultUser.password)

            if (existingUser == null) usuarioDao.insert(defaultUser)

        }
    }

    fun validateUser(onResult: (Boolean) -> Unit) {
        if (!(_user.isNullOrEmpty() || _password.isNullOrEmpty())) {
            CoroutineScope(Dispatchers.IO).launch {
                val encryptedPassword = encriptToBase64(_password!!)
                val usuario = usuarioDao.getUsuario(_user!!, encryptedPassword)

                withContext(Dispatchers.Main) {
                    onResult(usuario != null && usuario.username == "Admin")
                }
            }
        } else {
            onResult(false)
        }
    }

    private fun encriptToBase64(textToEncrypt: String): String {
        val data = textToEncrypt.toByteArray(Charsets.UTF_8)
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }
}