package com.binar.challenge4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.binar.challenge4.data.StudentDatabase
import com.binar.challenge4.data.model.Student
import com.binar.challenge4.runOnUiThread
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executors


class addFragment : DialogFragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var mDb: StudentDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FregmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = StudentDatabase.getInstance(view.context)
        binding.apply{
            btnSave.setOnClickListener{
                val student = Student(null, judul = edtJudul.text.toString(), catatan = edtCatatan.text.toString())

                val executor = Executors.newFixedThreadPool(1)

                executor.execute {
                    val result = mDb?.studentDao()?.insertStudent(student)
                    runOnUiThread {
                        if(result != 0.toLong()){
                            Toast.makeText(view.context, "Berhasil Disave", Toast.LENGTH_SHORT).show()
                        }else{
                            Snackbar.make(binding.root, "Terjadi Kesalahan", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
                dialog?.dimiss()
            }
            btnBatal.setOnClickListener{
                dialog?dimiss()
            }
        }
    }

    override fun onResume(){
        super.onResume()
        dialog?.window?.seLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}

