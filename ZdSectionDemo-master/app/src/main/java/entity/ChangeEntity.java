package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by niudong on 2017/7/4.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */


public class ChangeEntity implements Serializable {


    /**
     * id : 2001707040000081691
     * object : order
     * created : 1499161656
     * livemode : false
     * status : created
     * paid : false
     * refunded : false
     * app : app_PGOqTSGeX58C4uf9
     * uid : 18811793194
     * merchantOrderNo : 20171499161656434
     * type : purchase
     * amount : 10
     * couponAmount : 0
     * balanceAmount : 0
     * chargeAmount : 10
     * amountRefunded : 0
     * currency : cny
     * subject : orderTest
     * body : orderDes
     * clientIp : 10.0.10.169
     * timeExpire : 1499248056
     * refunds : {"object":"list","url":"/v1/orders/2001707040000081691/refunds","hasMore":false,"data":[]}
     * charge : ch_GuTq9K4GmLaTPWP000WDSSC0
     * metadata : {}
     * chargeEssentials : {"channel":"alipay","credential":{"object":"credential","alipay":{"orderInfo":"_input_charset=\"utf-8\"&body=\"orderDes\"&it_b_pay=\"2017-07-05 17:47:36\"&notify_url=\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Forders%2F2001707040000081691%2Fcharges%2Fch_GuTq9K4GmLaTPWP000WDSSC0\"&out_trade_no=\"20171499161656434\"&partner=\"2008539157384721\"&payment_type=\"1\"&seller_id=\"2008539157384721\"&view.niudong.com.demo.service=\"mobile.securitypay.pay\"&subject=\"orderTest\"&total_fee=\"0.10\"&sign=\"cWZmSGFQbW5EYVA4T1dMbTk0alBTQ084\"&sign_type=\"RSA\""}},"extra":{"disable_pay_channels":"credit_group,point","need_buyer_real_named":"F"}}
     * availableBalance : 0
     * userFee : 0
     * extraAmount : 0
     * receiptApp : app_PGOqTSGeX58C4uf9
     * serviceApp : app_PGOqTSGeX58C4uf9
     * availableMethods : ["balance","alipay"]
     */

    private String id;
    private String object;
    private int created;
    private boolean livemode;
    private String status;
    private boolean paid;
    private boolean refunded;
    private String app;
    private String uid;
    private String merchantOrderNo;
    private String type;
    private int amount;
    private int couponAmount;
    private int balanceAmount;
    private int chargeAmount;
    private int amountRefunded;
    private String currency;
    private String subject;
    private String body;
    private String clientIp;
    private int timeExpire;
    /**
     * object : list
     * url : /v1/orders/2001707040000081691/refunds
     * hasMore : false
     * data : []
     */

    private RefundsBean refunds;
    private String charge;
    private MetadataBean metadata;
    /**
     * channel : alipay
     * credential : {"object":"credential","alipay":{"orderInfo":"_input_charset=\"utf-8\"&body=\"orderDes\"&it_b_pay=\"2017-07-05 17:47:36\"&notify_url=\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Forders%2F2001707040000081691%2Fcharges%2Fch_GuTq9K4GmLaTPWP000WDSSC0\"&out_trade_no=\"20171499161656434\"&partner=\"2008539157384721\"&payment_type=\"1\"&seller_id=\"2008539157384721\"&view.niudong.com.demo.service=\"mobile.securitypay.pay\"&subject=\"orderTest\"&total_fee=\"0.10\"&sign=\"cWZmSGFQbW5EYVA4T1dMbTk0alBTQ084\"&sign_type=\"RSA\""}}
     * extra : {"disable_pay_channels":"credit_group,point","need_buyer_real_named":"F"}
     */

