package chat.hala.hala.avchat;

import android.content.Context;
import android.text.TextUtils;

import chat.hala.hala.base.App;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.SPUtil;

public class AvchatInfo {

    private static String name;
    private static String RTMToken;
    private static boolean sIsInCall;
    private static String sMediaToken;
    private static String sCallText;
    private static LoginBean.DataBean.MemberBean memberBean;


    public static boolean isLogin(){
        return SPUtil.getInstance(App.sContext).getUserId()!=0;
    }


    public static void setMemberBean(LoginBean.DataBean.MemberBean memberBean) {
        AvchatInfo.memberBean = memberBean;
    }

    public static LoginBean.DataBean.MemberBean getMemberBean() {
        return memberBean;
    }


    public static int getAccount() {
        if(judgeMemberEmpty()){
            return 0;
        }
        return memberBean.getMemberId();
    }
    public static boolean hasFamily(){
        return memberBean.getFamilyData()!=null&&memberBean.getFamilyData().getFid()!=0;
    }
    public static boolean isFamilyLeader(){
        return memberBean!=null&& memberBean.getFamilyData()!=null&&memberBean.getFamilyData().getLeaderId()==memberBean.getMemberId();
    }


    public static void setAccount(int account) {
        if(judgeMemberEmpty()){
            return ;
        }
        memberBean.setMemberId(account);
        saveBaseData(memberBean);
    }

    public static String getName() {
        if(judgeMemberEmpty()){
            return "";
        }
        return memberBean.getUsername();
    }

