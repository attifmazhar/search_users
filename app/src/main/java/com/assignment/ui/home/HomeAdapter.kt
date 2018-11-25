package com.assignment.ui.home

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.assignment.ui.util.DataBindingViewHolder
import com.assignment.BR.item
import com.assignment.data.model.User
import com.assignment.databinding.UserItemRowBinding

class HomeAdapter(
    private var items: MutableList<User> = arrayListOf<User>()
) : RecyclerView.Adapter<HomeAdapter.SimpleHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
//        val binding  = FoodItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val binding  = UserItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SimpleHolder(binding)
    }

    inner class SimpleHolder(dataBinding: ViewDataBinding)
        : DataBindingViewHolder<User>(dataBinding)  {
        override fun onBind(t: User): Unit = with(t) {
            dataBinding.setVariable(item, t)
        }
    }

    fun add(list: MutableList<User>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}