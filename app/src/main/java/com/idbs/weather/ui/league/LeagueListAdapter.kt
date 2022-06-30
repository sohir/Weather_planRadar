package com.idbs.weather.ui.league

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.idbs.weather.R
import com.idbs.weather.databinding.LeagueListItemBinding


class LeagueListAdapter (private val actionsInterface:ComponentActionsClickListener ?=null) : RecyclerView.Adapter<LeagueListAdapter.ViewHolder>() {
    val Diff_CallBack = object : DiffUtil.ItemCallback<LeagueResponsModel.Competition> (){
        override fun areItemsTheSame(oldItem: LeagueResponsModel.Competition, newItem: LeagueResponsModel.Competition): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: LeagueResponsModel.Competition, newItem: LeagueResponsModel.Competition): Boolean {
            return oldItem==newItem
        }

    }
    private val differ = AsyncListDiffer(this,Diff_CallBack)
   fun updateDate(newList:List<LeagueResponsModel.Competition>){
       differ.submitList(newList)
   }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = DataBindingUtil.inflate<LeagueListItemBinding>(inflater,
            R.layout.league_list_item, parent,false)
        return ViewHolder(rootView,actionsInterface)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.competition = differ.currentList[position]
        holder.bind(differ.currentList[position])
    }

    class ViewHolder (var view:LeagueListItemBinding,private val characterActions: ComponentActionsClickListener?):
        RecyclerView.ViewHolder(view.root){
        fun bind(itemL1: LeagueResponsModel.Competition){
            view.competition = itemL1
            view.listener = characterActions
            view.executePendingBindings()
        }
    }



    interface ComponentActionsClickListener {
        fun onItemClicked(v: View,item: LeagueResponsModel.Competition)

    }
}