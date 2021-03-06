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
 * DAO for table "DB_GET_STUDENT".
*/
public class DbGetStudentDao extends AbstractDao<DbGetStudent, Long> {

    public static final String TABLENAME = "DB_GET_STUDENT";

    /**
     * Properties of entity DbGetStudent.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property DbId = new Property(0, Long.class, "dbId", true, "_id");
        public final static Property StuImage = new Property(1, String.class, "stuImage", false, "STU_IMAGE");
        public final static Property StuInterest = new Property(2, String.class, "stuInterest", false, "STU_INTEREST");
        public final static Property RealName = new Property(3, String.class, "realName", false, "REAL_NAME");
        public final static Property Sex = new Property(4, String.class, "sex", false, "SEX");
        public final static Property Age = new Property(5, String.class, "age", false, "AGE");
        public final static Property Star = new Property(6, String.class, "star", false, "STAR");
        public final static Property BirthDay = new Property(7, String.class, "birthDay", false, "BIRTH_DAY");
        public final static Property Introduction = new Property(8, String.class, "introduction", false, "INTRODUCTION");
        public final static Property AboutSelf = new Property(9, String.class, "aboutSelf", false, "ABOUT_SELF");
        public final static Property ClassName = new Property(10, String.class, "className", false, "CLASS_NAME");
        public final static Property UniversityName = new Property(11, String.class, "universityName", false, "UNIVERSITY_NAME");
        public final static Property Hometown = new Property(12, String.class, "hometown", false, "HOMETOWN");
        public final static Property MemberId = new Property(13, String.class, "memberId", false, "MEMBER_ID");
        public final static Property ImgUrl = new Property(14, String.class, "imgUrl", false, "IMG_URL");
        public final static Property CreateTime = new Property(15, String.class, "createTime", false, "CREATE_TIME");
        public final static Property Phone = new Property(16, String.class, "phone", false, "PHONE");
        public final static Property Minority = new Property(17, String.class, "minority", false, "MINORITY");
        public final static Property Tel = new Property(18, String.class, "tel", false, "TEL");
        public final static Property Password = new Property(19, String.class, "password", false, "PASSWORD");
        public final static Property City = new Property(20, String.class, "city", false, "CITY");
        public final static Property SickName = new Property(21, String.class, "sickName", false, "SICK_NAME");
        public final static Property FacultyName = new Property(22, String.class, "facultyName", false, "FACULTY_NAME");
        public final static Property CardNo = new Property(23, String.class, "cardNo", false, "CARD_NO");
        public final static Property Token = new Property(24, String.class, "token", false, "TOKEN");
        public final static Property MotherId = new Property(25, String.class, "motherId", false, "MOTHER_ID");
        public final static Property FatherId = new Property(26, String.class, "fatherId", false, "FATHER_ID");
        public final static Property FacultyClassId = new Property(27, String.class, "facultyClassId", false, "FACULTY_CLASS_ID");
        public final static Property LoginName = new Property(28, String.class, "loginName", false, "LOGIN_NAME");
        public final static Property ContactPhone = new Property(29, String.class, "contactPhone", false, "CONTACT_PHONE");
        public final static Property StudentNo = new Property(30, String.class, "studentNo", false, "STUDENT_NO");
        public final static Property UpdateTime = new Property(31, String.class, "updateTime", false, "UPDATE_TIME");
        public final static Property Status = new Property(32, String.class, "status", false, "STATUS");
        public final static Property UniversityId = new Property(33, String.class, "universityId", false, "UNIVERSITY_ID");
        public final static Property Country = new Property(34, String.class, "country", false, "COUNTRY");
        public final static Property Email = new Property(35, String.class, "email", false, "EMAIL");
        public final static Property Address = new Property(36, String.class, "address", false, "ADDRESS");
        public final static Property ContactName = new Property(37, String.class, "contactName", false, "CONTACT_NAME");
        public final static Property Disabled = new Property(38, String.class, "disabled", false, "DISABLED");
    }

    private final StuImageConverter stuImageConverter = new StuImageConverter();
    private final StuInterestConverter stuInterestConverter = new StuInterestConverter();

    public DbGetStudentDao(DaoConfig config) {
        super(config);
    }
    
    public DbGetStudentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DB_GET_STUDENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: dbId
                "\"STU_IMAGE\" TEXT," + // 1: stuImage
                "\"STU_INTEREST\" TEXT," + // 2: stuInterest
                "\"REAL_NAME\" TEXT," + // 3: realName
                "\"SEX\" TEXT," + // 4: sex
                "\"AGE\" TEXT," + // 5: age
                "\"STAR\" TEXT," + // 6: star
                "\"BIRTH_DAY\" TEXT," + // 7: birthDay
                "\"INTRODUCTION\" TEXT," + // 8: introduction
                "\"ABOUT_SELF\" TEXT," + // 9: aboutSelf
                "\"CLASS_NAME\" TEXT," + // 10: className
                "\"UNIVERSITY_NAME\" TEXT," + // 11: universityName
                "\"HOMETOWN\" TEXT," + // 12: hometown
                "\"MEMBER_ID\" TEXT," + // 13: memberId
                "\"IMG_URL\" TEXT," + // 14: imgUrl
                "\"CREATE_TIME\" TEXT," + // 15: createTime
                "\"PHONE\" TEXT," + // 16: phone
                "\"MINORITY\" TEXT," + // 17: minority
                "\"TEL\" TEXT," + // 18: tel
                "\"PASSWORD\" TEXT," + // 19: password
                "\"CITY\" TEXT," + // 20: city
                "\"SICK_NAME\" TEXT," + // 21: sickName
                "\"FACULTY_NAME\" TEXT," + // 22: facultyName
                "\"CARD_NO\" TEXT," + // 23: cardNo
                "\"TOKEN\" TEXT," + // 24: token
                "\"MOTHER_ID\" TEXT," + // 25: motherId
                "\"FATHER_ID\" TEXT," + // 26: fatherId
                "\"FACULTY_CLASS_ID\" TEXT," + // 27: facultyClassId
                "\"LOGIN_NAME\" TEXT," + // 28: loginName
                "\"CONTACT_PHONE\" TEXT," + // 29: contactPhone
                "\"STUDENT_NO\" TEXT," + // 30: studentNo
                "\"UPDATE_TIME\" TEXT," + // 31: updateTime
                "\"STATUS\" TEXT," + // 32: status
                "\"UNIVERSITY_ID\" TEXT," + // 33: universityId
                "\"COUNTRY\" TEXT," + // 34: country
                "\"EMAIL\" TEXT," + // 35: email
                "\"ADDRESS\" TEXT," + // 36: address
                "\"CONTACT_NAME\" TEXT," + // 37: contactName
                "\"DISABLED\" TEXT);"); // 38: disabled
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DB_GET_STUDENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DbGetStudent entity) {
        stmt.clearBindings();
 
        Long dbId = entity.getDbId();
        if (dbId != null) {
            stmt.bindLong(1, dbId);
        }
 
        StuImage stuImage = entity.getStuImage();
        if (stuImage != null) {
            stmt.bindString(2, stuImageConverter.convertToDatabaseValue(stuImage));
        }
 
        StuInterest stuInterest = entity.getStuInterest();
        if (stuInterest != null) {
            stmt.bindString(3, stuInterestConverter.convertToDatabaseValue(stuInterest));
        }
 
        String realName = entity.getRealName();
        if (realName != null) {
            stmt.bindString(4, realName);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(5, sex);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(6, age);
        }
 
        String star = entity.getStar();
        if (star != null) {
            stmt.bindString(7, star);
        }
 
        String birthDay = entity.getBirthDay();
        if (birthDay != null) {
            stmt.bindString(8, birthDay);
        }
 
        String introduction = entity.getIntroduction();
        if (introduction != null) {
            stmt.bindString(9, introduction);
        }
 
        String aboutSelf = entity.getAboutSelf();
        if (aboutSelf != null) {
            stmt.bindString(10, aboutSelf);
        }
 
        String className = entity.getClassName();
        if (className != null) {
            stmt.bindString(11, className);
        }
 
        String universityName = entity.getUniversityName();
        if (universityName != null) {
            stmt.bindString(12, universityName);
        }
 
        String hometown = entity.getHometown();
        if (hometown != null) {
            stmt.bindString(13, hometown);
        }
 
        String memberId = entity.getMemberId();
        if (memberId != null) {
            stmt.bindString(14, memberId);
        }
 
        String imgUrl = entity.getImgUrl();
        if (imgUrl != null) {
            stmt.bindString(15, imgUrl);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(16, createTime);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(17, phone);
        }
 
        String minority = entity.getMinority();
        if (minority != null) {
            stmt.bindString(18, minority);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(19, tel);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(20, password);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(21, city);
        }
 
        String sickName = entity.getSickName();
        if (sickName != null) {
            stmt.bindString(22, sickName);
        }
 
        String facultyName = entity.getFacultyName();
        if (facultyName != null) {
            stmt.bindString(23, facultyName);
        }
 
        String cardNo = entity.getCardNo();
        if (cardNo != null) {
            stmt.bindString(24, cardNo);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(25, token);
        }
 
        String motherId = entity.getMotherId();
        if (motherId != null) {
            stmt.bindString(26, motherId);
        }
 
        String fatherId = entity.getFatherId();
        if (fatherId != null) {
            stmt.bindString(27, fatherId);
        }
 
        String facultyClassId = entity.getFacultyClassId();
        if (facultyClassId != null) {
            stmt.bindString(28, facultyClassId);
        }
 
        String loginName = entity.getLoginName();
        if (loginName != null) {
            stmt.bindString(29, loginName);
        }
 
        String contactPhone = entity.getContactPhone();
        if (contactPhone != null) {
            stmt.bindString(30, contactPhone);
        }
 
        String studentNo = entity.getStudentNo();
        if (studentNo != null) {
            stmt.bindString(31, studentNo);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(32, updateTime);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(33, status);
        }
 
        String universityId = entity.getUniversityId();
        if (universityId != null) {
            stmt.bindString(34, universityId);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(35, country);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(36, email);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(37, address);
        }
 
        String contactName = entity.getContactName();
        if (contactName != null) {
            stmt.bindString(38, contactName);
        }
 
        String disabled = entity.getDisabled();
        if (disabled != null) {
            stmt.bindString(39, disabled);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DbGetStudent entity) {
        stmt.clearBindings();
 
        Long dbId = entity.getDbId();
        if (dbId != null) {
            stmt.bindLong(1, dbId);
        }
 
        StuImage stuImage = entity.getStuImage();
        if (stuImage != null) {
            stmt.bindString(2, stuImageConverter.convertToDatabaseValue(stuImage));
        }
 
        StuInterest stuInterest = entity.getStuInterest();
        if (stuInterest != null) {
            stmt.bindString(3, stuInterestConverter.convertToDatabaseValue(stuInterest));
        }
 
        String realName = entity.getRealName();
        if (realName != null) {
            stmt.bindString(4, realName);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(5, sex);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(6, age);
        }
 
        String star = entity.getStar();
        if (star != null) {
            stmt.bindString(7, star);
        }
 
        String birthDay = entity.getBirthDay();
        if (birthDay != null) {
            stmt.bindString(8, birthDay);
        }
 
        String introduction = entity.getIntroduction();
        if (introduction != null) {
            stmt.bindString(9, introduction);
        }
 
        String aboutSelf = entity.getAboutSelf();
        if (aboutSelf != null) {
            stmt.bindString(10, aboutSelf);
        }
 
        String className = entity.getClassName();
        if (className != null) {
            stmt.bindString(11, className);
        }
 
        String universityName = entity.getUniversityName();
        if (universityName != null) {
            stmt.bindString(12, universityName);
        }
 
        String hometown = entity.getHometown();
        if (hometown != null) {
            stmt.bindString(13, hometown);
        }
 
        String memberId = entity.getMemberId();
        if (memberId != null) {
            stmt.bindString(14, memberId);
        }
 
        String imgUrl = entity.getImgUrl();
        if (imgUrl != null) {
            stmt.bindString(15, imgUrl);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(16, createTime);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(17, phone);
        }
 
        String minority = entity.getMinority();
        if (minority != null) {
            stmt.bindString(18, minority);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(19, tel);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(20, password);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(21, city);
        }
 
        String sickName = entity.getSickName();
        if (sickName != null) {
            stmt.bindString(22, sickName);
        }
 
        String facultyName = entity.getFacultyName();
        if (facultyName != null) {
            stmt.bindString(23, facultyName);
        }
 
        String cardNo = entity.getCardNo();
        if (cardNo != null) {
            stmt.bindString(24, cardNo);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(25, token);
        }
 
        String motherId = entity.getMotherId();
        if (motherId != null) {
            stmt.bindString(26, motherId);
        }
 
        String fatherId = entity.getFatherId();
        if (fatherId != null) {
            stmt.bindString(27, fatherId);
        }
 
        String facultyClassId = entity.getFacultyClassId();
        if (facultyClassId != null) {
            stmt.bindString(28, facultyClassId);
        }
 
        String loginName = entity.getLoginName();
        if (loginName != null) {
            stmt.bindString(29, loginName);
        }
 
        String contactPhone = entity.getContactPhone();
        if (contactPhone != null) {
            stmt.bindString(30, contactPhone);
        }
 
        String studentNo = entity.getStudentNo();
        if (studentNo != null) {
            stmt.bindString(31, studentNo);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(32, updateTime);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(33, status);
        }
 
        String universityId = entity.getUniversityId();
        if (universityId != null) {
            stmt.bindString(34, universityId);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(35, country);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(36, email);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(37, address);
        }
 
        String contactName = entity.getContactName();
        if (contactName != null) {
            stmt.bindString(38, contactName);
        }
 
        String disabled = entity.getDisabled();
        if (disabled != null) {
            stmt.bindString(39, disabled);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DbGetStudent readEntity(Cursor cursor, int offset) {
        DbGetStudent entity = new DbGetStudent( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // dbId
            cursor.isNull(offset + 1) ? null : stuImageConverter.convertToEntityProperty(cursor.getString(offset + 1)), // stuImage
            cursor.isNull(offset + 2) ? null : stuInterestConverter.convertToEntityProperty(cursor.getString(offset + 2)), // stuInterest
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // realName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // sex
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // age
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // star
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // birthDay
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // introduction
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // aboutSelf
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // className
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // universityName
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // hometown
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // memberId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // imgUrl
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // createTime
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // phone
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // minority
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // tel
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // password
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // city
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // sickName
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // facultyName
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // cardNo
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // token
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // motherId
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // fatherId
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // facultyClassId
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // loginName
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // contactPhone
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // studentNo
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // updateTime
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // status
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // universityId
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // country
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // email
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // address
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // contactName
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38) // disabled
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DbGetStudent entity, int offset) {
        entity.setDbId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStuImage(cursor.isNull(offset + 1) ? null : stuImageConverter.convertToEntityProperty(cursor.getString(offset + 1)));
        entity.setStuInterest(cursor.isNull(offset + 2) ? null : stuInterestConverter.convertToEntityProperty(cursor.getString(offset + 2)));
        entity.setRealName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSex(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAge(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStar(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setBirthDay(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIntroduction(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAboutSelf(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setClassName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUniversityName(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setHometown(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMemberId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setImgUrl(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCreateTime(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setPhone(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setMinority(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setTel(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setPassword(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setCity(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setSickName(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setFacultyName(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setCardNo(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setToken(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setMotherId(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setFatherId(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setFacultyClassId(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setLoginName(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setContactPhone(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setStudentNo(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setUpdateTime(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setStatus(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setUniversityId(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setCountry(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setEmail(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setAddress(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setContactName(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setDisabled(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DbGetStudent entity, long rowId) {
        entity.setDbId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DbGetStudent entity) {
        if(entity != null) {
            return entity.getDbId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DbGetStudent entity) {
        return entity.getDbId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
