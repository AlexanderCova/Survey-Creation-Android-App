package io.github.alexandercova.surveycreationapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.alexandercova.surveycreationapp.Adapters.RecyclerAdapter
import io.github.alexandercova.surveycreationapp.R


class SurveysFragment : Fragment() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_surveys, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = Firebase.auth
        val db = Firebase.firestore
        val recyclerView = view.findViewById<RecyclerView>(R.id.mySurveysRecyclerView)


        layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()





        db.collection(auth.uid!!).get().addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.data["name"]
                    val surveyPoints = document.data.count()

                    (adapter as RecyclerAdapter).surveyNames.add(name as String)
                    (adapter as RecyclerAdapter).surveyPoints.add(surveyPoints)

                }

                recyclerView.adapter = adapter
            }
    }

}