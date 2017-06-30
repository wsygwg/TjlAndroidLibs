package cn.com.hiss.www.multilib.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import cn.com.hiss.www.multilib.R;
import cn.com.hiss.www.multilib.db.DbGetChatGroupMembers;

/**
 * Created by junliang on 2017/2/23.
 */

public class PicDecorator {

    private static final int ROUND_CORNER_RATE = 5;
    private static final int LOAD_PIC = R.drawable.hiss_pic_placeholder;
    private static final int ERROR_PIC = R.drawable.hiss_pic_errorholder;

    public static final String NUMBER_BOY = "1";
    public static final String NUMBER_GIRL = "2";

    public enum Gender {
        Boy, Girl
    }

    public synchronized static void decorateImageView(Context con, int errorPic, ImageView iv, Object fileUrlResource) {
        try {
            int errorHolder = ERROR_PIC;
            if (errorPic > 0) {
                errorHolder = errorPic;
            }

            if (fileUrlResource instanceof Integer) {
                //resource资源
                Picasso.with(con).load((Integer) fileUrlResource).error(errorHolder).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
                //            Picasso.with(con).load((Integer) fileUrlResource).error(errorHolder).transform(new HissTransformation(iv)).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
            } else if (fileUrlResource instanceof File) {
                //文件
                Picasso.with(con).load((File) fileUrlResource).error(errorHolder).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
            } else if (fileUrlResource instanceof String) {
                String url = (String) fileUrlResource;
                if (Schecker.isStringNull(url)) {
                    Picasso.with(con).load(errorHolder).error(errorHolder).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
                } else {
                    Picasso.with(con).load(url).error(errorHolder).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static void roundCornerPic(Context con, int errorPic, ImageView iv, Object fileUrlResource) {
        try {
            int errorHolder = ERROR_PIC;
            if (errorPic > 0) {
                errorHolder = errorPic;
            }
            //        Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.BLACK).borderWidthDp(0).cornerRadiusDp(30).oval(false).build();
            if (fileUrlResource instanceof Integer) {
                //resource资源
                Picasso.with(con).load((Integer) fileUrlResource).error(errorHolder).transform(new HissTransformation2(iv)).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
            } else if (fileUrlResource instanceof File) {
                //文件
                Picasso.with(con).load((File) fileUrlResource).error(errorHolder).transform(new HissTransformation2(iv)).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
            } else if (fileUrlResource instanceof String) {
                String url = (String) fileUrlResource;
                if (Schecker.isStringNull(url)) {
                    Picasso.with(con).load(errorHolder).error(errorHolder).transform(new HissTransformation2(iv)).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
                } else {
                    Picasso.with(con).load(url).error(errorHolder).transform(new HissTransformation2(iv)).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(iv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static void glideDecorateImageView(Context con, int errorPic, ImageView iv, Object fileUrlResource) {
        try {
            int errorHolder = ERROR_PIC;
            if (errorPic > 0) {
                errorHolder = errorPic;
            }
            if(fileUrlResource == null){
                Glide.with(con).load(errorHolder).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
            }else if (fileUrlResource instanceof Integer) {
                //resource资源
                Glide.with(con).load((Integer) fileUrlResource).placeholder(LOAD_PIC).error(errorHolder).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
            } else if (fileUrlResource instanceof File) {
                //文件
                Glide.with(con).load((File) fileUrlResource).placeholder(LOAD_PIC).error(errorHolder).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
            } else if (fileUrlResource instanceof String) {
                String url = (String) fileUrlResource;
                if (Schecker.isStringNull(url)) {
                    Glide.with(con).load(errorHolder).placeholder(LOAD_PIC).error(errorHolder).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
                } else {
                    Glide.with(con).load(url).placeholder(LOAD_PIC).error(errorHolder).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static void glideRoundCornerPic(Context con, int errorPic, ImageView iv, Object fileUrlResource) {
        try {
            int errorHolder = ERROR_PIC;
            if (errorPic > 0) {
                errorHolder = errorPic;
            }
            if(fileUrlResource == null){
                Glide.with(con).load(errorHolder).dontAnimate().bitmapTransform(new GlideRoundTransform(con, ROUND_CORNER_RATE)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
            }else if (fileUrlResource instanceof Integer) {
                //resource资源
                Glide.with(con).load((Integer) fileUrlResource).placeholder(LOAD_PIC).error(errorHolder).dontAnimate().bitmapTransform(new GlideRoundTransform(con, ROUND_CORNER_RATE)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
            } else if (fileUrlResource instanceof File) {
                //文件
                //            Glide.with(con).load((File) fileUrlResource).placeholder(LOAD_PIC).error(errorHolder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
                Glide.with(con).load((File) fileUrlResource).placeholder(LOAD_PIC).error(errorHolder).dontAnimate().bitmapTransform(new GlideRoundTransform(con, ROUND_CORNER_RATE)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
            } else if (fileUrlResource instanceof String) {
                String url = (String) fileUrlResource;
                if (Schecker.isStringNull(url)) {
                    Glide.with(con).load(errorHolder).placeholder(LOAD_PIC).error(errorHolder).dontAnimate().bitmapTransform(new GlideRoundTransform(con, ROUND_CORNER_RATE)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
                } else {
                    Glide.with(con).load(url).placeholder(LOAD_PIC).error(errorHolder).dontAnimate().bitmapTransform(new GlideRoundTransform(con, ROUND_CORNER_RATE)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static int getMemberDefaultErrorPic(DbGetChatGroupMembers member) {
        int errorHolder = R.drawable.hiss_pic_errorholder;
        try{
            Gender gender = member.getSex().equals("1") ? Gender.Boy : Gender.Girl;
            if (gender == Gender.Boy) {
                errorHolder = R.drawable.img_avatar_default_boy;
            } else {
                errorHolder = R.drawable.img_avatar_default_girl;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return errorHolder;
    }

    public synchronized static int getMemberDefaultErrorPic(Gender gender) {
        int errorHolder;
        if (gender == Gender.Boy) {
            errorHolder = R.drawable.img_avatar_default_boy;
        } else {
            errorHolder = R.drawable.img_avatar_default_girl;
        }
        return errorHolder;
    }

    public synchronized static int getMemberDefaultErrorPic(String sex) {
        Gender gender = sex.equals(PicDecorator.NUMBER_BOY) ? PicDecorator.Gender.Boy : PicDecorator.Gender.Girl;
        int errorHolder;
        if (gender == Gender.Boy) {
            errorHolder = R.drawable.img_avatar_default_boy;
        } else {
            errorHolder = R.drawable.img_avatar_default_girl;
        }
        return errorHolder;
    }
}
