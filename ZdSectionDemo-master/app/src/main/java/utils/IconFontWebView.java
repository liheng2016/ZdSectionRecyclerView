package utils;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

/**
 * 名称：
 * Created by niudong on 2018/1/11.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class IconFontWebView extends WebView {
    private static final String DEFAULT_URL = "file:///android_asset/image.html";
    public IconFontWebView(Context context, String imageName, int withPx, int heightPx) {
        super(context);
        //生成html text
        String htmlString = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\"/>\n" +
                "    <title>IconFont</title>\n" +
                "    <link rel=\"stylesheet\" href=\"demo.css\">\n" +
                "    <script src=\"iconfont.js\"></script>\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "        .icon {\n" +
                "          /* 通过设置 font-size 来改变图标大小 */\n" +
                "          width: " + withPx + "; height: " + heightPx + ";\n" +
                "          /* 图标和文字相邻时，垂直对齐 */\n" +
                "          vertical-align: -0.15em;\n" +
                "          /* 通过设置 color 来改变 SVG 的颜色/fill */\n" +
                "          fill: currentColor;\n" +
                "          /* path 和 stroke 溢出 viewBox 部分在 IE 下会显示\n" +
                "             normalize.css 中也包含这行 */\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<svg class=\"icon\" aria-hidden=\"true\">\n" +
                "    <use xlink:href=\"" + imageName + "\"></use>\n" +
                "</svg>\n" +
                "</body>\n" +
                "</html>\n";

        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        if (null == layoutParams) {
            layoutParams = new ViewGroup.LayoutParams(withPx, heightPx);
        } else {
            layoutParams.width = withPx;
            layoutParams.height = heightPx;
        }
        this.loadDataWithBaseURL(DEFAULT_URL, htmlString, "text/html", "utf-8", null);
    }

}
