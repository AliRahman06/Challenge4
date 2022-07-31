package com.binar.challenge4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.binar.challenge4.data.StudentDatabase
import com.binar.challenge4.data.model.Student
import com.binar.challenge4.runOnUiThread
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executors


class EditFragment : DialogFragment() {

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

    override fun onResume(){
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val objectStudent = arguments?.getParcelable<Student>("student")
        mDb = StudentDatabase.getInstance(view.context)
        binding.apply{
            edtJudul.setText(objectStudent?.judul)
            edtCatatan.setText(objectStudent?.Catatan)
            btnSave.setOnClickListener{
                objectStudent?.judul = edtJudul.text.toString()
                objectStudent?.catatan = edtCatatan.text.toString()

                val executor = Executors.newFixedThreadPool(1)

                executor.execute {
                    val result = mDb?.studentDao()?.updateStudent(objectStudent)
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        StudentDatabase.destroyInstance()
}