package ru.sshex.exchange.ui.fragment.currencies.adapter

import android.content.Context
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_currency.view.*
import ru.sshex.exchange.R
import ru.sshex.exchange.presentation.model.CurrencyItem
import java.math.BigDecimal
import java.text.DecimalFormat

class CurrencyItemViewHolder(
    parent: ViewGroup,
    val valueChangedListener: (CurrencyItem) -> Unit,
    val clickListener: (CurrencyItem, Int) -> Unit
) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)) {
    private var context: Context = parent.context
    private var textWatcher: TextWatcher? = null

    fun bind(currencyItem: CurrencyItem) {
        itemView.currencyShortNameTextView.text = currencyItem.code
        itemView.currencyFullNameTextView.text = context.getString(currencyItem.fullName)
        val formattedVal = DecimalFormat("#0.##").format(currencyItem.value)
        itemView.amountEditText.setText(formattedVal)
        itemView.amountEditText.setSelection(formattedVal.length, formattedVal.length)

        itemView.setOnClickListener {
            if (layoutPosition != 0) {
                clickListener(currencyItem, layoutPosition)
            }
        }
        Glide.with(context)
            .load(currencyItem.flagIconUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(itemView.logoImageView)

        if (layoutPosition == 0) {
            textWatcher = itemView.amountEditText.doAfterTextChanged {
                if (layoutPosition == 0) {
                    val value = it.toString()
                    valueChangedListener(currencyItem.copy(value = if (value.isNotBlank()) value.toBigDecimal() else BigDecimal.ZERO))
                }
            }
        } else {
            textWatcher?.run {
                itemView.amountEditText.removeTextChangedListener(this)
            }
        }
    }

    fun enableInput(enable: Boolean) {
        itemView.amountEditText.isEnabled = enable
    }

    fun bind(newValue: BigDecimal) = itemView.amountEditText.setText(DecimalFormat("#0.##").format(newValue))
}