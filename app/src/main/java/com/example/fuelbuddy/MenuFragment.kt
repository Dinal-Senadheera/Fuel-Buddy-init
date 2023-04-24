package com.example.fuelbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class MenuFragment: Fragment() {
    private val fragmentPosted = PostedFragment()
    private val requestFragment = RequestFragment()
    private lateinit var requestBtn: Button
    private lateinit var postBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.menu_fragment, container, false)
        requestBtn = view.findViewById(R.id.edtRequests)
        postBtn = view.findViewById(R.id.edtPosted)

        childFragmentManager.beginTransaction().apply {
            replace(R.id.postedInit, fragmentPosted)
            commit()
        }

        requestBtn.setOnClickListener {
            requestBtn.setBackgroundColor(resources.getColor(R.color.primary))
            postBtn.setBackgroundColor(resources.getColor(R.color.background))
            childFragmentManager.beginTransaction().apply {
                replace(R.id.postedInit, requestFragment)
                commit()
            }
        }

        postBtn.setOnClickListener {
            postBtn.setBackgroundColor(resources.getColor(R.color.primary))
            requestBtn.setBackgroundColor(resources.getColor(R.color.background))
            childFragmentManager.beginTransaction().apply {
                replace(R.id.postedInit, fragmentPosted)
                commit()
            }
        }
        return view
    }
}
