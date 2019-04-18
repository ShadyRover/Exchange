package ru.sshex.exchange.ui.fragment.currencies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.sshex.exchange.presentation.model.CurrencyItem

class CurrenciesAdapter(
    private val valueChangeListener: (CurrencyItem) -> Unit,
    val clickListener: (CurrencyItem, Int) -> Unit
) :
    RecyclerView.Adapter<CurrencyItemViewHolder>() {

    private val currencyList = mutableListOf<CurrencyItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        return CurrencyItemViewHolder(
            parent,
            valueChangedListener = { value -> valueChangeListener(value) },
            clickListener = ::onItemClicked
        )
    }

    override fun getItemCount(): Int = currencyList.size

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        holder.enableInput(position == 0)
        holder.bind(currencyList[position])
    }

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int, payloads: MutableList<Any>) {
        if (!payloads.isEmpty()) {
            holder.bind(currencyList[position].value)
            holder.enableInput(position == 0)
        } else super.onBindViewHolder(holder, position, payloads)
    }

    fun updateAdapter(baseCurrencyItem: CurrencyItem, map: LinkedHashMap<String, CurrencyItem>) {
        if (itemCount == 0) {
            with(currencyList) {
                add(baseCurrencyItem)
                addAll(map.values)
            }
            notifyDataSetChanged()
        } else {
            val updateList = mutableListOf<CurrencyItem>().apply {
                add(currencyList[0])
                val subList = currencyList.subList(1, currencyList.size)
                val elementsExits = subList.filter {
                    map.keys.find { value -> value == it.code } != null
                }.map {
                    map[it.code]!!
                }.toMutableList()
                addAll(elementsExits)

                val notExists = subList.filter {
                    map.keys.find { value -> value == it.code } == null
                }.toMutableList()
                addAll(notExists)
            }
            val diffResult = DiffUtil.calculateDiff(CurrencyDiffCallback(this.currencyList, updateList))
            currencyList.clear()
            currencyList.addAll(updateList)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    private fun onItemClicked(item: CurrencyItem, position: Int) {
        currencyList.removeAt(position).also {
            currencyList.add(0, it)
        }
        clickListener(item, position)
        notifyItemMoved(position, 0)
        notifyItemChanged(0)
        notifyItemChanged(1)
    }

    internal class CurrencyDiffCallback(
        private val oldList: MutableList<CurrencyItem>,
        private val newList: MutableList<CurrencyItem>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].code == newList[newItemPosition].code

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            newList[newItemPosition].value == oldList[oldItemPosition].value

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) =
            DiffPayload(true)
    }

    internal data class DiffPayload(val needToUpdateValue: Boolean)
}