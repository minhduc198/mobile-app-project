package com.mobile.openlibraryapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class HeaderActivity extends AppCompatActivity {

    // Vẫn giữ dispatchTouchEvent của bạn
    private static DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ẩn status bar (giữ như bạn)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.header_activity);

        // Ánh xạ DrawerLayout (bắt buộc)
        drawerLayout = findViewById(R.id.drawerLayout);

        // ------------- Thiết lập kích thước drawer là 75% màn hình -------------
        // Lấy view rightDrawer (include)
        View rightDrawer = findViewById(R.id.rightDrawer);
        if (rightDrawer != null) {
            // Tính 75% chiều rộng màn hình
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int width70 = (int) (dm.widthPixels * 0.70f);

            // Thay đổi layout params của rightDrawer
            ViewGroup.LayoutParams lp = rightDrawer.getLayoutParams();
            lp.width = width70;
            rightDrawer.setLayoutParams(lp);
        }

        // ------------- Thiết lập màu overlay (scrim) khi drawer mở -------------
        // Bạn có thể chỉnh alpha/color ở đây
        if (drawerLayout != null) {
            // scrim màu đen mờ
            drawerLayout.setScrimColor(Color.parseColor("#66000000")); // ~40% đen
        }

        // Nếu muốn load fragments programmatically, bạn có thể làm ở đây.
        // (Bạn trước đó đang dùng <fragment> trong XML để đặt HeaderFragment & NavFragment,
        // nếu đã có thì không cần replace ở đây.)
    }

    // Hàm public static để HeaderFragment gọi (giữ nguyên cách bạn dùng)
    public static void openRightMenu() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }

    public static void closeRightMenu() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    // ---------- giữ nguyên dispatchTouchEvent như bạn yêu cầu ----------
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null && v instanceof android.widget.EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);

                // Nếu bấm ra ngoài EditText
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();

                    // Ẩn bàn phím
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
