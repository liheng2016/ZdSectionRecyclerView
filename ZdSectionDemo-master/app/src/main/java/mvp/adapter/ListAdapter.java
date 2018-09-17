package mvp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import entity.ListEntity;
import utils.GlideManagerUtil;
import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/5/10.
 * Tel： 18811793194
 * VersionChange:2.0  XXX
 * List列表数据适配器Adapter
 */


public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ListEntity dataList;  //数据Entity
    private Context mContext;       //上下文

    /**
     * 构造参数
     *
     * @param data     网络请求服务器返回的数据
     * @param mContext
     */
    public ListAdapter(ListEntity data, Context mContext) {
        this.mContext = mContext;
        this.dataList = data;
    }

    /**
     * 更新数据，复用Adapter，adapter 不为空时调用
     *
     * @param data 网络请求服务器返回的数据
     */
    public void updateData(ListEntity data) {
        this.dataList = data;
        // 刷新数据
        notifyDataSetChanged();
    }

    //TODO 创建ViewHolder布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         加载 RecyclerView  item布局
         * */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_list, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    //  TODO  创建 ViewHolder 内部类
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_question;   //问题
        private TextView tv_answer;     //回答内容
        private TextView tv_approve;    //赞同
        private TextView tv_comment;    //评论
        private TextView tv_concern;    //关注
        private TextView tv_date;       //日期
        private ImageView imageView;    //发言者头像

        public ItemViewHolder(View itemView) {
            super(itemView);
            //TODO   自动适配Item
            // 初始化控件
            tv_question = (TextView) itemView.findViewById(R.id.tv_question);
            tv_answer = (TextView) itemView.findViewById(R.id.tv_answer);
            tv_approve = (TextView) itemView.findViewById(R.id.tv_approve);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_concern = (TextView) itemView.findViewById(R.id.tv_concern);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            imageView = (ImageView) itemView.findViewById(R.id.iv);

        }
    }

    /**
     * TODO 绑定控件---加载数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //instanceof用来判断内存中实际对象ItemViewHolder是不是RecyclerView.ViewHolder类型

        if (holder instanceof ItemViewHolder) {
            //问题Title 设置数据
            String questionTitle = dataList.getQuestionList().get(position).getQuestionTitle();
            if (questionTitle != null) {
                ((ItemViewHolder) holder).tv_question.setText(questionTitle);
            }

            //问题回答 设置数据 采用textView.append 追加Html数据方式实现图文混排
            String answerContent = dataList.getQuestionList().get(position).getAnswerContent();
            if (answerContent != null) {
                // 显示问题答案 answerContent
                separateString((ItemViewHolder) holder, answerContent);
            }

            // 点赞数量 设置数据
            String agreeCount = dataList.getQuestionList().get(position).getAgreeCount();

            if (agreeCount != null) {
                ((ItemViewHolder) holder).tv_approve.setText(("赞同 " + agreeCount));
                //TODO  采用String.format 拼接字符串
            }

            //评论数量  设置数据
            String commentCount = dataList.getQuestionList().get(position).getCommentCount();
            if (commentCount != null) {
                ((ItemViewHolder) holder).tv_comment.setText("评论 " + commentCount);
            }

            //问题关注数量 设置数据
            String attentionCount = dataList.getQuestionList().get(position).getAttentionCount();
            if (attentionCount != null) {
                ((ItemViewHolder) holder).tv_concern.setText("问题关注 " + attentionCount);
            }

            //imageUrl头像
            String userImageUrl = dataList.getQuestionList().get(position).getQuestionAvatar();
            if (userImageUrl != null) {
                //采用Glide 工具类加载  将服务器的矩形图片加载显示为圆形
                GlideManagerUtil.glideLoader(mContext, userImageUrl, R.mipmap.ic_launcher, R.mipmap.ic_launcher, ((ItemViewHolder) holder).imageView, 0);
            }

            //问题日期
            String questionDate = dataList.getQuestionList().get(position).getQuestionDate();
            if (questionDate != null) {
                ((ItemViewHolder) holder).tv_date.setText(questionDate);
            }
        }

        //添加条目点击事件
        ((ItemViewHolder) holder).tv_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * TODO 切割String 串，得到文字，图片和audio展示出来
     *
     * @param holder        传入该holder
     * @param answerContent 传入问题内容，已经判空处理
     */
    private void separateString(ItemViewHolder holder, String answerContent) {
        //TODO 1、切割掉该字符，返回数组
        String[] split = answerContent.split("\\<&>");

        //TODO 2、遍历数组
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            //TODO 3、 去除<$>
            String replace = s.replace("<$>", "");
            //TODO 4、获取第一个字符   均为 0,1,2
            String substring = replace.substring(0, 1);//1/0
            //TODO 4、开始判断   是0,1,2 ？
            if (substring.equals("0")) {
                // TODO  截取字符后面的串 1---replace.length()
                String resultWenZi = replace.substring(1, replace.length());
                holder.tv_answer.append(resultWenZi);

            } else if (substring.equals("1")) {
                // 把图片生成的ID加入img标签中 <img src='123'>

                String htmlForAudio = mContext.getString(R.string.spacing) + "<img src='" + R.drawable.ic_aq_audio + "'>" + mContext.getString(R.string.spacing);
                //追加Res图片数据
                holder.tv_answer.append(AddImage(htmlForAudio));

            } else if (substring.equals("2")) {
                String htmlForImage = mContext.getString(R.string.spacing) + "<img src='" + R.drawable.ic_aq_pic + "'>" + mContext.getString(R.string.spacing);
                holder.tv_answer.append(AddImage(htmlForImage));
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.getQuestionList().size();
    }


    /**
     * @param imageStr 本地图片 html
     * @return
     */
    private Spanned AddImage(String imageStr) {
        return Html.fromHtml(imageStr, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int id = Integer.parseInt(source);

                //通过id 获取资源目录下drawerable 图片
                Drawable drawable = mContext.getResources().getDrawable(id);

                // 写了图片才可以显示   drawable将在被绘制在canvas的哪个矩形区域内  39
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
        }, null);
    }
}
