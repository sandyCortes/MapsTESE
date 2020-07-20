package com.example.basesdistribuidas.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.basesdistribuidas.R
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory.COURIER
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.w3c.dom.Document
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val STORAGE_CODE: Int = 100;

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val users: ArrayList<String> = ArrayList()
        users.add("Sandy F")
        users.add("Max santiago")
        users.add("Valeria perez")
        users.add("Jhon Bravo")
        users.add("Hola")
        users.add("Adios")

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val listView: ListView = root.findViewById(R.id.list)
        val btnPDF: Button = root.findViewById(R.id.btnPDF)

        val adaptador =
            getActivity()?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, users) }

        listView.adapter = adaptador

        btnPDF.setOnClickListener(View.OnClickListener {
            savePdf(users)
        })
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    private fun savePdf(lugares: ArrayList<String>) {
        val mDoc = com.itextpdf.text.Document()
        //pdf file name
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        //pdf file path
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName +".pdf"
        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //open the document for writing
            mDoc.open()

            //add author of the document (metadata)
            mDoc.addAuthor("Sandy F")

            for (item in lugares) {
                mDoc.add(Paragraph(item))
            }

            //close document
            mDoc.close()

            //show file saved message with file name and path

            Toast.makeText(context, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            Toast.makeText(context, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
        }
    }
}