//
//
//package com.example.callcontentprovider
//
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Bundle
//import android.provider.CallLog
//import android.widget.ListView
//import android.widget.SimpleCursorAdapter
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//
//class MainActivity : AppCompatActivity() {
//
//    private val CALL_PHONE_REQUEST_CODE = 1
//    private val READ_CALL_LOG_REQUEST_CODE = 111
//    private val cols = arrayOf(CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls._ID)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG), READ_CALL_LOG_REQUEST_CODE)
//        } else {
//            readCallLogs()
//        }
//    }
//
//
//
//    private fun readCallLogs() {
//        val cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, cols, null, null, null)
//        val adapter = SimpleCursorAdapter(
//            applicationContext,
//            android.R.layout.simple_list_item_2,
//            cursor,
//            cols,
//            intArrayOf(android.R.id.text1, android.R.id.text2),
//            0
//        )
//
//        val listView: ListView = findViewById(R.id.listView)
//        listView.adapter = adapter
//
//        listView.setOnItemClickListener { _, _, position, _ ->
//            cursor?.moveToPosition(position)
//            val phoneNumber = cursor?.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER))
//
//            if (phoneNumber != null) {
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_REQUEST_CODE)
//                } else {
//                    makePhoneCall(phoneNumber)
//                }
//            }
//        }
//    }
//
//    private fun makePhoneCall(phoneNumber: String) {
//        val callIntent = Intent(Intent.ACTION_CALL)
//        callIntent.data = Uri.parse("tel:$phoneNumber")
//        startActivity(callIntent)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == CALL_PHONE_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, you might need to handle further logic if needed
//            } else {
//                Toast.makeText(this, "Call permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
//



package com.example.callcontentprovider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val CALL_PHONE_REQUEST_CODE = 1
    private val READ_CONTACTS_REQUEST_CODE = 111

    private val cols = arrayOf(ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts._ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), READ_CONTACTS_REQUEST_CODE)
        } else {
            readContacts()
        }
    }

    private fun readContacts() {
        val cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, cols, null, null, null)
        val adapter = SimpleCursorAdapter(
            applicationContext,
            android.R.layout.simple_list_item_2,
            cursor,
            cols,
            intArrayOf(android.R.id.text1, android.R.id.text2),
            0
        )

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            cursor?.moveToPosition(position)
            val phoneNumber = cursor?.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            if (phoneNumber != null) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_REQUEST_CODE)
                } else {
                    makePhoneCall(phoneNumber)
                }
            }
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_CONTACTS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this, "Contacts permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            CALL_PHONE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                } else {
                    Toast.makeText(this, "Call permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
