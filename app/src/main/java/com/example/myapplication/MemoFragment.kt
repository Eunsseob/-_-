package com.example.myapplication

import adapter.MemoAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import db.DBLoader_1


class MemoFragment : Fragment() {

    private lateinit var adapter: MemoAdapter
    private lateinit var text_msg: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_memo, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btn_add)
        text_msg = view.findViewById<TextView>(R.id.text_msg)
        btnAdd.setOnClickListener {
            startActivity(Intent(requireContext(), MemoActivity::class.java))
        }

        adapter = MemoAdapter(requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.setList(DBLoader_1(requireContext()).memoList(null))
        if (adapter.itemCount > 0){
            text_msg.visibility = View.INVISIBLE
        }else{
            text_msg.visibility = View.VISIBLE
        }
    }

}
