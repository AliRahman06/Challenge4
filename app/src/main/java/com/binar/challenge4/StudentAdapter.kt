package com.binar.challenge4

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import com.binar.challenge4.data.StudentDatabase
import com.binar.challenge4.data.model.Student
import java.util.concurrent.Executors

class StudentAdapter : ListAdapter<Student, StudentAdapter, viewHolder>(StudentDiffCallback()){
    private val binding = StudentItemBinding.bind(itemview)

    fun bind(data: Student){
        binding.tvID.text = data.id.toString()
        binding.tvNama.text = data.judul
        binding.tvEmail.text = data.catatan
        binding.ivEdit.setOnClickListener {
            val sendData = HomeFragmentDirections.actionHomeFragmentToEditFragment(data)
            it.findNavController().navigate(sendData)
        }

        binding.ivDelete.setOnClickListener{
            AlertDialog.Builder(it.context)
                .setMessage("Apakah yakin ingin menghapus catatan ini?")
                .setCancelable(false)
                .setPositiveButton("ya"){_, _ ->
                    val db = StudentDatabase.getInstance(itemView.context)

                    val executor = Executors.newCachedThreadPool()

                    executor.execute {
                        val result = db?.studentDao()?.deleteStudent(data)
                        (itemView.context as MainActivity).runOnUiThread{
                            if(result != 0){
                                Toast.makeText(itemView.context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(itemView.context, "Data gagal dihapus", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Tidak"){ dialog, _ ->
                    dialog?.dimiss
                }
                .show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val data = getItem(position)
        holder.bind(data)
    }
}

class StudentDiffCallback: DiffUtil.ItemCallBack<Student>(){
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean{
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean{
        return oldItem == newItem
}