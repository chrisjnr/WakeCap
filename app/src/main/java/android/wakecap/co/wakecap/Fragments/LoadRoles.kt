package android.wakecap.co.wakecap.Fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.widget.*
import kotlinx.android.synthetic.main.fragment_load_roles.*
import java.lang.Exception



class LoadRoles : Fragment() {
    lateinit var myDialog: Dialog
    lateinit var adapter: WorkersAdapter
    lateinit var arrayAdapter: ArrayAdapter<String>
    var contactList: MutableList<Item> = mutableListOf(Item())
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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        adapter = WorkersAdapter(contactList, WorkersAdapter.ListItemClick {
            fun clickedWorker(workerDetails: Attributes){
                myDialog = Dialog(context!!)
                myDialog.setContentView(R.layout.worker_profile)
                val closeDialog = myDialog.findViewById<ImageButton>(R.id.closeDialog);
                val fullName = myDialog.findViewById<TextView>(R.id.userName)
                val role = myDialog.findViewById<TextView>(R.id.workerRole)
                val workerId = myDialog.findViewById<TextView>(R.id.workerId)
                val helmetColor = myDialog.findViewById<ImageView>(R.id.helmetColor)
                val available = myDialog.findViewById<TextView>(R.id.available)
                val phoneNumber = myDialog.findViewById<TextView>(R.id.phoneNumber)


                fullName.text = workerDetails.fullName
                role.text = workerDetails.roles
                workerId.setText(workerDetails.id!!)

                closeDialog.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        myDialog.dismiss()
                    }
                })
//                helmetColor.setBackgroundColor(Color.parseColor(workerDetails.helmetColor))
                if (workerDetails.inventories[0].attributes.isOnline!!) {
                    available.text = getString(R.string.online)
                } else {
                    available.text = getString(R.string.offline)
                }
                if (workerDetails.mobileNumber == null) {
                    phoneNumber.text = getString(R.string.not_config)
                } else {
                    phoneNumber.text = workerDetails.mobileNumber.toString()
                }

                myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
                val height = (resources.displayMetrics.heightPixels * 0.90).toInt()
                myDialog.window!!.setLayout(width, height)
                myDialog.show()
            }



            clickedWorker(it)

        } )
//        recyclerView.adapter = adapter
        workersViewModel = activity?.run {
            ViewModelProviders.of(this).get(WorkersViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        workersViewModel.workersList.observe(this, Observer<MutableList<Item>> { items ->
            // Update the UI
            val keyList: MutableList<String> = mutableListOf(String())
            workerRoleMap = mutableMapOf<String, MutableList<Item>>()
            val iterator = items!!.listIterator()
            for (item in iterator){
                val key = item.attributes.roles
                if (workerRoleMap.containsKey(key)){
                    var foundKey: MutableList<Item>? = workerRoleMap!![key]
                    foundKey!!.add(item)
                }else {
                    keyList.add(key)
                    val newKey: MutableList<Item>? = ArrayList<Item>()
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
                    if (!itemsList.isNullOrEmpty()){
                        adapter = WorkersAdapter(itemsList,
                                WorkersAdapter.ListItemClick {
                                    fun clickedWorker(workerDetails: Attributes){
                                        myDialog = Dialog(context!!)
                                        myDialog.setContentView(R.layout.worker_profile)
                                        val fullName = myDialog.findViewById<TextView>(R.id.userName)
                                        val role = myDialog.findViewById<TextView>(R.id.workerRole)
                                        val workerId = myDialog.findViewById<TextView>(R.id.workerId)
//                                        val helmetColor = myDialog.findViewById<ImageView>(R.id.helmetColor)
                                        val available = myDialog.findViewById<TextView>(R.id.available)
                                        val phoneNumber = myDialog.findViewById<TextView>(R.id.phoneNumber)


                                        fullName.text = workerDetails.fullName
                                        role.text = workerDetails.roles
                                        workerId.text = workerDetails.id!!.toString()
//
                                        if (workerDetails.inventories[0].attributes.isOnline!!) {
                                            available.text = getString(R.string.online)
                                        } else {
                                            available.text = getString(R.string.offline2)
                                        }
                                        if (workerDetails.mobileNumber == null) {
                                            phoneNumber.text = getString(R.string.not_config)
                                        } else {
                                            phoneNumber.text = workerDetails.mobileNumber.toString()
                                        }
                                        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
                                        val height = (resources.displayMetrics.heightPixels * 0.90).toInt()
                                        myDialog.window!!.setLayout(width, height)
                                        myDialog.show()
                                    }
                                    clickedWorker(it)
                                })
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }

                }

            }
        })

        }
    }

