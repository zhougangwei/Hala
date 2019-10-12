package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author wjy on 2019/10/11/011.
 */
public class MemberInfoBean extends BaseBean {
    /**
     * data : {"familyData":{"fid":1,"fname":"abc","leaderId":6,"mediaUrl":"http://starchat.general.7halachat.com/member_avatar_test.png","joinTime":"2019-09-19T11:22:41.000+0000"},"characterId":"08925802","mobileNumber":"+8615122220001","nickname":"test","gender":"male","birthDate":"2000-10-14","autograph":"?123","residentialPlace":"???","anchorId":2,"rongToken":"sm6ADusDTmrIhTYmXivermgPfwz4MZsem15zZgHcizS6wQxHd4hkc8YSLthfcy61y/KkdJBt5q5/aTia01YK+A==","coin":101040,"spent":0,"pea":180,"followingCount":3,"fansCount":2,"friendCount":2,"online":false,"joinfamilyAt":"2019-09-19T11:22:41.000+0000","leavefamilyAt":"2019-09-19T11:22:42.000+0000","album":[{"id":16,"mediaUrl":"http://starchat.anchor.7halachat.com/Screenshot_2019-08-13-10-58-51.png","sortby":0},{"id":17,"mediaUrl":"http://starchat.anchor.7halachat.com/Screenshot_2019-08-13-14-51-22.png","sortby":1}],"setting":{"videoNotify":true,"audioNotify":false,"chatNotify":false},"memberId":6,"createdAtDate":"2019-08-12","lastActiveMinuteGap":6,"genderOrdinal":1}
     */

    @SerializedName("data")
    private LoginBean.DataBean.MemberBean data;

    public LoginBean.DataBean.MemberBean getData() {
        return data;
    }

    public void setData(LoginBean.DataBean.MemberBean data) {
        this.data = data;
    }


}