    private ChargeEssentialsBean chargeEssentials;
    private int availableBalance;
    private int userFee;
    private int extraAmount;
    private String receiptApp;
    private String serviceApp;
    private List<String> availableMethods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(int balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public int getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(int chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public int getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(int amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(int timeExpire) {
        this.timeExpire = timeExpire;
    }

    public RefundsBean getRefunds() {
        return refunds;
    }

    public void setRefunds(RefundsBean refunds) {
        this.refunds = refunds;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public ChargeEssentialsBean getChargeEssentials() {
        return chargeEssentials;
    }

    public void setChargeEssentials(ChargeEssentialsBean chargeEssentials) {
        this.chargeEssentials = chargeEssentials;
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(int availableBalance) {
        this.availableBalance = availableBalance;
    }

    public int getUserFee() {
        return userFee;
    }

    public void setUserFee(int userFee) {
        this.userFee = userFee;
    }

    public int getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(int extraAmount) {
        this.extraAmount = extraAmount;
    }

    public String getReceiptApp() {
        return receiptApp;
    }

    public void setReceiptApp(String receiptApp) {
        this.receiptApp = receiptApp;
    }

    public String getServiceApp() {
        return serviceApp;
    }

    public void setServiceApp(String serviceApp) {
        this.serviceApp = serviceApp;
    }

    public List<String> getAvailableMethods() {
        return availableMethods;
    }

    public void setAvailableMethods(List<String> availableMethods) {
        this.availableMethods = availableMethods;
    }

    public static class RefundsBean {
        private String object;
        private String url;
        private boolean hasMore;
        private List<?> data;

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }

    public static class MetadataBean {
    }

    public static class ChargeEssentialsBean {
        private String channel;
        /**
         * object : credential
         * alipay : {"orderInfo":"_input_charset=\"utf-8\"&body=\"orderDes\"&it_b_pay=\"2017-07-05 17:47:36\"&notify_url=\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Forders%2F2001707040000081691%2Fcharges%2Fch_GuTq9K4GmLaTPWP000WDSSC0\"&out_trade_no=\"20171499161656434\"&partner=\"2008539157384721\"&payment_type=\"1\"&seller_id=\"2008539157384721\"&view.niudong.com.demo.service=\"mobile.securitypay.pay\"&subject=\"orderTest\"&total_fee=\"0.10\"&sign=\"cWZmSGFQbW5EYVA4T1dMbTk0alBTQ084\"&sign_type=\"RSA\""}
         */

        private CredentialBean credential;
        /**
         * disable_pay_channels : credit_group,point
         * need_buyer_real_named : F
         */

        private ExtraBean extra;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public CredentialBean getCredential() {
            return credential;
        }

        public void setCredential(CredentialBean credential) {
            this.credential = credential;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public static class CredentialBean {
            private String object;
            /**
             * orderInfo : _input_charset="utf-8"&body="orderDes"&it_b_pay="2017-07-05 17:47:36"&notify_url="https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Forders%2F2001707040000081691%2Fcharges%2Fch_GuTq9K4GmLaTPWP000WDSSC0"&out_trade_no="20171499161656434"&partner="2008539157384721"&payment_type="1"&seller_id="2008539157384721"&view.niudong.com.demo.service="mobile.securitypay.pay"&subject="orderTest"&total_fee="0.10"&sign="cWZmSGFQbW5EYVA4T1dMbTk0alBTQ084"&sign_type="RSA"
             */

            private AlipayBean alipay;

            public String getObject() {
                return object;
            }

            public void setObject(String object) {
                this.object = object;
            }

            public AlipayBean getAlipay() {
                return alipay;
            }

            public void setAlipay(AlipayBean alipay) {
                this.alipay = alipay;
            }

            public static class AlipayBean {
                private String orderInfo;

                public String getOrderInfo() {
                    return orderInfo;
                }

                public void setOrderInfo(String orderInfo) {
                    this.orderInfo = orderInfo;
                }
            }
        }

        public static class ExtraBean {
            private String disable_pay_channels;
            private String need_buyer_real_named;

            public String getDisable_pay_channels() {
                return disable_pay_channels;
            }

            public void setDisable_pay_channels(String disable_pay_channels) {
                this.disable_pay_channels = disable_pay_channels;
            }

            public String getNeed_buyer_real_named() {
                return need_buyer_real_named;
            }

            public void setNeed_buyer_real_named(String need_buyer_real_named) {
                this.need_buyer_real_named = need_buyer_real_named;
            }
        }
    }
}
