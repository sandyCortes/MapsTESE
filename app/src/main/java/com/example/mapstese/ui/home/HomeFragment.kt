package com.example.mapstese.ui.home

import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mapstese.R
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Rectangle
import com.itextpdf.text.log.LoggerFactory
import com.itextpdf.text.pdf.PdfWriter
import org.w3c.dom.Document
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var lugares: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val list: ListView = root.findViewById(R.id.list_view)
        val btnPDF = root.findViewById<Button>(R.id.btnPDF)

        lugares.add("Servicio Social")
        lugares.add("Inscripciones")
        lugares.add("Compra de credenciales")
        lugares.add("Inscripción a talleres")
        lugares.add("Centro de idiomas")

        val adaptador =
            context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, lugares) }

        list.adapter = adaptador


        btnPDF.setOnClickListener(View.OnClickListener {
          savePdf()
        })

        return root
    }

    private fun savePdf() {
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
