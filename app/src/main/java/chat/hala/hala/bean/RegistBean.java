package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/23 0023 22:16
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/23 0023$
 * @ 更新描述  ${TODO}
 */
public class RegistBean extends BaseBean {

    /**
     * data : {"characterId":"10486931","mobileNumber":"+8612345678910","username":"pp","gender":"male","birthDate":"2019-01-01","autograph":"不是","rongToken":"dck/4HVqHEl58hAUWXeZiV6AsbGff9KvUT62Noch30mb6n+CHOf4Vdoco+C/pVxohCVnUyo2V5Rc1gC7seANjA==","coin":0,"spent":0,"followingCount":0,"online":true,"album":[{"id":8,"mediaUrl":"http://starchat.general.7halachat.com/member_avatar_test.png","sortby":0}],"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwianRpIjoiNjljOTRlMzktNzIxMi00Y2JjLWIzODctZjc3ZTY2NDMxY2FlIn0.n6T5M64hKS7MC5djcuXYnNOQuCpoKiqTLJHL8qx9J3c","memberId":8,"lastActiveMinuteGap":0}
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
