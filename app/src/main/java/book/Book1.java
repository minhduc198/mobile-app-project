package book;

public class Book1 {
    private int resId;
    private String actionView;

    public Book1(int resId, String actionView) {
        this.resId = resId;
        this.actionView = actionView;
    }

    public int getResId() {
        return resId;
    }

    public String getActionView() {
        return actionView;
    }
}
