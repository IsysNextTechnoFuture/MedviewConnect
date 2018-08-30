package com.isysnext.medviewmd.medviewconnect.utils;


import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;


public interface AppConstants {
    /**
     * Application(Project) id on Google api(Developer) Console
     */

    /**
     * Messages for user interaction
     */
    String SHOW_ERROR = "-1", HIDE_ERROR = "-1", SUCCESS_1 = "1", SUCCESS_0 = "0", SUCCESS_TRUE = "true", SUCCESS_UNKNOWN = "Whoops! Unknown sucess value";
    String WORK_IN_PROGRESS = "WORK IN PROGRESS";
    String UNEXPEXTED_ERROR = "Whoops! Something is happen unexpectedly. Please try again.";
    String UNEXPECTED_RESPONSE = "Whoops! Something is happen unexpectedly. Response is not in proper format.";
    String PARSING_ERROR = "Whoops! Something is happen unexpectedly. Exception in data parsing.";
    String EXCEPTION = "Whoops! Something is happen unexpectedly. Exception in data processing.";
    int DARK = 1;
    int LIGHT = 2;

    //for Local
    //String BASE_URL = "http://192.168.11.153/StayConnected/API/api/baseURL";
    //for Server
    String BASE_URL = "http://medviewmd-api.noemaplatform.com/";
  //  String BASE_URL = "https://medviewmd.noemaplatform.com/";
    /**
     * Image Storage Path
     */
    String IMAGE_DIRECTORY = "/DCIM/PICTURES";
    String IMAGE_DIRECTORY_CROP = "/DCIM/CROP_PICTURES";
    String VIDEO_DIRECTORY = "/DCIM/VIDEOS";


    /**
     * Constant for Intent calling
     */
     int ACTIVITY_RESULT = 1001, ACTIVITY_FINISH = 1002,
            GALLERY = 111, CAMERA = 112, CROP = 113, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    /**
     * Validation regular expression
     */
    Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("^([a-zA-Z0-9._-]+)@{1}(([a-zA-Z0-9_-]{1,67})|([a-zA-Z0-9-]+\\.[a-zA-Z0-9-]{1,67}))\\.(([a-zA-Z0-9]{2,6})(\\.[a-zA-Z0-9]{2,6})?)$");
    Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("^[0-9]{10,14}$");
    Pattern LAND_LINE_NUMBER_PATTERN = Pattern.compile("^[0-9]\\d{2,4}-\\d{6,8}$");
    Pattern PERSON_NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
    Pattern USER_NAME_PATTERN = Pattern.compile("^([a-zA-Z0-9._-]){6,20}$");
    Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
    Pattern DATE_PATTERN = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])*$");

      //Menu parent items
     int EVENTS=0,FRIENDS=1,NOTIFICATIONS=2,SETTINGS=3,LOGOUT=4;

    //Menu child items
    int MY_EVENTS=0,PARTICIPATING_EVENT=1;


    String FILE_PATH = "file_path";
    String MINE_TYPE = "mimeType";

    /*Time format for 24 hours time to 12 hours*/
    SimpleDateFormat HH_MM_SS = new SimpleDateFormat(
            "HH:mm:ss", Locale.getDefault());
    SimpleDateFormat HH_MM_AM_PM = new SimpleDateFormat(
            "hh:mm a", Locale.getDefault());
    SimpleDateFormat HH_MM = new SimpleDateFormat(
            "HH:mm", Locale.getDefault());

    SimpleDateFormat YYYYMMDD = new SimpleDateFormat(
            "yyyyMMdd", Locale.getDefault());
    SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat(
            "yyyyMMddHHmmss", Locale.getDefault());

    /*Date format for later ride*/
    SimpleDateFormat MM_DD_YYYY = new SimpleDateFormat(
            "MM-dd-yyyy", Locale.getDefault());
    SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat DD_MMM_YYYY = new SimpleDateFormat(
            "dd MMM yyyy", Locale.getDefault());


    /**
     * Variable For Condition Check
     */
    String CURRENT_SECURITY_QUESTION="current_security_question";
    String NEW_SECURITY_QUESTION="new_security_question";
    String OFF="off";
    String ON="on";
    String PROVINCE_RESIDENCE = "province_residence";
    String PROVINCE_ADDRESS = "province_address";
    String PARTNER_ID="5";
    String CONSTANT_USERTYPE_PROVIDER = "1";
    String POSITION="position";
    String PROVIDER="provider";
    String FAVOURITE="favourite";
    String MY_MESSAGE_CHECK="my_message";
    String SENT_MESSAGE_CHECK="sent_message";

    /**************************
     * SERVICE NAME START***************************************
     * /**
     * Methods for request on remote server
     */
    String SIGN_IN = "api/ios/auth";
    String GET_VISIT = "api/ios/provider/call_list";
    String INTAKE_PATIENT ="api/ios/patient/getIntakeFormdata";
    String EDIT_PROFILE ="api/ios/updateprofile";
    String EDIT_PROFILE_SECURITY_QUESTION ="api/ios/patient/securityq";
    String EDIT_PROFILE_PASSWORD ="api/ios/patient/password";
    String LOAD_PROFILE = "api/ios/patient/profile";
    String MY_MESSAGE_LIST = "api/ios/securemessage";
    String LATEST_RATING = "api/ios/provider/getlatestratings";
    String MY_MESSAGE = "api/ios/securemessage";
    String ADD_TO_FAVORITE = "api/ios/patient/favorite/";
    String PROVIDER_LIST = "api/ios/providers/providerlist";
    String UNREAD_MESSAGE_COUNT = "api/ios/patient/unreadmessagecount";
    String REMOVE_TO_FAVORITE = "api/ios/patient/favorite/";

    /**************************
     * REQUEST PARAM START***************************************
     * /**
     * Parameter name for request on remote server
     */
    String PN_TITLE="title";
    String KEY_USER_ID = "id";

}
