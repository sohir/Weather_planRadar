package com.idbs.weather.ui.cites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.idbs.weather.R
import com.idbs.weather.databinding.CityListItemBinding


class CitesListAdapter (private val actionsInterface:ComponentActionsClickListener ?=null) : RecyclerView.Adapter<CitesListAdapter.ViewHolder>() {
    val Diff_CallBack = object : DiffUtil.ItemCallback<CitiesModel.Cities> (){
        override fun areItemsTheSame(oldItem: CitiesModel.Cities, newItem: CitiesModel.Cities): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: CitiesModel.Cities, newItem: CitiesModel.Cities): Boolean {
            return oldItem==newItem
        }

    }
    private val differ = AsyncListDiffer(this,Diff_CallBack)
   fun updateDate(newList:List<CitiesModel.Cities>){
       differ.submitList(newList)
   }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = DataBindingUtil.inflate<CityListItemBinding>(inflater,
            R.layout.city_list_item, parent,false)
        return ViewHolder(rootView,actionsInterface)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.city = differ.currentList[position]
        holder.bind(differ.currentList[position])
    }

    class ViewHolder (var view:CityListItemBinding,private val itemActions: ComponentActionsClickListener?):
        RecyclerView.ViewHolder(view.root){
        fun bind(item: CitiesModel.Cities){
            view.city = item
            view.listener = itemActions
            view.executePendingBindings()
        }
    }



    interface ComponentActionsClickListener {
        fun onItemClicked(v: View,item: CitiesModel.Cities)
        fun onInfoClicked(v: View,item: CitiesModel.Cities)
    }
}