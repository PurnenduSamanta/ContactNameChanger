package com.purnendu.contactnamechanger.screen

import android.app.Application
import android.content.ContentProviderOperation
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class ContactListingViewModel(private val application: Application): AndroidViewModel(application)
{

    fun getContacts():List<Contact> {
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
    }



    fun getContactByPhone(phoneNumber: String): Contact? {
        val contentResolver = application.contentResolver // Use safe call operator

        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val selection = ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?"
        val selectionArgs = arrayOf(phoneNumber)

        val cursor = contentResolver?.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val nameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val name = if (nameColumnIndex >= 0) {
                cursor.getString(nameColumnIndex)
            } else {
                "" // Handle case where name column is missing
            }
            val phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            cursor.close()
            return Contact(contactId, name, phone)
        } else {
            cursor?.close()
            return null // No contact found with the phone number
        }
    }


    fun getContactDetailsFromPhoneNumber( phoneNumber: String): Contact? {
        val contentResolver = application.contentResolver

        val projection = arrayOf(
            ContactsContract.PhoneLookup._ID,
            ContactsContract.PhoneLookup.DISPLAY_NAME,
            ContactsContract.PhoneLookup.NUMBER
        )

        // Uri to search the contact using the phone number
        val uri = ContactsContract.PhoneLookup.CONTENT_FILTER_URI.buildUpon()
            .appendPath(phoneNumber)
            .build()

        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            //it.columnNames.forEach {  Log.d("hello",  it )}
            if (it.moveToFirst()) {
                val IDIndex = it.getColumnIndex(ContactsContract.PhoneLookup._ID)
                val nameIndex = it.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)
                val numberIndex = it.getColumnIndex(ContactsContract.PhoneLookup.NUMBER)

                val id = if (IDIndex != -1) it.getLong(IDIndex) else null
                val name = if (nameIndex != -1) it.getString(nameIndex) else null
                val number = if (numberIndex != -1) it.getString(numberIndex) else null

                if (id!=null && name!=null && number!=null) {
                    return Contact(id,name,number)
                }
            }
        }

        return null
    }

    fun updateContactNoteIfNumberExists( phoneNumber: String, note: String) {
        val contact = getContactDetailsFromPhoneNumber(phoneNumber)

        if (contact != null) {
            val operations = arrayListOf<ContentProviderOperation>()

            // Add the note to the contact
            operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(
                    "${ContactsContract.PhoneLookup._ID}=?",
                    arrayOf(contact.id.toString())
                )
                .withValue(ContactsContract.Contacts._ID, contact.id)
                .withValue(ContactsContract.Contacts.DISPLAY_NAME, note)
                .build()
            )

            try {
                application.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
                Log.d("ContactUpdate", "Contact updated successfully with note.")
            } catch (e: Exception) {
                Log.e("ContactUpdate", "Error updating contact: ${e.message}")
            }
        } else {
            Log.d("ContactUpdate", "No contact found with the provided phone number.")
        }
    }



    data class Contact(val id: Long, val name: String,val phNoneNumber: String)

}