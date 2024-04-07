package com.example.mathpuzzle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Class1(var levelpage: Levelpage,var array: Array<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return array.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var card: TextView

        var view = LayoutInflater.from(levelpage).inflate(R.layout.puzzlebutton, parent, false)
        card = view.findViewById(R.id.card)

        var n = MainActivity.sp.getInt("mylevel",1)-1

        if(MainActivity.sp.getString("key${position+1}","lock").equals(MainActivity.lock) &&  n!=position){
            card.setBackgroundResource(R.drawable.lock)
        }
        else if(MainActivity.sp.getString("key${position+1}","lock").equals(MainActivity.skip)){
            card.setText(array[position])
        }
        else if(MainActivity.sp.getString("key${position+1}","lock").equals(MainActivity.complate)){
            card.setText(array[position])
            card.setBackgroundResource(R.drawable.done)

        }
        if(n==(position))
        {
            card.setText(array[position])
        }

        return view
    }

}
