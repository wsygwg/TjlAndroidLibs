package cn.com.hiss.www.multilib.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DB_CHAT_MESSAGE_BEAN".
*/
public class DbChatMessageBeanDao extends AbstractDao<DbChatMessageBean, Long> {

    public static final String TABLENAME = "DB_CHAT_MESSAGE_BEAN";

    /**
     * Properties of entity DbChatMessageBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property DbId = new Property(0, Long.class, "dbId", true, "_id");
        public final static Property LoginUserId = new Property(1, String.class, "loginUserId", false, "LOGIN_USER_ID");
        public final static Property GroupId = new Property(2, String.class, "groupId", false, "GROUP_ID");
        public final static Property UserId = new Property(3, String.class, "userId", false, "USER_ID");
        public final static Property Content = new Property(4, String.class, "content", false, "CONTENT");
        public final static Property Type = new Property(5, String.class, "type", false, "TYPE");
        public final static Property Trans = new Property(6, String.class, "trans", false, "TRANS");
        public final static Property CreateDate = new Property(7, String.class, "createDate", false, "CREATE_DATE");
        public final static Property Latitude = new Property(8, String.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(9, String.class, "longitude", false, "LONGITUDE");
        public final static Property MyId = new Property(10, String.class, "myId", false, "MY_ID");
        public final static Property SendState = new Property(11, int.class, "sendState", false, "SEND_STATE");
    }


    public DbChatMessageBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DbChatMessageBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DB_CHAT_MESSAGE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: dbId
                "\"LOGIN_USER_ID\" TEXT," + // 1: loginUserId
                "\"GROUP_ID\" TEXT," + // 2: groupId
                "\"USER_ID\" TEXT," + // 3: userId
                "\"CONTENT\" TEXT," + // 4: content
                "\"TYPE\" TEXT," + // 5: type
                "\"TRANS\" TEXT," + // 6: trans
                "\"CREATE_DATE\" TEXT," + // 7: createDate
                "\"LATITUDE\" TEXT," + // 8: latitude
                "\"LONGITUDE\" TEXT," + // 9: longitude
                "\"MY_ID\" TEXT," + // 10: myId
                "\"SEND_STATE\" INTEGER NOT NULL );"); // 11: sendState
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DB_CHAT_MESSAGE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DbChatMessageBean entity) {
        stmt.clearBindings();
 
        Long dbId = entity.getDbId();
        if (dbId != null) {
            stmt.bindLong(1, dbId);
        }
 
        String loginUserId = entity.getLoginUserId();
        if (loginUserId != null) {
            stmt.bindString(2, loginUserId);
        }
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(3, groupId);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(4, userId);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(5, content);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(6, type);
        }
 
        String trans = entity.getTrans();
        if (trans != null) {
            stmt.bindString(7, trans);
        }
 
        String createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindString(8, createDate);
        }
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(9, latitude);
        }
 
        String longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindString(10, longitude);
        }
 
        String myId = entity.getMyId();
        if (myId != null) {
            stmt.bindString(11, myId);
        }
        stmt.bindLong(12, entity.getSendState());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DbChatMessageBean entity) {
        stmt.clearBindings();
 
        Long dbId = entity.getDbId();
        if (dbId != null) {
            stmt.bindLong(1, dbId);
        }
 
        String loginUserId = entity.getLoginUserId();
        if (loginUserId != null) {
            stmt.bindString(2, loginUserId);
        }
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(3, groupId);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(4, userId);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(5, content);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(6, type);
        }
 
        String trans = entity.getTrans();
        if (trans != null) {
            stmt.bindString(7, trans);
        }
 
        String createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindString(8, createDate);
        }
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(9, latitude);
        }
 
        String longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindString(10, longitude);
        }
 
        String myId = entity.getMyId();
        if (myId != null) {
            stmt.bindString(11, myId);
        }
        stmt.bindLong(12, entity.getSendState());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DbChatMessageBean readEntity(Cursor cursor, int offset) {
        DbChatMessageBean entity = new DbChatMessageBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // dbId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // loginUserId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // groupId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // content
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // type
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // trans
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // createDate
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // latitude
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // longitude
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // myId
            cursor.getInt(offset + 11) // sendState
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DbChatMessageBean entity, int offset) {
        entity.setDbId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLoginUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroupId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setContent(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTrans(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCreateDate(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setLatitude(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLongitude(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setMyId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSendState(cursor.getInt(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DbChatMessageBean entity, long rowId) {
        entity.setDbId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DbChatMessageBean entity) {
        if(entity != null) {
            return entity.getDbId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DbChatMessageBean entity) {
        return entity.getDbId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
