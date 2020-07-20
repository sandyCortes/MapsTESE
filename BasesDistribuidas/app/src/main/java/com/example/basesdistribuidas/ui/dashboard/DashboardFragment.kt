package com.example.basesdistribuidas.ui.dashboard

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.basesdistribuidas.R
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val places: ArrayList<String> = ArrayList()
        places.add("Vinculación")
        places.add("Control escolar")
        places.add("Biblioteca")
        places.add("Centro de Gobierno")
        places.add("Edificio Q")
        places.add("Edificio K")

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        val listView: ListView = root.findViewById(R.id.list)
        val btnPDF: Button = root.findViewById(R.id.btnPDF)

        val adaptador =
            getActivity()?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, places) }

        listView.adapter = adaptador

        btnPDF.setOnClickListener(View.OnClickListener {
            savePdf(places)
        })
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
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