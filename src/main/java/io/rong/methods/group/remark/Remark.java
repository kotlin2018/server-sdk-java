package io.rong.methods.group.remark;

import io.rong.RongCloud;
import io.rong.models.CheckMethod;
import io.rong.models.Result;
import io.rong.models.response.GetUserRemarksResult;
import io.rong.models.response.GroupRemarkModel;
import io.rong.models.response.ResponseResult;
import io.rong.util.CommonUtil;
import io.rong.util.GsonUtil;
import io.rong.util.HttpUtil;

import java.net.HttpURLConnection;

/**
 * 群组备注服务
 */
public class Remark {

    private static final String UTF8 = "UTF-8";
    private static final String PATH = "group/remark";
    private String appKey;
    private String appSecret;
    private RongCloud rongCloud;

    public RongCloud getRongCloud() {
        return rongCloud;
    }

    public void setRongCloud(RongCloud rongCloud) {
        this.rongCloud = rongCloud;
    }

    public Remark(String appKey, String appSecret, RongCloud rongCloud) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.rongCloud  = rongCloud;

    }

    /**
     * 为应用中的群组添加备注，如果某群组已经添加了备注，再次对群组添加备注时将覆盖之前设置的备注内容。
     *
     * @param remark 群组备注信息
     * @return ResponseResult
     **/
    public ResponseResult set(String userId,String groupId, String remark) throws Exception {
        String message = CommonUtil.checkParam("id", groupId, PATH, CheckMethod.SET);
        if (null != message) {
            return (ResponseResult) GsonUtil.fromJson(message, ResponseResult.class);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("userId="+ userId);
        sb.append("&groupId="+ groupId);
        sb.append("&remark="+ remark);
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(rongCloud.getConfig(), appKey, appSecret,
                "/group/remarks/set.json");

        HttpUtil.setBodyParameter(sb.toString(), conn, rongCloud.getConfig());
        return (ResponseResult) GsonUtil.fromJson(
                CommonUtil.getResponseByCode(PATH, CheckMethod.SET, HttpUtil.returnResult(conn, rongCloud.getConfig())),
                ResponseResult.class);
    }

    /**
     * 删除群组备注
     *
     * @param groupId 删除群组备注
     * @return ResponseResult
     **/
    public ResponseResult del(String userId, String groupId) throws Exception {
        String message = CommonUtil.checkParam("id", userId, PATH, CheckMethod.SET);
        if (null != message) {
            return (ResponseResult) GsonUtil.fromJson(message, ResponseResult.class);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("userId="+ userId);
        sb.append("&groupId="+ groupId);
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(rongCloud.getConfig(), appKey, appSecret,
                "/group/remarks/del.json");

        HttpUtil.setBodyParameter(sb.toString(), conn, rongCloud.getConfig());
        return (ResponseResult) GsonUtil.fromJson(
                CommonUtil.getResponseByCode(PATH, CheckMethod.DEL, HttpUtil.returnResult(conn, rongCloud.getConfig())),
                ResponseResult.class);
    }


    /**
     * 查询群组备注功能
     *
     * @param userId
     * @return GetTagResult
     **/
    public GroupRemarkModel get(String userId, String groupId) throws Exception {
        String message = CommonUtil.checkParam("id",groupId, PATH, CheckMethod.SET);
        if (null != message) {
            return (GroupRemarkModel) GsonUtil.fromJson(message, GroupRemarkModel.class);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("userId="+userId);
        sb.append("&groupId="+groupId);


        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(rongCloud.getConfig(), appKey, appSecret,
                "/group/remarks/get.json");
        HttpUtil.setBodyParameter(sb.toString(), conn, rongCloud.getConfig());
        return (GroupRemarkModel) GsonUtil.fromJson(
                CommonUtil.getResponseByCode(PATH, CheckMethod.GET, HttpUtil.returnResult(conn, rongCloud.getConfig())),
                GroupRemarkModel.class);
    }

}