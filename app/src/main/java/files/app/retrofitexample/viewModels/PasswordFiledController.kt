package files.app.retrofitexample.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import files.app.retrofitexample.R

class PasswordFiledController {
    var password = mutableStateOf("")
    private var show by mutableStateOf(false)
    fun flipShow() {
        show = !show
    }

    fun getVisibleImage(): Int {
        return if (show) R.drawable.visibility else R.drawable.visibility_off
    }

    fun getVisualState(): VisualTransformation {
        return if (show) VisualTransformation.None
        else VisualTransformation {
            TransformedText(
                text = AnnotatedString("*".repeat(it.length)),
                offsetMapping = OffsetMapping.Identity
            )
        }
    }
}