package com.purnendu.contactnamechanger.utils.alarmManager

import android.content.BroadcastReceiver
import android.content.ContentProviderOperation
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.purnendu.contactnamechanger.database.AlarmDatabase
import com.purnendu.contactnamechanger.database.daos.AlarmDao
import com.purnendu.contactnamechanger.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null)
            return

        val alarmId = intent?.getStringExtra("alarm_id")
        val phoneNo = intent?.getStringExtra("phoneNo")
        val displayName = intent?.getStringExtra("displayName")

        if (phoneNo != null && displayName!=null) {
            updateContactDisplayNameIfNumberExists(
                context = context,
                phoneNumber = phoneNo,
                displayName = displayName
            )
        }
    }

    private fun getContactDetailsFromPhoneNumber(context: Context, phoneNumber: String): Contact? {
        val contentResolver = context.contentResolver

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

                if (id != null && name != null && number != null) {
                    return Contact(id, name, number)
                }
            }
        }

        return null
    }




    private fun updateContactDisplayNameIfNumberExists(
        context: Context,
        phoneNumber: String,
        displayName: String
    ) {
        val contact = getContactDetailsFromPhoneNumber(context, phoneNumber)

        if (contact != null) {
            val operations = arrayListOf<ContentProviderOperation>()

            // Add the note to the contact
            operations.add(
                ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                        "${ContactsContract.Data.CONTACT_ID}=? AND ${ContactsContract.Data.MIMETYPE}=?",
                        arrayOf(
                            contact.id.toString(),
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                        )
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        displayName
                    )
                    .build()
            )

            try {
                context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
                Log.d("ContactUpdate", "Contact updated successfully with note.")
            } catch (e: Exception) {
                Log.e("ContactUpdate", "Error updating contact: ${e.message}")
            }
        } else {
            Log.d("ContactUpdate", "No contact found with the provided phone number.")
        }
    }
}