    public static void setName(String name) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.setUsername(name);
        saveBaseData(memberBean);
    }

    public static int getCoin() {
        if(judgeMemberEmpty()){
            return 0;
        }
        return memberBean.getCoin();
    }

    public static void setCoin(int coin) {
        if(judgeMemberEmpty()){
            return ;
        }
        memberBean.setCoin(coin);
        saveBaseData(memberBean);
    }

    public static void setRTMToken(String RTMToken) {
        AvchatInfo.RTMToken = RTMToken;
    }

    public static String getRTMToken() {
        return RTMToken;
    }

    public static String getMediaToken() {
        return sMediaToken;
    }

    public static void setMediaToken(String mediaToken) {
        sMediaToken = mediaToken;
    }

    public static void setIsInCall(boolean isInCall) {
        sIsInCall = isInCall;
    }

    public static boolean isIsInCall() {
        return sIsInCall;
    }

    public static void saveBaseData(LoginBean.DataBean.MemberBean member, Context context, boolean whileSaveSp) {
        String memeberJson = GsonUtil.parseObjectToJson(member);
        AvchatInfo.setMemberBean(member);
        if (whileSaveSp) {
            String accessToken = member.getAccessToken();
            String rongToken = member.getRongToken();
            if(!TextUtils.isEmpty(accessToken)){
                SPUtil.getInstance(context).setString(Contact.TOKEN, accessToken);
            }
            if(!TextUtils.isEmpty(rongToken)){
                SPUtil.getInstance(context).setString(Contact.RONG_TOKEN, rongToken);
            }
            SPUtil.getInstance(context).setUserId(member.getMemberId());
            SPUtil.getInstance(context).setAnchorId(member.getAnchorId());
            SPUtil.getInstance(context).setMemberJson(memeberJson);
        }
    }
    public static void saveBaseData(LoginBean.DataBean.MemberBean member) {
        String memeberJson = GsonUtil.parseObjectToJson(member);
        AvchatInfo.setMemberBean(member);
        SPUtil.getInstance(App.sContext).setMemberJson(memeberJson);
    }



    public static void clearBaseData(Context context) {
        AvchatInfo.setMemberBean(null);
        SPUtil.getInstance(context).setString(Contact.TOKEN, "");
        SPUtil.getInstance(context).setString(Contact.RONG_TOKEN, "");
        SPUtil.getInstance(context).setUserId(0);
        SPUtil.getInstance(context).setAnchorId(0);
        SPUtil.getInstance(context).setMemberJson("");
    }



    public static void setAnchorId(int anchorId) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.setAnchorId(anchorId);
        saveBaseData(memberBean);
    }

    public static int getAnchorId() {
        if(judgeMemberEmpty()){
            return 0;
        }
        return memberBean.getAnchorId();
    }

    public static boolean isAnchor() {
        if(judgeMemberEmpty()){
            return false;
        }
        return  memberBean.getAnchorId() != 0;
    }


    public static void setAvatarUrl(String avatarUrl) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.getAlbum().get(0).setMediaUrl(avatarUrl);
        saveBaseData(memberBean);

    }

    public static String getAvatarUrl() {
        if(judgeMemberEmpty()){
            return "";
        }
        return  memberBean.getAlbum().get(0).getMediaUrl();
    }


    public static void setCallText(String callText) {
        sCallText = callText;
    }

    public static String getCallText() {
        return sCallText;
    }



    public static void setGender(int gender) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.setGenderOrdinal(gender);
        saveBaseData(memberBean);

    }

    public static int getGender() {
        if(judgeMemberEmpty()){
            return 1;
        }
        return memberBean.getGenderOrdinal();
    }

    public static void setBirthDate(String birthDate) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.setBirthDate(birthDate) ;
        saveBaseData(memberBean);
    }

    public static String getBirthDate() {
        if(judgeMemberEmpty()){
            return "";
        }
        return memberBean.getBirthDate();
    }

    public static void setMemberId(int memberId) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.setMemberId(memberId);
        saveBaseData(memberBean);

    }

    public static int getMemberId() {
        if(judgeMemberEmpty()){
            return -1;
        }
        return memberBean.getMemberId();

    }

    public static void setResidentialPlace(String residentialPlace) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.setResidentialPlace(residentialPlace);
        saveBaseData(memberBean);
    }

    public static String getResidentialPlace() {
        if(judgeMemberEmpty()){
            return "";
        }
        return memberBean.getResidentialPlace();
    }

    public static void setAutoGraph(String autoGraph) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.setAutograph(autoGraph);
        saveBaseData(memberBean);
    }

    public static String getAutoGraph() {
        if(judgeMemberEmpty()){
            return "";
        }
        return memberBean.getAutograph();
    }

    public static String getIntroduction() {
        if(judgeMemberEmpty()){
            return "";
        }
        return memberBean.getIntroduction();
    }





    private static boolean judgeMemberEmpty() {
        return getMemberBean()==null;
    }

    public static void setIntroduction(String introduction) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.setIntroduction(introduction);
        saveBaseData(memberBean);
    }


    public static void setAudioCpm(String audioCpm) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.getSetting().setAudioCpm(Integer.parseInt(audioCpm));
        saveBaseData(memberBean);
    }

    public static void setVideoCpm(String videoCpm) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.getSetting().setVideoCpm(Integer.parseInt(videoCpm));
        saveBaseData(memberBean);
    }

    public static void setChatCpm(String chatCmp) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.getSetting().setChatCpm(Integer.parseInt(chatCmp));
        saveBaseData(memberBean);
    }

    public static void setVideoNotify(boolean mVideoOpen) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.getSetting().setVideoNotify(mVideoOpen);
        saveBaseData(memberBean);
    }

    public static void setAudioNotify(boolean mVoiceOpen) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.getSetting().setAudioNotify(mVoiceOpen);
        saveBaseData(memberBean);
    }

    public static void setChatNotify(boolean mChatOpen) {
        if(judgeMemberEmpty()){
            return;
        }
        memberBean.getSetting().setChatNotify(mChatOpen);
        saveBaseData(memberBean);
    }


    public static int getAudioCpm() {
        if(judgeMemberEmpty()){
            return 0;
        }
       return memberBean.getSetting()==null?0:memberBean.getSetting().getAudioCpm();
    }

    public static int getVideoCpm() {
        if(judgeMemberEmpty()){
            return 0;
        }
        return memberBean.getSetting()==null?0:memberBean.getSetting().getVideoCpm();
    }
    public static int getChatCpm() {
        if(judgeMemberEmpty()){
            return 0;
        }
        return memberBean.getSetting()==null?0:memberBean.getSetting().getChatCpm();
    }

    public static boolean getVideoNotify() {
        if(judgeMemberEmpty()){
            return true;
        }
       return memberBean.getSetting()==null?true:memberBean.getSetting().isVideoNotify();
    }

    public static boolean getAudioNotify() {
        if(judgeMemberEmpty()){
            return true;
        }
        return memberBean.getSetting()==null?true:memberBean.getSetting().isAudioNotify();
    }

    public static boolean getChatNotify() {
        if(judgeMemberEmpty()){
            return true;
        }
        return memberBean.getSetting()==null?true:memberBean.getSetting().isChatNotify();
    }





}
