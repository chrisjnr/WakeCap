package android.wakecap.co.wakecap.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.wakecap.co.wakecap.Adapters.WorkersAdapter
import android.wakecap.co.wakecap.Models.Attributes
import android.wakecap.co.wakecap.Models.Item

import android.wakecap.co.wakecap.R
import android.wakecap.co.wakecap.ViewModels.WorkersViewModel
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_load_roles.*
import java.lang.Exception



class LoadRoles : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var adapter: WorkersAdapter
    lateinit var arrayAdapter: ArrayAdapter<String>
    var contactList: MutableList<Item> = mutableListOf(Item())
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var workersViewModel: WorkersViewModel
    lateinit var workerRoleMap: MutableMap<String, MutableList<Item>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_roles, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
//        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        adapter = WorkersAdapter(contactList)
//        recyclerView.adapter = adapter
        workersViewModel = activity?.run {
            ViewModelProviders.of(this).get(WorkersViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        workersViewModel.workersList.observe(this, Observer<MutableList<Item>> { items ->
            // Update the UI
            var keyList: MutableList<String> = mutableListOf(String())
            workerRoleMap = mutableMapOf<String, MutableList<Item>>()
            var iterator = items!!.listIterator()
            for (item in iterator){
                var key:String = item.attributes.roles
                if (workerRoleMap.containsKey(key)){
                    var foundKey: MutableList<Item>? = workerRoleMap!![key]
                    foundKey!!.add(item)
                }else {
                    keyList.add(key)
                    var newKey: MutableList<Item>? = ArrayList<Item>()
                    newKey!!.add(item)
                    workerRoleMap[key] = newKey
                }
            }

            arrayAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, keyList)
            sp1.adapter = arrayAdapter
            sp1?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    var mapKey: String? = arrayAdapter.getItem(position)
                    var itemsList:MutableList<Item>? = workerRoleMap[mapKey]
//                    adapter = WorkersAdapter(itemsList)
                    if (!itemsList.isNullOrEmpty()){
                        adapter = WorkersAdapter(itemsList)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
//                    Toast.makeText(context, ""+ itemsList.isNullOrEmpty(), Toast.LENGTH_SHORT).show()
//
                }

            }
        })

        }
    }

