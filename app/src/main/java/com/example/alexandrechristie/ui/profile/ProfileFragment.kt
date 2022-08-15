package com.example.alexandrechristie.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alexandrechristie.data.entity.UserLogin
import com.example.alexandrechristie.data.entity.ViewOrder
import com.example.alexandrechristie.databinding.FragmentProfileBinding
import com.example.alexandrechristie.ui.login.LoginActivity
import com.example.alexandrechristie.ui.profile.adapter.ViewOrderAdapter
import com.example.alexandrechristie.viewModel.UserLoginViewModel
import com.example.alexandrechristie.viewModel.UserViewModel
import com.example.alexandrechristie.viewModel.ViewOrderViewModel


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var userLoginList: ArrayList<UserLogin>
    private lateinit var viewOrderList: ArrayList<ViewOrder>

    private val viewModel : UserLoginViewModel by activityViewModels()
    private val userViewModel : UserViewModel by activityViewModels()
    private val viewOrderViewModel : ViewOrderViewModel by activityViewModels()

    private lateinit var viewOrderAdapter: ViewOrderAdapter

    private val binding get() = _binding!!
    private var id : Int ?= null
    private lateinit var email : String
    private lateinit var imageView : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        val root : View = binding.root

//        get user information from main activity when login from login page
//        val intent = (activity as MainActivity).intent
//        val name = intent.getStringExtra("name").toString()
//        val phone=intent.getStringExtra("phone").toString()
//        val email=intent.getStringExtra("email").toString()
//        val profile=intent.getStringExtra("profile").toString()
//
//        binding.uname.text = phone
//        binding.uPhone.text = name
//        binding.uEmail.text=email
//        binding.imgProfile.setImageURI(Uri.parse(profile))

        return root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLoginList = arrayListOf()
        viewOrderList= arrayListOf()
        getUserLogin()
        onClick()

//        initRec()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUserLogin() {
        viewModel.getUserLogin().observe(viewLifecycleOwner){
            if(it.isNotEmpty()) {
                userLoginList = it as ArrayList<UserLogin>
                for (userLogin in userLoginList){
                    id=userLogin.userId
                    email=userLogin.email

                    binding.uname.text=userLogin.userName
                    binding.uPhone.text=userLogin.phone
                    binding.uEmail.text=userLogin.email
                    binding.imgProfile.setImageURI(Uri.parse(userLogin.userImg))
                }
            }
        }
    }

    private fun onClick() {
        binding.logout.setOnClickListener{
            deleteAllUserData()
            startActivity(Intent(activity,LoginActivity::class.java))
            activity?.finish()
        }
        binding.delete.setOnClickListener{
            id?.let { it1 -> deleteUser(it1) }
            startActivity(Intent(activity,LoginActivity::class.java))
            activity?.finish()
        }
        binding.viewOrder.setOnClickListener{
            getViewOrder(email)
            initRec()
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getViewOrder(email: String) {
        viewOrderViewModel.getViewOrderByEmail(email).observe(viewLifecycleOwner){
//            Log.i("get food", "setupSearch: $it")
            if(it.isNotEmpty()) {
                viewOrderList = it as ArrayList<ViewOrder>
                viewOrderAdapter.setData(viewOrderList)
            }
        }
    }
    private fun initRec() {
        viewOrderAdapter = ViewOrderAdapter(viewOrderList)
        binding.recViewOrderList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewOrderAdapter
        }
    }

    private fun deleteUser(id : Int) {
        userViewModel.deleteAllUser(id)
    }

    private fun deleteAllUserData() {
        viewModel.deleteAllUserLogin()
    }

}