package com.purnendu.contactnamechanger.utils.contactOperation

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.purnendu.contactnamechanger.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun isContactExist(context: Context, phoneNumber: String): Contact? = withContext(Dispatchers.IO)
{
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
        if (it.moveToFirst()) {
            val IDIndex = it.getColumnIndex(ContactsContract.PhoneLookup._ID)
            val nameIndex = it.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.PhoneLookup.NUMBER)

            val id = if (IDIndex != -1) it.getLong(IDIndex) else null
            val name = if (nameIndex != -1) it.getString(nameIndex) else null
            val number = if (numberIndex != -1) it.getString(numberIndex) else null

            if (id != null && name != null && number != null) {
                return@withContext Contact(id, name, number)
            }
        }
    }
    return@withContext null
}