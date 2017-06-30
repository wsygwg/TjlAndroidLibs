package cn.com.hiss.www.multilib.hissviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.hiss.www.multilib.R;
import cn.com.hiss.www.multilib.utils.FontHelper;
import cn.com.hiss.www.multilib.utils.KeyBoardUtils;

/**
 * Created by wuyanzhe on 2017/3/17.
 */

public class HissSearchBar extends RelativeLayout {
    private RelativeLayout root;
    private TextView searchTv;
    private EditText contentEt;
    private TextView clearTv;
    private TextView cancelTv;
    private LinearLayout hintLo;
    private TextView hintIcon;
    private TextView hintTv;
    private OnSearchAction onSearchAction;

    private Context con;
    private String hintStr = "";

    public HissSearchBar(Context context) {
        super(context);
    }

    public HissSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        con = context;
        View inflater = LayoutInflater.from(context).inflate(R.layout.layout_hiss_search_bar, this, true);
        root = (RelativeLayout) inflater.findViewById(R.id.id_searchbar_root);
        root.setFocusable(true);
        searchTv = (TextView) inflater.findViewById(R.id.id_searchbar_search);
        contentEt = (EditText) inflater.findViewById(R.id.id_searchbar_et);
        contentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent event) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    KeyBoardUtils.hideKeyBoard(con, contentEt);
                    if (onSearchAction != null) {
                        onSearchAction.onSearch();
                    }
                    return true;
                }
                return false;
            }
        });
        contentEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    KeyBoardUtils.showKeyBoard(con, contentEt);
                    hintLo.setVisibility(GONE);
                    contentEt.setHint(hintStr);
                    searchTv.setVisibility(VISIBLE);
                    clearTv.setVisibility(VISIBLE);
                    cancelTv.setVisibility(VISIBLE);
                    if (onSearchAction != null) {
                        onSearchAction.onFocused();
                    }
                } else {
                    if (onSearchAction != null) {
                        onSearchAction.onLoseFocus();
                    }
                }
            }
        });
        clearTv = (TextView) inflater.findViewById(R.id.id_searchbar_clear);
        clearTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                contentEt.setText("");
            }
        });
        cancelTv = (TextView) inflater.findViewById(R.id.id_searchbar_cancel);
        cancelTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                if (onSearchAction != null) {
                    onSearchAction.onCancel();
                }
            }
        });
        hintLo = (LinearLayout) inflater.findViewById(R.id.id_searchbar_hint_lo);

        hintIcon = (TextView) inflater.findViewById(R.id.id_searchbar_hint_icon);
        hintTv = (TextView) inflater.findViewById(R.id.id_searchbar_hint_tv);

        initViewsVisibility();
        hintStr = hintTv.getText().toString();

        FontHelper.injectFont(searchTv);
        FontHelper.injectFont(clearTv);
        FontHelper.injectFont(hintIcon);
    }

    private void initViewsVisibility() {
        hintLo.setVisibility(VISIBLE);
        searchTv.setVisibility(GONE);
        clearTv.setVisibility(GONE);
        cancelTv.setVisibility(GONE);
    }

    public HissSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditText getEditText() {
        return contentEt;
    }

    public void setHintText(String text) {
        hintTv.setText(text);
        hintStr = text;
    }

    private void reset() {
        contentEt.clearFocus();
        hintLo.setVisibility(VISIBLE);
        contentEt.setHint("");
        contentEt.setText("");
        searchTv.setVisibility(GONE);
        clearTv.setVisibility(GONE);
        cancelTv.setVisibility(GONE);
        KeyBoardUtils.hideKeyBoard(con, contentEt);
    }

    public interface OnSearchAction {
        void onSearch();

        void onCancel();

        void onFocused();

        void onLoseFocus();
    }

    public void setOnSearchAction(OnSearchAction onSearchAction) {
        this.onSearchAction = onSearchAction;
    }

    public void resetAction() {
        cancelTv.performClick();
    }
}
