package cn.com.hiss.www.multilib.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by junliang on 2017/2/24.
 */
@Entity
public class DbGetStudent implements Parcelable {
    @Id
    private Long dbId;
    @Convert(converter = StuImageConverter.class, columnType = String.class)
    private StuImage stuImage;    //学生图片相关
    @Convert(converter = StuInterestConverter.class, columnType = String.class)
    private StuInterest stuInterest; //爱好
    private String realName;    //姓名
    private String sex; //性别：男1，女2
    private String age; //年龄
    private String star; //星座
    private String birthDay; //生日
    private String introduction; //个性签名
    private String aboutSelf;//个人简介
    private String className; //班级
    private String universityName; //学校
    private String hometown; //家乡
    private String memberId;//登录用户ID
    private String imgUrl;    //背景图片
    /***********************************补充项******************************************/
    private String createTime;
    private String phone;
    private String minority;
    private String tel;
    private String password;
    private String city;
    private String sickName;
    private String facultyName;
    private String cardNo;
    private String token;
    private String motherId;
    private String fatherId;
    private String facultyClassId;
    private String loginName;
    private String contactPhone;
    private String studentNo;
    private String updateTime;
    private String status;
    private String universityId;
    private String country;
    private String email;
    private String address;
    private String contactName;
    private String disabled;

    protected DbGetStudent(Parcel in) {
        stuImage = in.readParcelable(StuImage.class.getClassLoader());
        stuInterest = in.readParcelable(StuInterest.class.getClassLoader());
        realName = in.readString();
        sex = in.readString();
        age = in.readString();
        star = in.readString();
        birthDay = in.readString();
        introduction = in.readString();
        aboutSelf = in.readString();
        className = in.readString();
        universityName = in.readString();
        hometown = in.readString();
        memberId = in.readString();
        imgUrl = in.readString();
        createTime = in.readString();
        phone = in.readString();
        minority = in.readString();
        tel = in.readString();
        password = in.readString();
        city = in.readString();
        sickName = in.readString();
        facultyName = in.readString();
        cardNo = in.readString();
        token = in.readString();
        motherId = in.readString();
        fatherId = in.readString();
        facultyClassId = in.readString();
        loginName = in.readString();
        contactPhone = in.readString();
        studentNo = in.readString();
        updateTime = in.readString();
        status = in.readString();
        universityId = in.readString();
        country = in.readString();
        email = in.readString();
        address = in.readString();
        contactName = in.readString();
        disabled = in.readString();
    }

    @Generated(hash = 1502687633)
    public DbGetStudent(Long dbId, StuImage stuImage, StuInterest stuInterest,
            String realName, String sex, String age, String star, String birthDay,
            String introduction, String aboutSelf, String className, String universityName,
            String hometown, String memberId, String imgUrl, String createTime, String phone,
            String minority, String tel, String password, String city, String sickName,
            String facultyName, String cardNo, String token, String motherId, String fatherId,
            String facultyClassId, String loginName, String contactPhone, String studentNo,
            String updateTime, String status, String universityId, String country,
            String email, String address, String contactName, String disabled) {
        this.dbId = dbId;
        this.stuImage = stuImage;
        this.stuInterest = stuInterest;
        this.realName = realName;
        this.sex = sex;
        this.age = age;
        this.star = star;
        this.birthDay = birthDay;
        this.introduction = introduction;
        this.aboutSelf = aboutSelf;
        this.className = className;
        this.universityName = universityName;
        this.hometown = hometown;
        this.memberId = memberId;
        this.imgUrl = imgUrl;
        this.createTime = createTime;
        this.phone = phone;
        this.minority = minority;
        this.tel = tel;
        this.password = password;
        this.city = city;
        this.sickName = sickName;
        this.facultyName = facultyName;
        this.cardNo = cardNo;
        this.token = token;
        this.motherId = motherId;
        this.fatherId = fatherId;
        this.facultyClassId = facultyClassId;
        this.loginName = loginName;
        this.contactPhone = contactPhone;
        this.studentNo = studentNo;
        this.updateTime = updateTime;
        this.status = status;
        this.universityId = universityId;
        this.country = country;
        this.email = email;
        this.address = address;
        this.contactName = contactName;
        this.disabled = disabled;
    }

    @Generated(hash = 1646094502)
    public DbGetStudent() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stuImage, flags);
        dest.writeParcelable(stuInterest, flags);
        dest.writeString(realName);
        dest.writeString(sex);
        dest.writeString(age);
        dest.writeString(star);
        dest.writeString(birthDay);
        dest.writeString(introduction);
        dest.writeString(aboutSelf);
        dest.writeString(className);
        dest.writeString(universityName);
        dest.writeString(hometown);
        dest.writeString(memberId);
        dest.writeString(imgUrl);
        dest.writeString(createTime);
        dest.writeString(phone);
        dest.writeString(minority);
        dest.writeString(tel);
        dest.writeString(password);
        dest.writeString(city);
        dest.writeString(sickName);
        dest.writeString(facultyName);
        dest.writeString(cardNo);
        dest.writeString(token);
        dest.writeString(motherId);
        dest.writeString(fatherId);
        dest.writeString(facultyClassId);
        dest.writeString(loginName);
        dest.writeString(contactPhone);
        dest.writeString(studentNo);
        dest.writeString(updateTime);
        dest.writeString(status);
        dest.writeString(universityId);
        dest.writeString(country);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(contactName);
        dest.writeString(disabled);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DbGetStudent> CREATOR = new Creator<DbGetStudent>() {
        @Override
        public DbGetStudent createFromParcel(Parcel in) {
            return new DbGetStudent(in);
        }

        @Override
        public DbGetStudent[] newArray(int size) {
            return new DbGetStudent[size];
        }
    };

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public StuImage getStuImage() {
        return stuImage;
    }

    public void setStuImage(StuImage stuImage) {
        this.stuImage = stuImage;
    }

    public StuInterest getStuInterest() {
        return stuInterest;
    }

    public void setStuInterest(StuInterest stuInterest) {
        this.stuInterest = stuInterest;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAboutSelf() {
        return aboutSelf;
    }

    public void setAboutSelf(String aboutSelf) {
        this.aboutSelf = aboutSelf;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMinority() {
        return minority;
    }

    public void setMinority(String minority) {
        this.minority = minority;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSickName() {
        return sickName;
    }

    public void setSickName(String sickName) {
        this.sickName = sickName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getFacultyClassId() {
        return facultyClassId;
    }

    public void setFacultyClassId(String facultyClassId) {
        this.facultyClassId = facultyClassId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }
}
