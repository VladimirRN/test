package com.example.vladimir.contactreader.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.model.db.ContactDao;

import java.util.List;

import io.reactivex.Observable;

public class DetailsLoad {

    private static final String TAG = "TAG";
    private final String SORT_ORDER = ContactsContract.Data.MIMETYPE;
    private final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";
    private final String[] PROJECTION = {
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME};
    private Context context;
    private String[] selectionArgs = {""};
    private Cursor cursor;

    public DetailsLoad(Context context) {
        this.context = context;
    }

    public Observable<String> getDeatailContact() {
        return Observable.create(emitter -> {
            AppDataBase db = App.getInstance().getDataBase();
            ContactDao contactDao = db.contactDao();
            List<Contact> listContact = contactDao.getAll();
            for (int i = 0; i < listContact.size(); i++) {
                String key = listContact.get(i).getLookupKey();
                selectionArgs[0] = key;
                long id = listContact.get(i).getId();
                ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
                cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, PROJECTION, SELECTION, selectionArgs, SORT_ORDER);
                if (cursor.moveToFirst()) {
                    do {
                        String mime = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                        switch (mime) {
                            case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                                String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                                contactDao.updateName(name, (int) id);
                                contactDao.updateSurname(surname, (int) id);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                contactDao.updatePhone(number, (int) id);
                                break;
                            case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                                String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                                contactDao.updateEmail(address, (int) id);
                                break;
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}

