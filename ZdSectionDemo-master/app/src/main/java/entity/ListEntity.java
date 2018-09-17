package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by niudong on 2017/5/9.
 * <p>
 * 数据的实体
 */


public class ListEntity implements Serializable {


    private String responseCode;
    private String responseDes;

    public String getResponseDes() {
        return responseDes;
    }

    public void setResponseDes(String responseDes) {
        this.responseDes = responseDes;
    }

    /**
     * questionId : 363
     * questionTitle : 233232
     * questionContent : 0<$>
     * answerCount : 0
     * attentionCount : 1
     * questionUserId : 300096
     * questionUserMobile : 18211072521
     * questionNickName : 咔咔
     * questionAvatar : http://obb9dnp29.bkt.clouddn.com/b5feb7ae9399abca8e39e97ac12e1a8e6af7e5ca   .png?e=2122795224&token=oJwWDNz1gWY4Xd9Ybv4lirWY6V4Kco_0jifhorKO:rKVi1ds_G99sXxZ-a9A0pmIB0XI=
     * questionDate : 05-03
     */

    private List<QuestionListBean> questionList;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<QuestionListBean> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionListBean> questionList) {
        this.questionList = questionList;
    }

    public static class QuestionListBean {
        private String questionId;
        private String questionTitle;
        private String questionContent;
        private String answerCount;
        private String answerContent;

        private String attentionCount;
        private String questionUserId;
        private String questionUserMobile;
        private String questionNickName;
        private String questionAvatar;
        private String questionDate;
        private String agreeCount;
        private String commentCount;
        private String answerUserId;
        private String answerUserMobile;
        private String answerNickName;

        public String getAnswerContent() {
            return answerContent;
        }

        public void setAnswerContent(String answerContent) {
            this.answerContent = answerContent;
        }

        public String getAgreeCount() {
            return agreeCount;
        }

        public void setAgreeCount(String agreeCount) {
            this.agreeCount = agreeCount;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public String getAnswerUserId() {
            return answerUserId;
        }

        public void setAnswerUserId(String answerUserId) {
            this.answerUserId = answerUserId;
        }

        public String getAnswerUserMobile() {
            return answerUserMobile;
        }

        public void setAnswerUserMobile(String answerUserMobile) {
            this.answerUserMobile = answerUserMobile;
        }

        public String getAnswerNickName() {
            return answerNickName;
        }

        public void setAnswerNickName(String answerNickName) {
            this.answerNickName = answerNickName;
        }

        public String getAnswerAvatar() {
            return answerAvatar;
        }

        public void setAnswerAvatar(String answerAvatar) {
            this.answerAvatar = answerAvatar;
        }

        private String answerAvatar;

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getQuestionTitle() {
            return questionTitle;
        }

        public void setQuestionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
        }

        public String getQuestionContent() {
            return questionContent;
        }

        public void setQuestionContent(String questionContent) {
            this.questionContent = questionContent;
        }

        public String getAnswerCount() {
            return answerCount;
        }

        public void setAnswerCount(String answerCount) {
            this.answerCount = answerCount;
        }

        public String getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(String attentionCount) {
            this.attentionCount = attentionCount;
        }

        public String getQuestionUserId() {
            return questionUserId;
        }

        public void setQuestionUserId(String questionUserId) {
            this.questionUserId = questionUserId;
        }

        public String getQuestionUserMobile() {
            return questionUserMobile;
        }

        public void setQuestionUserMobile(String questionUserMobile) {
            this.questionUserMobile = questionUserMobile;
        }

        public String getQuestionNickName() {
            return questionNickName;
        }

        public void setQuestionNickName(String questionNickName) {
            this.questionNickName = questionNickName;
        }

        public String getQuestionAvatar() {
            return questionAvatar;
        }

        public void setQuestionAvatar(String questionAvatar) {
            this.questionAvatar = questionAvatar;
        }

        public String getQuestionDate() {
            return questionDate;
        }

        public void setQuestionDate(String questionDate) {
            this.questionDate = questionDate;
        }
    }
}
