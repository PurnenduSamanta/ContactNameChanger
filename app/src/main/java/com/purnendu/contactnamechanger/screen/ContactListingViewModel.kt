package com.purnendu.contactnamechanger.screen

import android.app.Application
import android.content.ContentProviderOperation
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class ContactListingViewModel(private val application: Application): AndroidViewModel(application)
{

    /*fun getContacts():List<Contact> {
        val contentResolver = application.contentResolver
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
        )
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        val contacts = mutableListOf<Contact>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0

                if (hasPhoneNumber) {
                    // Get the phone number using the appropriate column
                    val phoneColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    if (phoneColumnIndex >= 0) {
                        val phoneNumber = cursor.getString(phoneColumnIndex)
                        contacts.add(Contact(contactId, name, phoneNumber))
                    }
                }
            }
            cursor.close()
        }

       return  contacts
    }*/




}